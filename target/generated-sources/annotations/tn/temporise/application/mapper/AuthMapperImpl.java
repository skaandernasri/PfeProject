package tn.temporise.application.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.application.dto.AuthenticationRequest;
import tn.temporise.domain.model.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-01T10:53:53+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public Utilisateur toEntity(AuthenticationRequest authenticationRequest) {
        if ( authenticationRequest == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail( authenticationRequest.getEmail() );
        utilisateur.setPassword( authenticationRequest.getPassword() );

        return utilisateur;
    }
}
