package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.CommandeRequest;
import tn.temporise.domain.model.CommandeResponse;
import tn.temporise.infrastructure.api.CommandesApi;

import java.util.List;

public class CommandeController implements CommandesApi {
    @Override
    public ResponseEntity<CommandeResponse> _createCommande(CommandeRequest commande) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteCommande(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<CommandeResponse>> _getAllCommandes() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<CommandeResponse> _getCommandeById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<CommandeResponse> _updateCommande(Long id, CommandeRequest commande) throws Exception {
        return null;
    }
}