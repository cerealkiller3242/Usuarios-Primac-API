package com.example.usuariosprimacapi.Client.dto;

import com.example.usuariosprimacapi.Client.domain.DocumentType;
import com.example.usuariosprimacapi.User.domain.State;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPatchDto {
    private Long userId;
    
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;
    
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;
    
    private DocumentType documentType;
    
    @Size(max = 20, message = "Document number must not exceed 20 characters")
    private String documentNumber;
    
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    // User fields for partial updates
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;
    
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
    
    @Size(max = 100, message = "Street must not exceed 100 characters")
    private String street;
    
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;
    
    private State state;
}