package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.CategorieRepo;
import tn.temporise.infrastructure.persistence.entity.CategorieEntity;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class CategorieRepoImp implements CategorieRepo {
    private final CategorieJpaRepo categorieJpaRepo;
    @Override
    public CategorieEntity save(CategorieEntity categorie) {
        return categorieJpaRepo.save(categorie);
    }

    @Override
    public Optional<CategorieEntity> findById(Long id) {
        return categorieJpaRepo.findById(id);
    }

    @Override
    public List<CategorieEntity> findAll() {
        return categorieJpaRepo.findAll();
    }

    @Override
    public CategorieEntity update(CategorieEntity categorie) {
        return categorieJpaRepo.save(categorie);
    }

    @Override
    public void deleteById(Long id) {
        categorieJpaRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        categorieJpaRepo.deleteAll();
    }
}
