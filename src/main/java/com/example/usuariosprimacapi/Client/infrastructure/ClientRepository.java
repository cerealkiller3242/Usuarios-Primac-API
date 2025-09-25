package com.example.usuariosprimacapi.Client.infrastructure;

import com.example.usuariosprimacapi.Client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
