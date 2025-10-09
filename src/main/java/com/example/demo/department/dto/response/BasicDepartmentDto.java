package com.example.demo.department.dto.response;

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
