package com.example.usuariosprimacapi.Client.domain;
import com.example.usuariosprimacapi.User.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
@Builder
public class Client extends User {
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    String documentType;
    @Column(nullable = false, unique = true, updatable = false)
    String documentNumber;
    @Column(nullable = false, updatable = false)
    Date birthDate;
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;
    @Column(nullable = false)
    LocalDateTime updatedAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
