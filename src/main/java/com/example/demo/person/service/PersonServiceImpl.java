package com.example.demo.person.service;

import com.example.demo.company.CompanyRepository;
import com.example.demo.company.entity.Company;
import com.example.demo.person.dto.*;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.person.entity.Person;
import com.example.demo.person.PersonRepository;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import com.example.demo.user.entity.User;
import com.example.demo.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, UserRepository userRepository, CompanyRepository companyRepository) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<ResponsePersonDto> getAllPersons() {
        return personMapper.toDTOs(personRepository.findAll());
    }

    @Override
    public PagedResponse<ResponsePersonDto> getAllPersons(Pageable pageable){
        Page<Person> page = personRepository.findAll(pageable);
        List<ResponsePersonDto> mappedContent = personMapper.toDTOs(page.getContent());

        return PagedUtil.ToPagedResponse(page, mappedContent);
    }

    @Override
    public ResponsePersonDto getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        return personMapper.toDTO(person);
    }

    @Override
    public ResponsePersonDto getPersonByPhoneNumber(String phoneNumber) {
        Person person = personRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        return personMapper.toDTO(person);
    }

    @Override
    public ResponsePersonDto getPersonByUserId(Long userId) {
        Person person = personRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        return personMapper.toDTO(person);
    }

    @Override
    public List<ResponsePersonDto> getPersonsByName(String name) {
        return personMapper.toDTOs(personRepository.findByFullNameContainingIgnoreCase(name));
    }

    @Override
    public List<ResponsePersonDto> getPersonsByCompanyId(Long companyId) {
        return personMapper.toDTOs(personRepository.findByCompanyId(companyId));
    }

    @Override
    public List<PersonBasicDto> getPersonsByProjectId(Long projectId) {
        return personMapper.toBasicDTOs(personRepository.findByProjectsId(projectId));
    }

    @Override
    public ResponsePersonDto createPerson(CreatePersonDto personDto) {
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

        if (personDto.getCompanyId() != null) {
            Company company = companyRepository.findById(personDto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy company"));
            person.setCompany(company);
        }
        
        Person saved = personRepository.save(person);
        return personMapper.toDTO(saved);
    }

    @Override
    public ResponsePersonDto updatePerson(UpdatePersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        if (personDto.getPhoneNumber() != null && !personDto.getPhoneNumber().equals(person.getPhoneNumber())) {
            if (personRepository.existsByPhoneNumber(personDto.getPhoneNumber())) {
                throw new DuplicateResourceException("Số điện thoại đã tồn tại");
            }
        }
        
        personMapper.updateEntity(person, personDto);
        Person saved = personRepository.save(person);
        return personMapper.toDTO(saved);
    }

    @Override
    public ResponsePersonDto deletePersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        ResponsePersonDto dto = personMapper.toDTO(person);
        personRepository.delete(person);
        return dto;
    }

    @Override
    public ResponsePersonDto assignCompany(AssignCompanyDto dto) {
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
    public boolean existsPersonById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public boolean existsPersonByPhoneNumber(String phoneNumber) {
        return personRepository.existsByPhoneNumber(phoneNumber);
    }
}
