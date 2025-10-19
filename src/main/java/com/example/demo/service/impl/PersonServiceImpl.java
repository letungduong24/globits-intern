package com.example.demo.service.impl;

import com.example.demo.repositories.CompanyRepository;
import com.example.demo.domain.Company;
import com.example.demo.person.dto.*;
import com.example.demo.person.dto.request.AssignCompanyDto;
import com.example.demo.person.dto.request.CreatePersonDto;
import com.example.demo.person.dto.request.UpdatePersonDto;
import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.service.PersonService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.domain.Person;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.service.FileUploadService;
import com.example.demo.shared.util.PagedUtil;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final FileUploadService fileUploadService;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, UserRepository userRepository, CompanyRepository companyRepository, FileUploadService fileUploadService) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personMapper.toDTOs(personRepository.findAll());
    }

    @Override
    public PagedResponse<PersonDto> getAllPersons(Pageable pageable){
        Page<Person> page = personRepository.findAll(pageable);
        List<PersonDto> mappedContent = personMapper.toDTOs(page.getContent());

        return PagedUtil.ToPagedResponse(page, mappedContent);
    }

    @Override
    public PersonDto getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        return personMapper.toDTO(person);
    }

    @Override
    public PersonDto getPersonByPhoneNumber(String phoneNumber) {
        Person person = personRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        return personMapper.toDTO(person);
    }

    @Override
    public PersonDto getPersonByUserId(Long userId) {
        Person person = personRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        return personMapper.toDTO(person);
    }

    @Override
    public List<PersonDto> getPersonsByName(String name) {
        return personMapper.toDTOs(personRepository.findByFullNameContainingIgnoreCase(name));
    }

    @Override
    public List<PersonDto> getPersonsByCompanyId(Long companyId) {
        return personMapper.toDTOs(personRepository.findByCompanyId(companyId));
    }

    @Override
    public List<PersonDto> getPersonsByProjectId(Long projectId) {
        return personMapper.toDTOs(personRepository.findByProjectsId(projectId));
    }

    @Override
    public PersonDto createPerson(CreatePersonDto personDto) {
        if (personDto.getPhoneNumber() != null && personRepository.existsByPhoneNumber(personDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Số điện thoại đã tồn tại");
        }

        Person person = personMapper.toEntity(personDto);

        if (personDto.getUserId() != null) {
            User user = userRepository.findById(personDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
            if(user.getPerson() != null) {
                throw new DuplicateResourceException("User đã có person");
            }
            person.setUser(user);
        }

        Person saved = personRepository.save(person);
        return personMapper.toDTO(saved);
    }

    @Override
    public PersonDto updatePerson(UpdatePersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        
        personMapper.updateEntity(person, personDto);
        Person saved = personRepository.save(person);
        return personMapper.toDTO(saved);
    }

    @Override
    public PersonDto deletePersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        PersonDto dto = personMapper.toDTO(person);
        personRepository.delete(person);
        return dto;
    }

    @Override
    public PersonDto assignCompany(AssignCompanyDto dto) {
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy company"));
        if(person.getCompany() != null && Objects.equals(person.getCompany().getId(), dto.getCompanyId())) {
            throw new DuplicateResourceException("Person đã company này");
        }
        person.setCompany(company);
        return personMapper.toDTO(personRepository.save(person));
    }

    @Override
    public String uploadAvatar(Long personId, MultipartFile file) {
        try {
            // Find person
            Person person = personRepository.findById(personId)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person với ID: " + personId));

            // Delete old avatar if exists
            if (person.getAvatar() != null && !person.getAvatar().isEmpty()) {
                fileUploadService.deleteAvatar(person.getAvatar());
            }

            // Upload new avatar
            String avatarPath = fileUploadService.uploadAvatar(file);
            
            // Update person with new avatar path
            person.setAvatar(avatarPath);
            personRepository.save(person);

            return avatarPath;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi upload avatar: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeAvatar(Long personId) {
        // Find person
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person với ID: " + personId));

        // Delete avatar file if exists
        if (person.getAvatar() != null && !person.getAvatar().isEmpty()) {
            fileUploadService.deleteAvatar(person.getAvatar());
            person.setAvatar(null);
            personRepository.save(person);
        }
    }
}
