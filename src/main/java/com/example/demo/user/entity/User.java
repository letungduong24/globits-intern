package com.example.demo.user.entity;

import com.example.demo.person.entity.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name = "users")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Column(unique = true, nullable = false)
    String email;
    
    @Column(nullable = false)
    String password;
    
    @Column(name = "is_active")
    boolean isActive;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Person person;
}
