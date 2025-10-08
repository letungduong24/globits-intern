package com.example.demo.department.dto;

import com.example.demo.company.entity.Company;
import com.example.demo.department.entity.Department;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicDepartmentDto {
    Long id;
    String name;
    String code;
}
