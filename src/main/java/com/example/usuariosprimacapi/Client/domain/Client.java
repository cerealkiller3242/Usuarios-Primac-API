package com.example.usuariosprimacapi.Client.domain;
import com.example.usuariosprimacapi.User.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
@Builder
public class Client {
    @Id
    @Column(name = "user_id")
    private Long userId; // This will be the same as the User's ID
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String documentType;
    
    @Column(nullable = false, unique = true)
    private String documentNumber;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
