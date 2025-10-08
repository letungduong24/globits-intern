package com.example.demo.person.entity;

import com.example.demo.company.entity.Company;
import com.example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
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
    
    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;
    
    String address;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Company company;
}
