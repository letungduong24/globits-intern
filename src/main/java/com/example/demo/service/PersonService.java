package com.example.demo.service;

import com.example.demo.dto.PersonDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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
    PersonDto createPerson(PersonDto person);
    PersonDto updatePerson(PersonDto person);
    PersonDto deletePersonById(Long id);
    PersonDto assignCompany(Long personId, Long companyId);
    String uploadAvatar(Long personId, MultipartFile file);
    void removeAvatar(Long personId);
}
