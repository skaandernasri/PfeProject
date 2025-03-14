package tn.temporise.infrastructure.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.temporise.infrastructure.persistence.entity.CategorieEntity;

import java.util.Optional;

@Repository
public interface CategorieJpaRepo extends JpaRepository<CategorieEntity,Long> {
    @Query("SELECT c FROM CategorieEntity c WHERE c.id = :id")
    Optional<CategorieEntity> findById(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM CategorieEntity c WHERE c.id = :id")
    void deleteById(@Param("id") Long id);
}
