package com.example.demo.company.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDto {
    Long id;
    String name;
    String address;
    String code;
}
