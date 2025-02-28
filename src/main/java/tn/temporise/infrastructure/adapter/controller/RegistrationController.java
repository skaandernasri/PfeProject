package tn.temporise.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.application.dto.RegistrationRequest;
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
    public String register(@RequestBody RegistrationRequest request) {
        Utilisateur user = regMapper.toEntity(request);
        registrationService.register(user);
        return "User registered successfully";
    }
}
