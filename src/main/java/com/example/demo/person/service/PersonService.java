package com.example.demo.person.service;

import com.example.demo.person.dto.*;

import java.util.List;

public interface PersonService {
    List<ResponsePersonDto> getAllPersons();
    List<ResponsePersonDto> getPersonsByName(String name);
    List<ResponsePersonDto> getPersonsByCompanyId(Long companyId);
    List<PersonBasicDto> getPersonsByProjectId(Long projectId);
    ResponsePersonDto getPersonById(Long id);
    ResponsePersonDto getPersonByPhoneNumber(String phoneNumber);
    ResponsePersonDto getPersonByUserId(Long userId);
    ResponsePersonDto createPerson(CreatePersonDto person);
    ResponsePersonDto updatePerson(UpdatePersonDto person);
    ResponsePersonDto deletePersonById(Long id);
    ResponsePersonDto assignCompany(AssignCompanyDto dto);
    boolean existsPersonById(Long id);
    boolean existsPersonByPhoneNumber(String phoneNumber);
}
