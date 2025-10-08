package com.example.demo.person;

import com.example.demo.country.dto.ResponseCountryDto;
import com.example.demo.person.dto.AssignCompanyDto;
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
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ResponsePersonDto>>> getPersons(){
        List<ResponsePersonDto> countries = personService.getAllPersons();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponsePersonDto>>builder()
                        .success(true)
                        .message("Lấy persons thành công")
                        .data(countries)
                        .build());
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

    @PutMapping("/assign-company")
    public ResponseEntity<ApiResponse<ResponsePersonDto>> assignCompany(@Valid @RequestBody AssignCompanyDto assignCompanyDto){
        ResponsePersonDto assigned = personService.assignCompany(assignCompanyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Gán công ty thành công")
                        .data(assigned)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponsePersonDto>> getPersonById(@PathVariable Long id){
        ResponsePersonDto person = personService.getPersonById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Lấy person thành công")
                        .data(person)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<ResponsePersonDto>>> getPersonsByName(@PathVariable String name){
        List<ResponsePersonDto> persons = personService.getPersonsByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponsePersonDto>>builder()
                        .success(true)
                        .message("Lấy persons thành công")
                        .data(persons)
                        .build());
    }

    @GetMapping("/{phoneNumber}/by-phone")
    public ResponseEntity<ApiResponse<ResponsePersonDto>> getPersonByPhoneNumber(@PathVariable String phoneNumber){
        ResponsePersonDto person = personService.getPersonByPhoneNumber(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Lấy person thành công")
                        .data(person)
                        .build());
    }

    @GetMapping("/{userId}/by-user")
    public ResponseEntity<ApiResponse<ResponsePersonDto>> getPersonByUserId(@PathVariable Long userId){
        ResponsePersonDto person = personService.getPersonByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePersonDto>builder()
                        .success(true)
                        .message("Lấy person thành công")
                        .data(person)
                        .build());
    }
}
