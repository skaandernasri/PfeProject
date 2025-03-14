package tn.temporise.domain.port;

import tn.temporise.infrastructure.persistence.entity.CategorieEntity;

import java.util.List;
import java.util.Optional;

public interface CategorieRepo {
    public CategorieEntity save(CategorieEntity categorie);
    public Optional<CategorieEntity> findById(Long id);
    public List<CategorieEntity> findAll();
    public CategorieEntity update(CategorieEntity categorie);
    public void deleteById(Long id);
    public void deleteAll();
}
