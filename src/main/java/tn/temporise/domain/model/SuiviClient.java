package tn.temporise.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "suiviclient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuiviClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
    @Column(name = "action",nullable = false)
    private String action;
    @Column(name = "date",nullable = false)
    private LocalDateTime date;
}
