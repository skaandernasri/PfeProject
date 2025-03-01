package tn.temporise.infrastructure.adapter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.temporise.domain.model.Utilisateur;

@RestController
public class UserController {
    @RequestMapping("/")
    public String home(){
        return "Welcome";
    }
    @RequestMapping("/user")
    public Utilisateur user(Utilisateur user){
        return user;
    }
}
