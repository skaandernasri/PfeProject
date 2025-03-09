package tn.temporise.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authentification {
    private Long id;
    private String password;
    private String providerId;
    private String token;
    private TypeAuthentification type;
    private Utilisateur user; // Just a reference without @ManyToOne

    public Authentification(String password, TypeAuthentification type, Utilisateur user,String providerId) {
        this.password = password;
        this.type = type;
        this.user = user;
        this.providerId=providerId;
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
                ", motDePasse='" + password + '\'' +
                ", providerId='" + providerId + '\'' +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
