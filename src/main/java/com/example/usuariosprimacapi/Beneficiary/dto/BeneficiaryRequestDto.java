package com.example.usuariosprimacapi.Beneficiary.dto;

import com.example.usuariosprimacapi.Beneficiary.domain.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryRequestDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Document type is required")
    private String documentType;

    @NotBlank(message = "Document number is required")
    private String documentNumber;

    @NotNull(message = "Birth date is required")
    private Date birthDate;

    @NotNull(message = "Relationship is required")
    private Relationship relationship;

    @NotNull(message = "Client ID is required")
    private Long clientId;
}