package com.example.usuariosprimacapi.Client.dto;

import com.example.usuariosprimacapi.Client.domain.DocumentType;
import com.example.usuariosprimacapi.User.domain.State;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;
    
    @NotNull(message = "Document type is required")
    private DocumentType documentType;
    
    @NotBlank(message = "Document number is required")
    @Size(max = 20, message = "Document number must not exceed 20 characters")
    private String documentNumber;
    
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @NotNull(message = "State is required")
    private State state;
}