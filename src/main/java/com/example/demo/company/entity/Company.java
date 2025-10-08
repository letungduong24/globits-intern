package com.example.demo.company.entity;

import com.example.demo.department.entity.Department;
import com.example.demo.person.entity.Person;
import com.example.demo.project.entity.Project;
import com.example.demo.user.entity.User;
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