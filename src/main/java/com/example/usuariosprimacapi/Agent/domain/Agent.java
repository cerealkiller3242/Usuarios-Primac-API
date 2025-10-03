package com.example.usuariosprimacapi.Agent.domain;

import com.example.usuariosprimacapi.User.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agents")
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isActive = true;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}