package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor()
@AllArgsConstructor()
@Table(name = "companies")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Column(nullable = false)
    String name;
    
    String address;
    
    @Column(unique = true, nullable = false)
    String code;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    Set<Person> persons;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    Set<Department> departments;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    Set<Project> projects;
}