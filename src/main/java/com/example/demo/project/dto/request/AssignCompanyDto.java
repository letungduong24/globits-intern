package com.example.demo.project.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignCompanyDto {
    @NotNull(message = "Project ID không được để trống")
    Long projectId;
    @NotNull(message = "Company ID không được để trống")
    Long companyId;
}

