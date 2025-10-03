package com.example.usuariosprimacapi.User.infrastructure;

import com.example.usuariosprimacapi.User.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    // Paginated query for large datasets
    @Query("SELECT u FROM User u ORDER BY u.id")
    Page<User> findAllPaginated(Pageable pageable);
    
    // Count query for pagination
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();
}
