package com.example.usuariosprimacapi.Beneficiary.domain;

import com.example.usuariosprimacapi.Client.domain.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "beneficiaries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String documentType;
    
    @Column(nullable = false, unique = true)
    private String documentNumber;
    
    @Column(nullable = false)
    private Date birthDate;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "user_id")
    private Client client;
}
