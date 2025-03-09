package tn.temporise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuiviClient {

    private Long id;


    private Utilisateur utilisateur;
    private String action;
    private LocalDateTime date;
}
