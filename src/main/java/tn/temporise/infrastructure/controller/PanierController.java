package tn.temporise.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.service.PanierService;
import tn.temporise.domain.model.PanierRequest;
import tn.temporise.domain.model.PanierResponse;
import tn.temporise.domain.model.Response;
import tn.temporise.infrastructure.api.PaniersApi;

import java.util.List;
@RestController
@RequiredArgsConstructor
@Slf4j
public class PanierController implements PaniersApi {
    private final PanierService panierService;
    @Override
    public ResponseEntity<PanierResponse> _createPanier(PanierRequest panierRequest) throws Exception {
//        log.info("Creating a new Panier with request: {}", panierRequest);
////        PanierResponse panierResponse = panierService.createPanier(panierRequest);
////        return ResponseEntity.status(HttpStatus.CREATED).body(panierResponse);
        return null;
    }

    @Override
    public ResponseEntity<Response> _deleteAllPaniers() throws Exception {
        panierService.deleteAllPaniers();
        Response response = new Response();
        response.setCode("200");
        response.setMessage("Tout les paniers ont été supprimés avec succès");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> _deletePanier(Long id) throws Exception {
        log.info("Deleting Panier with ID: {}", id);
        panierService.deletePanier(id);
        Response response = new Response();
        response.setCode("200");
        response.setMessage("Panier supprimé avec succès !");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<PanierResponse>> _getAllPaniers() throws Exception {
        log.info("Fetching all Paniers");
        List<PanierResponse> panierResponses = panierService.getAllPaniers();
        return ResponseEntity.ok(panierResponses);
    }

    @Override
    public ResponseEntity<PanierResponse> _getPanierById(Long id) throws Exception {
        log.info("Fetching Panier with ID: {}", id);
        PanierResponse panierResponse = panierService.getPanierById(id);
        return ResponseEntity.ok(panierResponse);
    }

    @Override
    public ResponseEntity<PanierResponse> _updatePanier(Long id, PanierRequest panierRequest) throws Exception {
        log.info("Updating Panier with ID: {} and request: {}", id, panierRequest);
        PanierResponse panierResponse = panierService.updatePanier(id, panierRequest);
        return ResponseEntity.ok(panierResponse);
    }
}
