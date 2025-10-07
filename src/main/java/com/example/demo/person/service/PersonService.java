package com.example.demo.person.service;

import com.example.demo.person.dto.CreatePersonDto;
import com.example.demo.person.dto.ResponsePersonDto;
import com.example.demo.person.dto.UpdatePersonDto;

import java.util.List;

public interface PersonService {
    List<ResponsePersonDto> getAllPersons();
    List<ResponsePersonDto> getPersonsByName(String name);
    ResponsePersonDto getPersonById(Long id);
    ResponsePersonDto getPersonByPhoneNumber(String phoneNumber);
    ResponsePersonDto getPersonByUserId(Long userId);
    ResponsePersonDto createPerson(CreatePersonDto person);
    ResponsePersonDto updatePerson(UpdatePersonDto person);
    ResponsePersonDto deletePersonById(Long id);
    boolean existsPersonById(Long id);
    boolean existsPersonByPhoneNumber(String phoneNumber);
}
