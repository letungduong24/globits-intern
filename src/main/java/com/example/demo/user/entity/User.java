package com.example.demo.user.entity;

import com.example.demo.person.entity.Person;
import com.example.demo.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
    @Builder.Default
    boolean isActive = true;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    Person person;

    @ManyToMany(fetch =  FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles =  new HashSet<>();

}
