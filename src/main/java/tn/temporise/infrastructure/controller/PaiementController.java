package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.Paiement;
import tn.temporise.infrastructure.api.PaiementsApi;

import java.util.List;

public class PaiementController implements PaiementsApi {
    @Override
    public ResponseEntity<Paiement> _createPaiement(Paiement paiement) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deletePaiement(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<Paiement>> _getAllPaiements() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Paiement> _getPaiementById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Paiement> _updatePaiement(Long id, Paiement paiement) throws Exception {
        return null;
    }
}
