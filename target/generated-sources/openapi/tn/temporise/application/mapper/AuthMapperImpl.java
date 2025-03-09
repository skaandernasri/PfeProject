package tn.temporise.application.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.domain.model.SigninUserRequest;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-09T10:11:16+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public Utilisateur toEntity(SigninUserRequest signinUserRequest) {
        if ( signinUserRequest == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail( signinUserRequest.getEmail() );
        utilisateur.setPassword( signinUserRequest.getPassword() );

        return utilisateur;
    }
}
