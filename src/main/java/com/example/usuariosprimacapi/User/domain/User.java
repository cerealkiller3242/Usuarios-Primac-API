package com.example.usuariosprimacapi.User.domain;

import com.example.usuariosprimacapi.Agent.domain.Agent;
import com.example.usuariosprimacapi.Client.domain.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private rol role;

    @Column(nullable = false, unique = true)
    private int phone;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Client client;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Agent> agents;
}
