package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.PanierRepo;
import tn.temporise.infrastructure.persistence.entity.PanierEntity;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class PanierRepoImp implements PanierRepo {
    private final PanierJpaRepo panierJpaRepo;
    @Override
    public PanierEntity save(PanierEntity panier) {
        return panierJpaRepo.save(panier);
    }

    @Override
    public Optional<PanierEntity> findById(Long id) {
        return panierJpaRepo.findById(id);
    }

    @Override
    public List<PanierEntity> findAll() {
        return panierJpaRepo.findAll();
    }

    @Override
    public PanierEntity update(PanierEntity panier) {
        return panierJpaRepo.save(panier);
    }

    @Override
    public void deleteById(Long id) {
        panierJpaRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        panierJpaRepo.deleteAll();
    }

    @Override
    public Optional<PanierEntity> findByUtilisateurId(Long id) {
        return panierJpaRepo.findByUtilisateurId(id);
    }
}
