//package tn.temporise.infrastructure.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//
//import org.springframework.stereotype.Repository;
//import tn.temporise.domain.port.UserRepo;
//import tn.temporise.infrastructure.persistence.entity.Role;
//import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.function.Function;
//@RequiredArgsConstructor
//@Repository
//public class UserRepoImpl implements UserRepo {
//    @Autowired
//    private final UserRepo userRepo;
//
//
//    @Override
//    public Optional<UtilisateurEntity> findByEmail(String email) {
//        return userRepo.findByEmail(email); // Calls the method on the injected repository
//    }
//
//    @Override
//    public Set<Role> findRolesById(long id) {
//        return userRepo.findRolesById(id); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteByEmail(String email) {
//        userRepo.deleteByEmail(email); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void flush() {
//        // Flush operation, if needed, can be handled here
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> S saveAndFlush(S entity) {
//        return userRepo.saveAndFlush(entity); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return userRepo.saveAllAndFlush(entities); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<UtilisateurEntity> entities) {
//        userRepo.deleteAllInBatch(entities); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//        userRepo.deleteAllByIdInBatch(longs); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//        userRepo.deleteAllInBatch(); // Calls the method on the injected repository
//    }
//
//    @Override
//    public UtilisateurEntity getOne(Long aLong) {
//        return userRepo.getOne(aLong); // Calls the method on the injected repository
//    }
//
//    @Override
//    public UtilisateurEntity getById(Long aLong) {
//        return userRepo.getById(aLong); // Calls the method on the injected repository
//    }
//
//    @Override
//    public UtilisateurEntity getReferenceById(Long aLong) {
//        return userRepo.getReferenceById(aLong); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> Optional<S> findOne(Example<S> example) {
//        return userRepo.findOne(example); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> List<S> findAll(Example<S> example) {
//        return userRepo.findAll(example); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> List<S> findAll(Example<S> example, Sort sort) {
//        return userRepo.findAll(example, sort); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return userRepo.findAll(example, pageable); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> long count(Example<S> example) {
//        return userRepo.count(example); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> boolean exists(Example<S> example) {
//        return userRepo.exists(example); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return userRepo.findBy(example, queryFunction); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> S save(S entity) {
//        return userRepo.save(entity); // Calls the method on the injected repository
//    }
//
//    @Override
//    public <S extends UtilisateurEntity> List<S> saveAll(Iterable<S> entities) {
//        return userRepo.saveAll(entities); // Calls the method on the injected repository
//    }
//
//    @Override
//    public Optional<UtilisateurEntity> findById(Long aLong) {
//        return userRepo.findById(aLong); // Calls the method on the injected repository
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return userRepo.existsById(aLong); // Calls the method on the injected repository
//    }
//
//    @Override
//    public List<UtilisateurEntity> findAll() {
//        return userRepo.findAll(); // Calls the method on the injected repository
//    }
//
//    @Override
//    public List<UtilisateurEntity> findAllById(Iterable<Long> longs) {
//        return userRepo.findAllById(longs); // Calls the method on the injected repository
//    }
//
//    @Override
//    public long count() {
//        return userRepo.count(); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//        userRepo.deleteById(aLong); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void delete(UtilisateurEntity entity) {
//        userRepo.delete(entity); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//        userRepo.deleteAllById(longs); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends UtilisateurEntity> entities) {
//        userRepo.deleteAll(entities); // Calls the method on the injected repository
//    }
//
//    @Override
//    public void deleteAll() {
//        userRepo.deleteAll(); // Calls the method on the injected repository
//    }
//
//    @Override
//    public List<UtilisateurEntity> findAll(Sort sort) {
//        return userRepo.findAll(sort); // Calls the method on the injected repository
//    }
//
//    @Override
//    public Page<UtilisateurEntity> findAll(Pageable pageable) {
//        return userRepo.findAll(pageable); // Calls the method on the injected repository
//    }
//}
//
//
//
