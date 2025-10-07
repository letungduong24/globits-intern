package com.example.demo.person;

import com.example.demo.person.dto.CreatePersonDto;
import com.example.demo.person.dto.ResponsePersonDto;
import com.example.demo.person.dto.UpdatePersonDto;
import com.example.demo.person.service.PersonService;
import com.example.demo.shared.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<ResponsePersonDto> getPersons(){
        return personService.getAllPersons();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResponsePersonDto>> createPerson(@Valid @RequestBody CreatePersonDto person){
        ResponsePersonDto created = personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Tạo person thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponsePersonDto>> updatePerson(@Valid @RequestBody UpdatePersonDto personDto){
        ResponsePersonDto updated = personService.updatePerson(personDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Cập nhật person thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponsePersonDto>> deletePersonById(@PathVariable Long id) {
        ResponsePersonDto deleted = personService.deletePersonById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Xóa person thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponsePersonDto getPersonById(@PathVariable Long id){
        return personService.getPersonById(id);
    }

    @GetMapping("/{name}/by-name")
    public List<ResponsePersonDto> getPersonsByName(@PathVariable String name){
        return personService.getPersonsByName(name);
    }

    @GetMapping("/{phoneNumber}/by-phone")
    public ResponsePersonDto getPersonByPhoneNumber(@PathVariable String phoneNumber){
        return personService.getPersonByPhoneNumber(phoneNumber);
    }

    @GetMapping("/{userId}/by-user")
    public ResponsePersonDto getPersonByUserId(@PathVariable Long userId){
        return personService.getPersonByUserId(userId);
    }
}
