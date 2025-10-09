package com.example.demo.person.service;

import com.example.demo.person.dto.request.AssignCompanyDto;
import com.example.demo.person.dto.request.CreatePersonDto;
import com.example.demo.person.dto.request.UpdatePersonDto;
import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    List<PersonDto> getAllPersons();
    PagedResponse<PersonDto> getAllPersons(Pageable pageable);
    List<PersonDto> getPersonsByName(String name);
    List<PersonDto> getPersonsByCompanyId(Long companyId);
    List<PersonDto> getPersonsByProjectId(Long projectId);
    PersonDto getPersonById(Long id);
    PersonDto getPersonByPhoneNumber(String phoneNumber);
    PersonDto getPersonByUserId(Long userId);
    PersonDto createPerson(CreatePersonDto person);
    PersonDto updatePerson(UpdatePersonDto person);
    PersonDto deletePersonById(Long id);
    PersonDto assignCompany(AssignCompanyDto dto);
}
