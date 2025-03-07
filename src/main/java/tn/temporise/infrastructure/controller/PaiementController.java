
package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;

import tn.temporise.domain.model.PaiementRequest;
import tn.temporise.domain.model.PaiementResponse;
import tn.temporise.domain.model.Response;
import tn.temporise.infrastructure.api.PaiementsApi;

import java.util.List;

public class PaiementController implements PaiementsApi {
    @Override
    public ResponseEntity<PaiementResponse> _createPaiement(PaiementRequest paiement) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Response> _deletePaiement(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<PaiementResponse>> _getAllPaiements() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<PaiementResponse> _getPaiementById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<PaiementResponse> _updatePaiement(Long id, PaiementRequest paiement) throws Exception {
        return null;
    }
}
