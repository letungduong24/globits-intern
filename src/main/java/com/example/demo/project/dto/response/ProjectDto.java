package com.example.demo.project.dto.response;

import com.example.demo.company.dto.response.CompanyDto;
import com.example.demo.person.dto.response.PersonDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProjectDto {
    Long id;
    String name;
    String code;
    String description;
    CompanyDto company;
    Set<PersonDto> persons;
}
