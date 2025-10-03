package com.example.usuariosprimacapi.Client.infrastructure;

import com.example.usuariosprimacapi.Client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    @Query("SELECT c FROM Client c JOIN FETCH c.user")
    List<Client> findAllWithUser();
    
    @Query("SELECT c FROM Client c JOIN FETCH c.user WHERE c.userId = :id")
    Optional<Client> findByIdWithUser(Long id);
    
    @Query(value = "SELECT c FROM Client c JOIN FETCH c.user",
           countQuery = "SELECT COUNT(c) FROM Client c")
    Page<Client> findAllWithUser(Pageable pageable);
    
    @Query(value = "SELECT c FROM Client c JOIN FETCH c.user WHERE " +
           "(:firstName IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:documentNumber IS NULL OR c.documentNumber LIKE CONCAT('%', :documentNumber, '%'))",
           countQuery = "SELECT COUNT(c) FROM Client c WHERE " +
           "(:firstName IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:documentNumber IS NULL OR c.documentNumber LIKE CONCAT('%', :documentNumber, '%'))")
    Page<Client> findBySearchCriteria(@Param("firstName") String firstName,
                                     @Param("lastName") String lastName,
                                     @Param("documentNumber") String documentNumber,
                                     Pageable pageable);
}
