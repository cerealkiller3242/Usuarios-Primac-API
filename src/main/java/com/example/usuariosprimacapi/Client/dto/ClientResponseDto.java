package com.example.usuariosprimacapi.Client.dto;

import com.example.usuariosprimacapi.Client.domain.DocumentType;
import com.example.usuariosprimacapi.User.domain.rol;
import com.example.usuariosprimacapi.User.domain.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {
    private Long id;
    private String username;
    private String email;
    private rol role;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String street;
    private String city;
    private State state;
    private String firstName;
    private String lastName;
    private DocumentType documentType;
    private String documentNumber;
    private LocalDate birthDate;
}