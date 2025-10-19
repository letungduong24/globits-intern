package com.example.demo.service.impl;

import com.example.demo.repositories.CompanyRepository;
import com.example.demo.domain.Company;
import com.example.demo.dto.PersonDto;
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
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final FileUploadService fileUploadService;

    public PersonServiceImpl(PersonRepository personRepository, UserRepository userRepository, CompanyRepository companyRepository, FileUploadService fileUploadService) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personRepository.findAllAsDto();
    }

    @Override
    public PagedResponse<PersonDto> getAllPersons(Pageable pageable){
        Page<Person> page = personRepository.findAll(pageable);
        List<PersonDto> mappedContent = personRepository.findAllAsDto();

        return PagedUtil.ToPagedResponse(page, mappedContent);
    }

    @Override
    public PersonDto getPersonById(Long id) {
        return personRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
    }

    @Override
    public PersonDto getPersonByPhoneNumber(String phoneNumber) {
        return personRepository.findByPhoneNumberAsDto(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
    }

    @Override
    public PersonDto getPersonByUserId(Long userId) {
        return personRepository.findByUserIdAsDto(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
    }

    @Override
    public List<PersonDto> getPersonsByName(String name) {
        return personRepository.findByFullNameContainingIgnoreCaseAsDto(name);
    }

    @Override
    public List<PersonDto> getPersonsByCompanyId(Long companyId) {
        return personRepository.findByCompanyIdAsDto(companyId);
    }

    @Override
    public List<PersonDto> getPersonsByProjectId(Long projectId) {
        return personRepository.findByProjectsIdAsDto(projectId);
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        if (personDto.getPhoneNumber() != null && personRepository.existsByPhoneNumber(personDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Số điện thoại đã tồn tại");
        }

        Person person = personDto.toEntity();

        if (personDto.getUserId() != null) {
            User user = userRepository.findById(personDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
            if(user.getPerson() != null) {
                throw new DuplicateResourceException("User đã có person");
            }
            person.setUser(user);
        }

        Person saved = personRepository.save(person);
        return PersonDto.fromEntity(saved);
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        
        // Cập nhật các trường
        if (personDto.getFullName() != null) {
            person.setFullName(personDto.getFullName());
        }
        if (personDto.getGender() != null) {
            person.setGender(personDto.getGender());
        }
        if (personDto.getBirthDate() != null) {
            person.setBirthDate(personDto.getBirthDate());
        }
        if (personDto.getPhoneNumber() != null) {
            person.setPhoneNumber(personDto.getPhoneNumber());
        }
        if (personDto.getAddress() != null) {
            person.setAddress(personDto.getAddress());
        }
        if (personDto.getAvatar() != null) {
            person.setAvatar(personDto.getAvatar());
        }
        
        Person saved = personRepository.save(person);
        return PersonDto.fromEntity(saved);
    }

    @Override
    public PersonDto deletePersonById(Long id) {
        PersonDto dto = personRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        
        personRepository.deleteById(id);
        return dto;
    }

    @Override
    public PersonDto assignCompany(Long personId, Long companyId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy company"));
        if(person.getCompany() != null && Objects.equals(person.getCompany().getId(), companyId)) {
            throw new DuplicateResourceException("Person đã company này");
        }
        person.setCompany(company);
        return PersonDto.fromEntity(personRepository.save(person));
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
