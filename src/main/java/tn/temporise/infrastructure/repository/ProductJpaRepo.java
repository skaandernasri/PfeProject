package tn.temporise.infrastructure.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.util.Optional;


@Repository
public interface ProductJpaRepo  extends JpaRepository<ProduitEntity,Long> {
    @Query("SELECT p FROM ProduitEntity p WHERE p.id = :id")
    Optional<ProduitEntity> findById(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM ProduitEntity p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
