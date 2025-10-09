package com.example.demo.country.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCountryDto {
    @NotNull(message = "ID không được để trống")
    Long id;
    String name;
    String description;
    String code;
}
