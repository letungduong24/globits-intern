package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor()
@AllArgsConstructor()
@Table(name = "projects")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(unique = true, nullable = false)
    String code;
    @Column(columnDefinition = "TEXT")
    String description;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Company company;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "project"),
            inverseJoinColumns = @JoinColumn(name = "person")
    )
    @Builder.Default
    Set<Person> persons = new HashSet<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    Set<Task> tasks;
}
