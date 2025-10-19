package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor()
@AllArgsConstructor()
@Table(name = "persons")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
    Long id;
    
    @Column(name = "full_name", nullable = false)
    String fullName;
    
    String gender;
    
    @Column(name = "birth_date")
    LocalDate birthDate;
    
    @Column(name = "phone_number")
    String phoneNumber;
    
    String address;
    
    @Column(name = "avatar")
    String avatar;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Company company;

    @ManyToMany(mappedBy = "persons", fetch = FetchType.LAZY)
    Set<Project> projects;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    Set<Task> tasks;
}
