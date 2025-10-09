package com.example.demo.project.dto.response;

import com.example.demo.company.dto.response.CompanyDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicProjectDto {
    Long id;
    String name;
    String code;
    CompanyDto company;
}

