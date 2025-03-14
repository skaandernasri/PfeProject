package tn.temporise.domain.port;

import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepo {
    public ProduitEntity save(ProduitEntity product);
    public Optional<ProduitEntity> findById(Long id);
    public List<ProduitEntity> findAll();
    public ProduitEntity update(ProduitEntity product);
    public void deleteById(Long id);
    public void deleteAll();
}
