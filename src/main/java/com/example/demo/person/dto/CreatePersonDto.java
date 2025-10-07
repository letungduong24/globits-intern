package com.example.demo.person.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePersonDto {
    @NotBlank(message = "Họ tên không được để trống")
    String fullName;
    
    String gender;
    
    LocalDate birthDate;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    String phoneNumber;
    
    String address;

    @NotNull(message = "ID không được để trống")
    Long userId;
}
