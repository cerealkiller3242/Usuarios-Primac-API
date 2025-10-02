package com.example.usuariosprimacapi.Beneficiary.dto;

import com.example.usuariosprimacapi.Beneficiary.domain.Relationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryResponseDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String document_type;
    private String document_number;
    private LocalDateTime birth_date;
    private Relationship relationship;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
}