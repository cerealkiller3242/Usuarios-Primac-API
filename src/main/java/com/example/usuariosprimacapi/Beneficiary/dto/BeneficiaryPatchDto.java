package com.example.usuariosprimacapi.Beneficiary.dto;

import com.example.usuariosprimacapi.Beneficiary.domain.Relationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryPatchDto {
    private String firstName;
    
    private String lastName;
    
    private String documentType;
    
    private String documentNumber;
    
    private Date birthDate;
    
    private Relationship relationship;
    
    private Long clientId;
}