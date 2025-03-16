package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "utilisateur")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtilisateurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private String nom;
    private String email;
//    @Column(name="password")
    @Transient
    private String password;
    @OneToMany(mappedBy = "utilisateur")
    private Set<PanierEntity> paniers;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) //it was EAGER
    @CollectionTable(name = "utilisateur_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name ="role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles=new HashSet<>();
    public UtilisateurEntity(String email, String nom, Role role) {
        this.email = email;
        this.nom = nom;
        this.roles.add(role);

    }
    public UtilisateurEntity(Long id) {
        this.id=id;

    }


    public UtilisateurEntity(String email, Role role) {
        this.email = email;
        this.roles.add(role);
        this.password="Skander123.";
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
