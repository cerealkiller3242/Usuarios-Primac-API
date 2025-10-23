package com.example.usuariosprimacapi.User.dto;

import com.example.usuariosprimacapi.User.domain.State;
import com.example.usuariosprimacapi.User.domain.rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
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
}
