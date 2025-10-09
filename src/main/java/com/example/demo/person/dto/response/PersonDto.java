package com.example.demo.person.dto.response;

import lombok.*;

import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto {
    Long id;
    String fullName;
    String gender;
    LocalDate birthDate;
    String phoneNumber;
    String address;
    Long userId;
    Long companyId;
    String companyName;
}

