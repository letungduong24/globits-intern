package com.example.demo.department.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignCompanyDto {
    @NotNull(message = "Department ID không được để trống")
    Long departmentId;
    @NotNull(message = "Company ID không được để trống")
    Long companyId;
}

