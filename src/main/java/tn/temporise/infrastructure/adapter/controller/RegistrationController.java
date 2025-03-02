package tn.temporise.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tn.temporise.application.dto.RegistrationRequest;
import tn.temporise.application.dto.RegistrationResponse;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.domain.model.Utilisateur;
import tn.temporise.domain.service.RegistrationService;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;
    @Autowired
    RegMapper regMapper;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            Utilisateur user = regMapper.toEntity(request);

            registrationService.register(user);

            return ResponseEntity.ok(new RegistrationResponse("success", "User registered successfully" ));
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new RegistrationResponse("error", e.getMessage()));
        } catch (InternalError e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RegistrationResponse("error", "Registration failed"));
        }
    }
}
