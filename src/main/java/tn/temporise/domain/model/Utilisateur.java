package tn.temporise.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Table(name = "utilisateur")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Utilisateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long  id;
    @Column(name="nom")
    private String nom;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="password")
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) //it was EAGER
    @CollectionTable(name = "utilisateur_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name ="roles",nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles=new HashSet<>();
    public Utilisateur(String email, String nom, String password, Role role) {
        this.email = email;
        this.nom = nom;
        this.roles.add(role);
        this.password=password;
    }

    public Utilisateur(String nom, String email, Role role) {
        this.nom = nom;
        this.email = email;
        this.roles.add(role);
    }

    public Utilisateur(String email, Role role) {
        this.email = email;
        this.roles.add(role);
        this.password="";
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
