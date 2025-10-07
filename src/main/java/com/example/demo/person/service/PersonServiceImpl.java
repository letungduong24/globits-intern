package com.example.demo.person.service;

import com.example.demo.shared.Exception.DuplicateResourceException;
import com.example.demo.shared.Exception.ResourceNotFoundException;
import com.example.demo.person.dto.CreatePersonDto;
import com.example.demo.person.dto.ResponsePersonDto;
import com.example.demo.person.dto.UpdatePersonDto;
import com.example.demo.person.entity.Person;
import com.example.demo.person.PersonRepository;
import com.example.demo.person.dto.PersonMapper;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final UserRepository userRepository;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ResponsePersonDto> getAllPersons() {
        return personMapper.toDTOs(personRepository.findAll());
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
    public ResponsePersonDto createPerson(CreatePersonDto personDto) {
        if (personDto.getPhoneNumber() != null && personRepository.existsByPhoneNumber(personDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Số điện thoại đã tồn tại");
        }
        
        Person person = personMapper.toEntity(personDto);
        
        if (personDto.getUserId() != null) {
            User user = userRepository.findById(personDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
            person.setUser(user);
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
    public boolean existsPersonById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public boolean existsPersonByPhoneNumber(String phoneNumber) {
        return personRepository.existsByPhoneNumber(phoneNumber);
    }
}
