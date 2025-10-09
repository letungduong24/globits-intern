package com.example.demo.person.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePersonDto {
    @NotNull(message = "ID không được để trống")
    Long id;
    @Size(min = 2, message = "Tên phải tối thiểu 2 ký tự")
    String fullName;
    String gender;
    LocalDate birthDate;
    String phoneNumber;
    String address;
}
