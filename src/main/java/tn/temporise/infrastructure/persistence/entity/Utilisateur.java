package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Utilisateur {

    private Long  id;
    private String nom;
    private String email;
//    @Column(name="password")
    @Transient
    private String password;
//    @OneToMany(mappedBy = "utilisateur")
//    private Set<Panier> paniers;
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) //it was EAGER
//    @CollectionTable(name = "utilisateur_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name ="role",nullable = false)
//    @Enumerated(EnumType.STRING)
    private Set<Role> roles=new HashSet<>();
    public Utilisateur(String email, String nom, Role role) {
        this.email = email;
        this.nom = nom;
        this.roles.add(role);

    }


    public Utilisateur(String email, Role role) {
        this.email = email;
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
