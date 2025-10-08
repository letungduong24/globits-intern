package com.example.demo.country.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor()
@AllArgsConstructor()
@Table(name = "countries")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(columnDefinition = "TEXT")
    String description;
    @Column(unique = true, nullable = false)
    String code;
}
