package com.example.usuariosprimacapi.Beneficiary.dto;

import com.example.usuariosprimacapi.Beneficiary.domain.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryRequestDto {
    @NotBlank(message = "First name is required")
    private String first_name;

    @NotBlank(message = "Last name is required")
    private String last_name;

    @NotBlank(message = "Document type is required")
    private String document_type;

    @NotBlank(message = "Document number is required")
    private String document_number;

    @NotNull(message = "Birth date is required")
    private LocalDateTime birth_date;

    @NotNull(message = "Relationship is required")
    private Relationship relationship;

    @NotNull(message = "User ID is required")
    private Long userId;
}