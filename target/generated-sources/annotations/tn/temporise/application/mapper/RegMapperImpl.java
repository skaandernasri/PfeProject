package tn.temporise.application.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.application.dto.RegistrationRequest;
import tn.temporise.domain.model.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-01T10:53:52+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class RegMapperImpl implements RegMapper {

    @Override
    public Utilisateur toEntity(RegistrationRequest rq) {
        if ( rq == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setRoles( mapRoleToSet( rq.getRole() ) );
        utilisateur.setNom( rq.getNom() );
        utilisateur.setEmail( rq.getEmail() );
        utilisateur.setPassword( rq.getPassword() );

        return utilisateur;
    }
}
