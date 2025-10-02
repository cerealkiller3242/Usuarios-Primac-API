package com.example.usuariosprimacapi.Beneficiary.infrastructure;


import com.example.usuariosprimacapi.Beneficiary.domain.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
}
