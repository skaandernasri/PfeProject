package tn.temporise.domain.port;

import tn.temporise.infrastructure.persistence.entity.PanierEntity;

import java.util.List;
import java.util.Optional;

public interface PanierRepo {
    public PanierEntity save(PanierEntity panier);
    public Optional<PanierEntity> findById(Long id);
    public List<PanierEntity> findAll();
    public PanierEntity update(PanierEntity panier);
    public void deleteById(Long id);
    public void deleteAll();
    public Optional<PanierEntity> findByUtilisateurId(Long id);
}
