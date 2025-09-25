package com.example.usuariosprimacapi.Agent.domain;

import com.example.usuariosprimacapi.User.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Agents")
@Builder
public class Agent extends User {
    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, columnDefinition = "boolean default true")
    boolean isActive;
}
