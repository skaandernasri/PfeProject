package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@Repository
public class UserRepoImpl implements UserRepo {
    private final RegMapper regMapper;
    private final UserJpaRepo userJpaRepo;
    @Override
    public Optional<UtilisateurModel> findByEmail(String email) {
        Optional<UtilisateurEntity> utilisateurEntity = userJpaRepo.findByEmail(email);
        return utilisateurEntity.map(regMapper::entityToModel);
    }

    @Override
    public Set<Role> findRolesById(long id) {
        return userJpaRepo.findRolesById(id); // Calls the method on the injected repository
    }

    @Override
    public void deleteByEmail(String email) {
        userJpaRepo.deleteByEmail(email); // Calls the method on the injected repository
    }

    @Override
    public UtilisateurModel save(UtilisateurModel utilisateurModel) {
        UtilisateurEntity utilisateurEntity=regMapper.modelToEntity(utilisateurModel);
        userJpaRepo.save(utilisateurEntity);
        return regMapper.entityToModel(utilisateurEntity);
    }

    @Override
    public void deleteAll() {
        userJpaRepo.deleteAll();
    }


}



