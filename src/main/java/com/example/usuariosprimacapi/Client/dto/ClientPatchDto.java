package com.example.usuariosprimacapi.Client.dto;

import com.example.usuariosprimacapi.User.domain.State;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPatchDto {
    private String username;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    
    private Integer phone;
    
    private String street;
    
    private String city;
    
    private State state;
    
    private String firstName;
    
    private String lastName;
    
    private String documentType;
    
    // Note: documentNumber and birthDate typically should not be updatable
    // but including them in case business rules allow it
    private String documentNumber;
    
    private Date birthDate;
}