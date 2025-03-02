package tn.temporise.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import tn.temporise.application.dto.AuthenticationRequest;
import tn.temporise.domain.model.Utilisateur;
@Mapper(componentModel = "spring")
public interface AuthMapper {
        Utilisateur toEntity(AuthenticationRequest authenticationRequest);
}
