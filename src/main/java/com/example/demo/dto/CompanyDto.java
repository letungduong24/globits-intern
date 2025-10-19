package com.example.demo.dto;

import com.example.demo.domain.Company;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDto {
    Long id;
    
    @NotBlank(message = "Tên công ty không được để trống")
    String name;
    
    String address;
    
    @NotBlank(message = "Mã công ty không được để trống")
    String code;

    /**
     * Constructor cho HQL queries
     */
    public CompanyDto(Long id, String name, String address, String code, boolean forHql) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.code = code;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Company toEntity() {
        return Company.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .code(this.code)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static CompanyDto fromEntity(Company company) {
        if (company == null) {
            return null;
        }
        
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .code(company.getCode())
                .build();
    }
}
