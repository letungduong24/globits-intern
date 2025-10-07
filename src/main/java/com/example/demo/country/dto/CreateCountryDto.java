package com.example.demo.country.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCountryDto {
    @NotBlank(message = "Tên không được để trống")
    String name;
    String description;
    @NotBlank(message = "Code không được để trống")
    String code;
}
