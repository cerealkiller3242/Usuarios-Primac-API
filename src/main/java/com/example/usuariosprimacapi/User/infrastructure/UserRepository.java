package com.example.usuariosprimacapi.User.infrastructure;

import com.example.usuariosprimacapi.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
