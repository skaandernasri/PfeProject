package tn.temporise.application.mapper;


import org.mapstruct.Mapper;
import tn.temporise.domain.model.SigninUserRequest;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;
@Mapper(componentModel = "spring")
public interface AuthMapper {
        Utilisateur toEntity(SigninUserRequest signinUserRequest);
}
