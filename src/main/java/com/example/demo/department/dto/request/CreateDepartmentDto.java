package com.example.demo.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDepartmentDto {
    @NotBlank(message = "Mã phòng ban không được để trống")
    String code;
    @NotBlank(message = "Tên phòng ban không được để trống")
    String name;
    Long parentId;
    Long companyId;
}
