package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.Commande;
import tn.temporise.infrastructure.api.CommandesApi;

import java.util.List;

public class CommandeController implements CommandesApi {
    @Override
    public ResponseEntity<Commande> _createCommande(Commande commande) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteCommande(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<Commande>> _getAllCommandes() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Commande> _getCommandeById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Commande> _updateCommande(Long id, Commande commande) throws Exception {
        return null;
    }
}
