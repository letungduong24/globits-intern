package com.example.demo.person.dto;

import com.example.demo.person.entity.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    public ResponsePersonDto toDTO(Person person) {
        if (person == null) {
            return null;
        }
        
        return ResponsePersonDto.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .gender(person.getGender())
                .birthDate(person.getBirthDate())
                .phoneNumber(person.getPhoneNumber())
                .address(person.getAddress())
                .userId(person.getUser() != null ? person.getUser().getId() : null)
                .build();
    }

    public List<ResponsePersonDto> toDTOs(List<Person> persons) {
        if (persons == null) {
            return null;
        }
        
        return persons.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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

    public PersonBasicDto toBasicDTO(Person person) {
        if (person == null) {
            return null;
        }
        
        return PersonBasicDto.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .gender(person.getGender())
                .birthDate(person.getBirthDate())
                .phoneNumber(person.getPhoneNumber())
                .address(person.getAddress())
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