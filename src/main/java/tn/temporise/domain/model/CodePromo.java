package tn.temporise.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "codepromo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodePromo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,name = "code")
    private String code;

    @Column(nullable = false,name = "reduction")
    private double reduction;
    @Column(nullable = false,name = "dateExpiration")
    private LocalDateTime dateExpiration;
    @OneToOne(mappedBy = "codePromo")
    private Commande commande;
}