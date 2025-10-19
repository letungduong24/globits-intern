package com.example.demo.person.dto;

import com.example.demo.person.dto.request.CreatePersonDto;
import com.example.demo.person.dto.request.UpdatePersonDto;
import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.domain.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    public PersonDto toDTO(Person person) {
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

    public List<PersonDto> toDTOs(List<Person> persons) {
        if (persons == null) {
            return null;
        }
        
        return persons.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Set<PersonDto> toDTOs(Set<Person> persons) {
        if (persons == null) {
            return null;
        }

        return persons.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Person toEntity(CreatePersonDto personDto) {
        if (personDto == null) {
            return null;
        }
        
        return Person.builder()
                .fullName(personDto.getFullName())
                .gender(personDto.getGender())
                .birthDate(personDto.getBirthDate())
                .phoneNumber(personDto.getPhoneNumber())
                .address(personDto.getAddress())
                .build();
    }

    public Person updateEntity(Person existingPerson, UpdatePersonDto updateDto) {
        if (existingPerson == null || updateDto == null) {
            return existingPerson;
        }
        
        if (updateDto.getFullName() != null) {
            existingPerson.setFullName(updateDto.getFullName());
        }
        if (updateDto.getGender() != null) {
            existingPerson.setGender(updateDto.getGender());
        }
        if (updateDto.getBirthDate() != null) {
            existingPerson.setBirthDate(updateDto.getBirthDate());
        }
        if (updateDto.getPhoneNumber() != null) {
            existingPerson.setPhoneNumber(updateDto.getPhoneNumber());
        }
        if (updateDto.getAddress() != null) {
            existingPerson.setAddress(updateDto.getAddress());
        }
        
        return existingPerson;
    }
}