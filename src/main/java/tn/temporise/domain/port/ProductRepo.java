package tn.temporise.domain.port;

import tn.temporise.domain.model.Produit;

import java.util.List;

public interface ProductRepo {
    public Produit save(Produit product);
    public Produit findById(Long id);
    public List<Produit> findAll();
    public Produit update(Produit product);
    public void deleteById(Long id);
    public void deleteAll();
}
