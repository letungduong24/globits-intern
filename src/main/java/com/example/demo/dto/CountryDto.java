package com.example.demo.dto;

import com.example.demo.domain.Country;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryDto {
    Long id;
    
    @NotBlank(message = "Tên quốc gia không được để trống")
    String name;
    
    String description;
    
    @NotBlank(message = "Mã quốc gia không được để trống")
    String code;

    /**
     * Constructor cho HQL queries
     */
    public CountryDto(Long id, String name, String description, String code, boolean forHql) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Country toEntity() {
        return Country.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .code(this.code)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static CountryDto fromEntity(Country country) {
        if (country == null) {
            return null;
        }
        
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .description(country.getDescription())
                .code(country.getCode())
                .build();
    }
}
