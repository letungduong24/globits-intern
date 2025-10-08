package com.example.demo.project.dto;

import com.example.demo.company.entity.Company;
import com.example.demo.person.entity.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ResponseProjectDto {
    Long id;
    String name;
    String code;
    String description;
    Long companyId;
}
