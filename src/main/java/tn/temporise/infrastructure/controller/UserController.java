
package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;

import tn.temporise.domain.model.Response;
import tn.temporise.domain.model.UserRequest;
import tn.temporise.domain.model.UserResponse;
import tn.temporise.infrastructure.api.UtilisateursApi;

import java.util.List;

public class UserController implements UtilisateursApi {
    @Override
    public ResponseEntity<UserResponse> _createUtilisateur(UserRequest utilisateur) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Response> _deleteUtilisateur(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<UserResponse>> _getAllUtilisateurs() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> _getUtilisateurById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> _updateUtilisateur(Long id, UserRequest utilisateur) throws Exception {
        return null;
    }
}
