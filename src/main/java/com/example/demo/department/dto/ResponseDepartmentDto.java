package com.example.demo.department.dto;

import com.example.demo.company.entity.Company;
import com.example.demo.department.entity.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDepartmentDto  {
    Long id;
    String name;
    String code;
    Long parentId;
    Long companyId;
}
