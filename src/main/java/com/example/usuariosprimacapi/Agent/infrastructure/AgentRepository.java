package com.example.usuariosprimacapi.Agent.infrastructure;

import com.example.usuariosprimacapi.Agent.domain.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    
    @Query("SELECT a FROM Agent a JOIN FETCH a.user")
    List<Agent> findAllWithUser();
    
    @Query("SELECT a FROM Agent a JOIN FETCH a.user WHERE a.id = :id")
    Optional<Agent> findByIdWithUser(Long id);
    
    @Query(value = "SELECT a FROM Agent a JOIN FETCH a.user",
           countQuery = "SELECT COUNT(a) FROM Agent a")
    Page<Agent> findAllWithUser(Pageable pageable);
    
    @Query(value = "SELECT a FROM Agent a JOIN FETCH a.user WHERE " +
           "(:firstName IS NULL OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:code IS NULL OR LOWER(a.code) LIKE LOWER(CONCAT('%', :code, '%')))",
           countQuery = "SELECT COUNT(a) FROM Agent a WHERE " +
           "(:firstName IS NULL OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:code IS NULL OR LOWER(a.code) LIKE LOWER(CONCAT('%', :code, '%')))")
    Page<Agent> findBySearchCriteria(@Param("firstName") String firstName,
                                    @Param("lastName") String lastName,
                                    @Param("code") String code,
                                    Pageable pageable);
}
