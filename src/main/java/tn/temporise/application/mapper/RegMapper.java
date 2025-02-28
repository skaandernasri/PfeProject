package tn.temporise.application.mapper;

import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tn.temporise.application.dto.RegistrationRequest;
import tn.temporise.domain.model.Role;
import tn.temporise.domain.model.Utilisateur;

import java.util.Collections;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RegMapper {
   @Mapping(target = "roles",source = "role",qualifiedByName = "mapRoleToSet")
   Utilisateur toEntity(RegistrationRequest rq);
   @Named("mapRoleToSet")
   default Set<Role> mapRoleToSet(Role role) {
      return role != null ? Set.of(role) : Collections.emptySet();
   }
}
