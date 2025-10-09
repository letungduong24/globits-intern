package com.example.demo.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCompanyDto {
    @NotBlank(message = "Tên công ty không được để trống")
    String name;
    
    String address;
    
    @NotBlank(message = "Mã công ty không được để trống")
    String code;
}
