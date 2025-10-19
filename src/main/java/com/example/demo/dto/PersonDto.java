package com.example.demo.dto;

import com.example.demo.domain.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto {
    Long id;
    
    @NotBlank(message = "Họ tên không được để trống")
    String fullName;
    
    String gender;
    
    LocalDate birthDate;
    
    String phoneNumber;
    
    String address;
    
    String avatar;
    
    @NotNull(message = "ID người dùng không được để trống")
    Long userId;
    
    Long companyId;
    String companyName;

    /**
     * Constructor cho HQL queries
     */
    public PersonDto(Long id, String fullName, String gender, LocalDate birthDate, String phoneNumber, String address, String avatar, Long userId, Long companyId, String companyName, boolean forHql) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
        this.userId = userId;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Person toEntity() {
        return Person.builder()
                .id(this.id)
                .fullName(this.fullName)
                .gender(this.gender)
                .birthDate(this.birthDate)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .avatar(this.avatar)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static PersonDto fromEntity(Person person) {
        if (person == null) {
            return null;
        }
        
        return PersonDto.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .gender(person.getGender())
                .birthDate(person.getBirthDate())
                .phoneNumber(person.getPhoneNumber())
                .address(person.getAddress())
                .avatar(person.getAvatar())
                .userId(person.getUser() != null ? person.getUser().getId() : null)
                .companyId(person.getCompany() != null ? person.getCompany().getId() : null)
                .companyName(person.getCompany() != null ? person.getCompany().getName() : null)
                .build();
    }
}
