package com.example.usuariosprimacapi.Beneficiary.domain;

import com.example.usuariosprimacapi.User.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Beneficiaries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "beneficiary_id")
    private Long id;
    @Column(nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private String document_type;
    @Column(nullable = false, unique = true)
    private String document_number;
    @Column(nullable = false)
    private LocalDateTime birth_date;
    @Column(nullable = false)
    private Relationship relationship;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;
}
