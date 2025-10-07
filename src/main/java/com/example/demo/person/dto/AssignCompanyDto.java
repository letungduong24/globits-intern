package com.example.demo.person.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignCompanyDto {
    @NotNull(message = "Person ID không được để trống")
    Long personId;
    @NotNull(message = "Comapny ID không được để trống")
    Long companyId;
}
