package com.example.demo.role.entity;

import com.example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name = "roles")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String role;
    @Column(columnDefinition = "TEXT")
    String description;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    Set<User> users;
}
