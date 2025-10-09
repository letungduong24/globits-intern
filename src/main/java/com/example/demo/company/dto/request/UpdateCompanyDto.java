package com.example.demo.company.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCompanyDto {
    @NotNull(message = "ID không được để trống")
    Long id;
    
    String name;
    String address;
    String code;
}
