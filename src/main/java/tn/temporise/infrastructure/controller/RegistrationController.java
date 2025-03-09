//package tn.temporise.infrastructure.controller;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//import tn.temporise.domain.model.SignupUserRequest;
//import tn.temporise.infrastructure.dto.RegistrationResponse;
//import tn.temporise.application.mapper.RegMapper;
//import tn.temporise.infrastructure.persistence.entity.Utilisateur;
//import tn.temporise.application.service.RegistrationService;
//
//@RestController
//public class RegistrationController {
//    private static final Logger logger = LogManager.getLogger(RegistrationController.class);
//
//    private final RegistrationService registrationService;
//    @Autowired
//    RegMapper regMapper;
//    public RegistrationController(RegistrationService registrationService) {
//        this.registrationService = registrationService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody SignupUserRequest request) {
//        try {
//            logger.info("Received register request : {}", request.getEmail());
//
//            Utilisateur user = regMapper.toEntity(request);
//
//            logger.debug("Attempt to create the user : {}", request.getEmail());
//
//            registrationService.register(user);
//
//            logger.info("User registered successfully: {}", request.getEmail());
//            return ResponseEntity.ok(new RegistrationResponse("success", "User registered successfully" ));
//        } catch (ResponseStatusException e) {
//            logger.error("Email not available");
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body(new RegistrationResponse("error", e.getMessage()));
//        } catch (InternalError e) {
//            logger.error("Registration failed");
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new RegistrationResponse("error", "Registration failed"));
//        }
//    }
//}
