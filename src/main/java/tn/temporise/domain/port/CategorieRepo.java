package tn.temporise.domain.port;

import tn.temporise.domain.model.Categorie;

import java.util.List;

public interface CategorieRepo {
    public Categorie save(Categorie categorie);
    public Categorie findById(Long id);
    public List<Categorie> findAll();
    public Categorie update(Categorie categorie);
    public void deleteById(Long id);
    public void deleteAll();
}
