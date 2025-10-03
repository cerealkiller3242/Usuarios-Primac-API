package com.example.usuariosprimacapi.Agent.dto;

import com.example.usuariosprimacapi.User.domain.State;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentPatchDto {
    private String username;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    
    private Integer phone;
    
    private String street;
    
    private String city;
    
    private State state;
    
    private String code;
    
    private String firstName;
    
    private String lastName;
    
    private Boolean isActive;
}