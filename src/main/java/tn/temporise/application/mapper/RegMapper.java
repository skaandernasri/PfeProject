package tn.temporise.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tn.temporise.domain.model.SignupUserRequest;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Collections;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RegMapper {
   @Mapping(target = "roles",source = "roles")
   UtilisateurEntity toEntity(SignupUserRequest signupUserRequest);
   @Named("mapRoleToSet")
   default Set<Role> mapRoleToSet(Role role) {
      return role != null ? Set.of(role) : Collections.emptySet();
   }
}

