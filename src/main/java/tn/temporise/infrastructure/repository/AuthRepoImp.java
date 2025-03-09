//package tn.temporise.infrastructure.repository;
//
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.stereotype.Repository;
//import tn.temporise.domain.port.AuthRepo;
//import tn.temporise.domain.port.UserRepo;
//import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//
//@Repository
//public class AuthRepoImp implements AuthRepo {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private AuthRepo authRepo;
//
//    @Override
//    public Optional<AuthentificationEntity> findByUserEmail(String email) {
//        return authRepo.findByUserEmail(email);
//    }
//
//    @Override
//    public Optional<AuthentificationEntity> findByUserEmailAndProviderId(String email, String providerId) {
//        return authRepo.findByUserEmailAndProviderId(email, providerId);
//    }
//
//    @Override
//    public Optional<AuthentificationEntity> findByToken(String token) {
//        return authRepo.findByToken(token);
//    }
//
//    @Override
//    public void flush() {
//        authRepo.flush();
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> S saveAndFlush(S entity) {
//        return authRepo.saveAndFlush(entity);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return authRepo.saveAllAndFlush(entities);
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<AuthentificationEntity> entities) {
//        authRepo.deleteAllInBatch(entities);
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//        authRepo.deleteAllByIdInBatch(longs);
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//        authRepo.deleteAllInBatch();
//    }
//
//    @Override
//    public AuthentificationEntity getOne(Long aLong) {
//        return authRepo.getOne(aLong);
//    }
//
//    @Override
//    public AuthentificationEntity getById(Long aLong) {
//        return authRepo.getById(aLong);
//    }
//
//    @Override
//    public AuthentificationEntity getReferenceById(Long aLong) {
//        return authRepo.getReferenceById(aLong);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> Optional<S> findOne(Example<S> example) {
//        return authRepo.findOne(example);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> List<S> findAll(Example<S> example) {
//        return authRepo.findAll(example);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> List<S> findAll(Example<S> example, Sort sort) {
//        return authRepo.findAll(example, sort);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return authRepo.findAll(example, pageable);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> long count(Example<S> example) {
//        return authRepo.count(example);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> boolean exists(Example<S> example) {
//        return authRepo.exists(example);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return authRepo.findBy(example, queryFunction);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> S save(S entity) {
//        return authRepo.save(entity);
//    }
//
//    @Override
//    public <S extends AuthentificationEntity> List<S> saveAll(Iterable<S> entities) {
//        return authRepo.saveAll(entities);
//    }
//
//    @Override
//    public Optional<AuthentificationEntity> findById(Long aLong) {
//        return authRepo.findById(aLong);
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return authRepo.existsById(aLong);
//    }
//
//    @Override
//    public List<AuthentificationEntity> findAll() {
//        return authRepo.findAll();
//    }
//
//    @Override
//    public List<AuthentificationEntity> findAllById(Iterable<Long> longs) {
//        return authRepo.findAllById(longs);
//    }
//
//    @Override
//    public long count() {
//        return authRepo.count();
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//        authRepo.deleteById(aLong);
//    }
//
//    @Override
//    public void delete(AuthentificationEntity entity) {
//        authRepo.delete(entity);
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//        authRepo.deleteAllById(longs);
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends AuthentificationEntity> entities) {
//        authRepo.deleteAll(entities);
//    }
//
//    @Override
//    public void deleteAll() {
//        authRepo.deleteAll();
//    }
//
//    @Override
//    public List<AuthentificationEntity> findAll(Sort sort) {
//        return authRepo.findAll(sort);
//    }
//
//    @Override
//    public Page<AuthentificationEntity> findAll(Pageable pageable) {
//        return authRepo.findAll(pageable);
//    }
//
//
//}
