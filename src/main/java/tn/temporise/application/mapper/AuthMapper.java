package tn.temporise.application.mapper;


import org.mapstruct.Mapper;
import tn.temporise.domain.model.SigninUserRequest;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
@Mapper(componentModel = "spring")
public interface AuthMapper {
        UtilisateurModel toModel(SigninUserRequest signinUserRequest);
        UtilisateurEntity modelToEntity(UtilisateurModel utilisateurModel);
        UtilisateurModel entityToModel(UtilisateurEntity utilisateurEntity);
}
