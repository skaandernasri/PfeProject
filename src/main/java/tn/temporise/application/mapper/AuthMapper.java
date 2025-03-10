package tn.temporise.application.mapper;


import org.mapstruct.Mapper;
import tn.temporise.domain.model.SigninUserRequest;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
@Mapper(componentModel = "spring")
public interface AuthMapper {
        UtilisateurEntity toEntity(SigninUserRequest signinUserRequest);
}
