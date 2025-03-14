package tn.temporise.application.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.SigninUserRequest;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T10:20:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public UtilisateurModel toModel(SigninUserRequest signinUserRequest) {
        if ( signinUserRequest == null ) {
            return null;
        }

        String email = null;
        String password = null;

        email = signinUserRequest.getEmail();
        password = signinUserRequest.getPassword();

        Long id = null;
        String nom = null;
        Set<Role> roles = null;

        UtilisateurModel utilisateurModel = new UtilisateurModel( id, nom, email, password, roles );

        return utilisateurModel;
    }

    @Override
    public UtilisateurEntity modelToEntity(UtilisateurModel utilisateurModel) {
        if ( utilisateurModel == null ) {
            return null;
        }

        UtilisateurEntity utilisateurEntity = new UtilisateurEntity();

        utilisateurEntity.setId( utilisateurModel.id() );
        utilisateurEntity.setNom( utilisateurModel.nom() );
        utilisateurEntity.setEmail( utilisateurModel.email() );
        utilisateurEntity.setPassword( utilisateurModel.password() );
        utilisateurEntity.setRoles( roleSetToRoleSet( utilisateurModel.roles() ) );

        return utilisateurEntity;
    }

    @Override
    public UtilisateurModel entityToModel(UtilisateurEntity utilisateurEntity) {
        if ( utilisateurEntity == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String email = null;
        String password = null;
        Set<Role> roles = null;

        id = utilisateurEntity.getId();
        nom = utilisateurEntity.getNom();
        email = utilisateurEntity.getEmail();
        password = utilisateurEntity.getPassword();
        roles = roleSetToRoleSet1( utilisateurEntity.getRoles() );

        UtilisateurModel utilisateurModel = new UtilisateurModel( id, nom, email, password, roles );

        return utilisateurModel;
    }

    protected tn.temporise.infrastructure.persistence.entity.Role roleToRole(Role role) {
        if ( role == null ) {
            return null;
        }

        tn.temporise.infrastructure.persistence.entity.Role role1;

        switch ( role ) {
            case CLIENT: role1 = tn.temporise.infrastructure.persistence.entity.Role.CLIENT;
            break;
            case GESTIONNAIRE: role1 = tn.temporise.infrastructure.persistence.entity.Role.GESTIONNAIRE;
            break;
            case ADMIN: role1 = tn.temporise.infrastructure.persistence.entity.Role.ADMIN;
            break;
            case REDACTEUR: role1 = tn.temporise.infrastructure.persistence.entity.Role.REDACTEUR;
            break;
            case SUPER_ADMIN: role1 = tn.temporise.infrastructure.persistence.entity.Role.SUPER_ADMIN;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + role );
        }

        return role1;
    }

    protected Set<tn.temporise.infrastructure.persistence.entity.Role> roleSetToRoleSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<tn.temporise.infrastructure.persistence.entity.Role> set1 = new LinkedHashSet<tn.temporise.infrastructure.persistence.entity.Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRole( role ) );
        }

        return set1;
    }

    protected Role roleToRole1(tn.temporise.infrastructure.persistence.entity.Role role) {
        if ( role == null ) {
            return null;
        }

        Role role1;

        switch ( role ) {
            case CLIENT: role1 = Role.CLIENT;
            break;
            case GESTIONNAIRE: role1 = Role.GESTIONNAIRE;
            break;
            case ADMIN: role1 = Role.ADMIN;
            break;
            case REDACTEUR: role1 = Role.REDACTEUR;
            break;
            case SUPER_ADMIN: role1 = Role.SUPER_ADMIN;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + role );
        }

        return role1;
    }

    protected Set<Role> roleSetToRoleSet1(Set<tn.temporise.infrastructure.persistence.entity.Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( tn.temporise.infrastructure.persistence.entity.Role role : set ) {
            set1.add( roleToRole1( role ) );
        }

        return set1;
    }
}
