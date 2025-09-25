package com.example.usuariosprimacapi.address.infrastructure;

import com.example.usuariosprimacapi.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
