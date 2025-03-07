package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.PanierRequest;
import tn.temporise.domain.model.PanierResponse;
import tn.temporise.domain.model.Response;
import tn.temporise.infrastructure.api.PaniersApi;

import java.util.List;

public class PanierController implements PaniersApi {
    @Override
    public ResponseEntity<PanierResponse> _createPanier(PanierRequest panierRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Response> _deletePanier(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<PanierResponse>> _getAllPaniers() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<PanierResponse> _getPanierById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<PanierResponse> _updatePanier(Long id, PanierRequest panierRequest) throws Exception {
        return null;
    }
}
