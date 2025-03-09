package tn.temporise.application.mapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.domain.model.SignupUserRequest;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-09T10:11:16+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class RegMapperImpl implements RegMapper {

    @Override
    public Utilisateur toEntity(SignupUserRequest signupUserRequest) {
        if ( signupUserRequest == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setRoles( stringListToRoleSet( signupUserRequest.getRoles() ) );
        utilisateur.setNom( signupUserRequest.getNom() );
        utilisateur.setEmail( signupUserRequest.getEmail() );
        utilisateur.setPassword( signupUserRequest.getPassword() );

        return utilisateur;
    }

    protected Set<Role> stringListToRoleSet(List<String> list) {
        if ( list == null ) {
            return null;
        }

        Set<Role> set = new LinkedHashSet<Role>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( String string : list ) {
            set.add( Enum.valueOf( Role.class, string ) );
        }

        return set;
    }
}
