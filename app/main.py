from fastapi import FastAPI, Depends, HTTPException
from sqlmodel import Session, select
from contextlib import asynccontextmanager
from app.database import get_session, engine
from app.models import Utilisateur, Produit, SuiviClient
from lightfm import LightFM
from lightfm.data import Dataset
import os, pickle, numpy as np
from datetime import datetime
from lightfm.cross_validation import random_train_test_split
from lightfm.evaluation import precision_at_k, recall_at_k, auc_score

MODEL_FILE = "lightfm_model.pkl"

# Global variables
model = None
dataset = None
user_features = None
item_features = None


def compute_user_features(user: Utilisateur):
    age = datetime.now().year - user.date_naissance.year
    return [f"genre:{user.genre}", f"age_group:{age // 10 * 10}"]


def compute_product_features(product: Produit):
    return [
        f"categorie:{product.categorie_id}",
        f"marque:{product.marque}",
        f"taille:{product.taille}",
        f"couleur:{product.couleur}"
    ]


def load_data(session: Session):
    users = session.exec(select(Utilisateur)).all()
    products = session.exec(select(Produit)).all()
    interactions = session.exec(select(SuiviClient)).all()
    return users, products, interactions


def build_dataset(users, products, interactions):
    dataset = Dataset()
    user_ids = [str(u.id) for u in users]
    product_ids = [str(p.id) for p in products]
    user_features_list = [(str(u.id), compute_user_features(u)) for u in users]
    product_features_list = [(str(p.id), compute_product_features(p)) for p in products]

    dataset.fit(
        users=user_ids,
        items=product_ids,
        user_features=set(f for _, feats in user_features_list for f in feats),
        item_features=set(f for _, feats in product_features_list for f in feats)
    )

    interactions_data = [(str(i.utilisateur_id), str(i.produit_id)) for i in interactions]
    interaction_matrix, _ = dataset.build_interactions(interactions_data)
    user_features = dataset.build_user_features(user_features_list)
    item_features = dataset.build_item_features(product_features_list)

    return dataset, interaction_matrix, user_features, item_features


def train_model(session: Session):
    users, products, interactions = load_data(session)
    dataset, interaction_matrix, u_features, i_features = build_dataset(users, products, interactions)

    # Split data for better evaluation
    train_matrix, test_matrix = random_train_test_split(interaction_matrix, test_percentage=0.2)

    model = LightFM(loss='warp', no_components=64)
    model.fit(
        train_matrix,
        user_features=u_features,
        item_features=i_features,
        epochs=40,
        num_threads=4
    )

    # Save the model
    with open(MODEL_FILE, "wb") as f:
        pickle.dump((model, dataset, u_features, i_features), f)

    return model, dataset, u_features, i_features

# Lifespan handler
@asynccontextmanager
async def lifespan(app: FastAPI):
    global model, dataset, user_features, item_features
    with Session(engine) as session:
        if os.path.exists(MODEL_FILE):
            with open(MODEL_FILE, "rb") as f:
                model, dataset, user_features, item_features = pickle.load(f)
            print("✅ Model loaded from file.")
        else:
            print("⚠️ Model not found. Training now...")
            model, dataset, user_features, item_features = train_model(session)
            print("✅ Model trained and saved.")
    yield
    # No shutdown logic needed


# Initialize FastAPI with lifespan
app = FastAPI(lifespan=lifespan)

@app.get("/model/train")
def train_model_endpoint(session: Session = Depends(get_session)):
    global model, dataset, user_features, item_features
    model, dataset, user_features, item_features = train_model(session)
    return {"message": "Model trained and saved."}
@app.get("/recommendations/{user_id}")
def recommend_products(user_id: int, session: Session = Depends(get_session), num_recs: int = 5):
    global model, dataset, user_features, item_features

    if model is None or dataset is None or user_features is None or item_features is None:
        raise HTTPException(status_code=503, detail="Model not ready")

    user = session.get(Utilisateur, user_id)
    if not user:
        raise HTTPException(status_code=404, detail="User not found")

    products = session.exec(select(Produit)).all()
    product_ids = [str(p.id) for p in products]

    try:
        user_idx = dataset.mapping()[0][str(user_id)]
    except KeyError:
        raise HTTPException(status_code=404, detail="User not in training dataset")

    scores = model.predict(
        user_ids=user_idx,
        item_ids=np.arange(len(product_ids)),
        user_features=user_features,
        item_features=item_features,
        num_threads=4
    )

    top_indices = np.argsort(-scores)[:num_recs]
    recommended_product_ids = [int(product_ids[i]) for i in top_indices]

    return {
        "user_id": user_id,
        "recommended_products": recommended_product_ids
    }
@app.get("/model/metrics")
def get_model_metrics():
    global model, dataset, user_features, item_features

    if model is None or dataset is None:
        raise HTTPException(status_code=503, detail="Model not ready")

    # Reload interaction matrix from data
    with Session(engine) as session:
        users, products, interactions = load_data(session)
        _, interaction_matrix, _, _ = build_dataset(users, products, interactions)
        train, test = random_train_test_split(interaction_matrix, test_percentage=0.2)

        precision = precision_at_k(model, test, k=5, user_features=user_features, item_features=item_features).mean()
        recall = recall_at_k(model, test, k=5, user_features=user_features, item_features=item_features).mean()
        auc = auc_score(model, test, user_features=user_features, item_features=item_features).mean()
    return {
        "Precision@5": float(round(precision, 4)),
        "Recall@5": float(round(recall, 4)),
        "AUC": float(round(auc, 4))
    }


