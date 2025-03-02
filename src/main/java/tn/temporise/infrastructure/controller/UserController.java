package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.infrastructure.api.UtilisateursApi;

import java.util.List;

public class UserController implements UtilisateursApi {
    @Override
    public ResponseEntity<Utilisateur> _createUtilisateur(Utilisateur utilisateur) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteUtilisateur(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<Utilisateur>> _getAllUtilisateurs() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> _getUtilisateurById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> _updateUtilisateur(Long id, Utilisateur utilisateur) throws Exception {
        return null;
    }
}
