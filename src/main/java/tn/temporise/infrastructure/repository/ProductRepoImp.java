package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.ProductRepo;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Repository
@Slf4j
public class ProductRepoImp implements ProductRepo {
    private final ProductJpaRepo productJpaRepo;

    @Override
    public ProduitEntity save(ProduitEntity product) {
        return productJpaRepo.save(product);
    }

    @Override
    public Optional<ProduitEntity> findById(Long id) {
        log.info("before finById RepoImp" +id);
        return productJpaRepo.findById(id);
    }

    @Override
    public List<ProduitEntity> findAll() {
        return productJpaRepo.findAll();
    }

    @Override
    public ProduitEntity update(ProduitEntity product) {
        return productJpaRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        productJpaRepo.deleteAll();
    }
}
