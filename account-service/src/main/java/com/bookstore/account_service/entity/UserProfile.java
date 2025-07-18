package com.bookstore.account_service.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    private String fullName;

    private String address;
    private String phone;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String imageKey;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
