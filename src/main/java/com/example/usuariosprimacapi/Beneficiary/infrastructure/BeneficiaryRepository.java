package com.example.usuariosprimacapi.Beneficiary.infrastructure;

import com.example.usuariosprimacapi.Beneficiary.domain.Beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    
    @Query("SELECT b FROM Beneficiary b JOIN FETCH b.client c JOIN FETCH c.user")
    List<Beneficiary> findAllWithClientAndUser();
    
    @Query("SELECT b FROM Beneficiary b JOIN FETCH b.client c JOIN FETCH c.user WHERE b.id = :id")
    Optional<Beneficiary> findByIdWithClientAndUser(Long id);
    
    @Query(value = "SELECT b FROM Beneficiary b JOIN FETCH b.client c JOIN FETCH c.user",
           countQuery = "SELECT COUNT(b) FROM Beneficiary b")
    Page<Beneficiary> findAllWithClientAndUser(Pageable pageable);
    
    @Query(value = "SELECT b FROM Beneficiary b JOIN FETCH b.client c JOIN FETCH c.user WHERE " +
           "(:firstName IS NULL OR LOWER(b.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(b.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:documentNumber IS NULL OR b.documentNumber LIKE CONCAT('%', :documentNumber, '%')) AND " +
           "(:clientId IS NULL OR b.client.userId = :clientId)",
           countQuery = "SELECT COUNT(b) FROM Beneficiary b WHERE " +
           "(:firstName IS NULL OR LOWER(b.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(b.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:documentNumber IS NULL OR b.documentNumber LIKE CONCAT('%', :documentNumber, '%')) AND " +
           "(:clientId IS NULL OR b.client.userId = :clientId)")
    Page<Beneficiary> findBySearchCriteria(@Param("firstName") String firstName,
                                          @Param("lastName") String lastName,
                                          @Param("documentNumber") String documentNumber,
                                          @Param("clientId") Long clientId,
                                          Pageable pageable);
}
