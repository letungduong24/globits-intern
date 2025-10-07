package com.example.demo.person.dto;

import lombok.*;

import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonBasicDto {
    Long id;
    String fullName;
    String gender;
    LocalDate birthDate;
    String phoneNumber;
    String address;
}
