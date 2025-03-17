package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import tn.temporise.application.mapper.ProductMapper;
import tn.temporise.domain.model.Produit;
import tn.temporise.domain.port.ProductRepo;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ProductRepoImp implements ProductRepo {
    private final ProductJpaRepo productJpaRepo;
    private final ProductMapper productMapper;
    @Override
    public Produit save(Produit product) {
        ProduitEntity produitEntity=productJpaRepo.save(productMapper.modelToEntity(product));
        return productMapper.entityToModel(produitEntity);
    }

    @Override
    public Produit findById(Long id) {
        log.info("before findById RepoImp " + id);
        return productJpaRepo.findById(id)
                .map(productMapper::entityToModel)
                .orElse(null); // Retourne null si le produit n'existe pas
    }

    @Override
    public List<Produit> findAll() {
        return productJpaRepo.findAll()
                .stream()
                .map(productMapper::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Produit update(Produit product) {
        return productJpaRepo.findById(product.id())
                .map(existingEntity -> {
                    ProduitEntity updatedEntity = productMapper.modelToEntity(product);
                    updatedEntity.setId(existingEntity.getId()); // Assurer la conservation de l'ID
                    return productMapper.entityToModel(productJpaRepo.save(updatedEntity));
                })
                .orElse(null);
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
