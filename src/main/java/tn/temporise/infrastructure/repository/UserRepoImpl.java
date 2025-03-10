package tn.temporise.infrastructure.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@Repository
public class UserRepoImpl implements UserRepo {

    private final UserJpaRepo userJpaRepo;
    @Override
    public Optional<UtilisateurEntity> findByEmail(String email) {
        return userJpaRepo.findByEmail(email); // Calls the method on the injected repository
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
    public UtilisateurEntity save(UtilisateurEntity utilisateurEntity) {
        return userJpaRepo.save(utilisateurEntity);
    }

    @Override
    public void deleteAll() {
        userJpaRepo.deleteAll();
    }


}



