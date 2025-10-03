package com.example.usuariosprimacapi.User.dto;

import com.example.usuariosprimacapi.User.domain.State;
import com.example.usuariosprimacapi.User.domain.rol;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDto {
    private String username;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    
    private rol role;
    
    private Integer phone;
    
    private String street;
    
    private String city;
    
    private State state;
}