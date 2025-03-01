package tn.temporise.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "authentification")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authentification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long  id;
    @Column(name="motDePasse")
    private String motDePasse;
    @Column(name="providerId")
    private String providerId;
    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeAuthentification type;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur user;

    public Authentification(String motDePasse, TypeAuthentification type, Utilisateur user) {
        this.motDePasse = motDePasse;
        this.type = type;
        this.user = user;
    }
    public Authentification(Utilisateur user,String providerId, TypeAuthentification type) {
        this.providerId = providerId;
        this.type = type;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Authentification{" +
                "id=" + id +
                ", motDePasse='" + motDePasse + '\'' +
                ", providerId='" + providerId + '\'' +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
