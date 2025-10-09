package com.example.demo.person;

import com.example.demo.person.dto.request.AssignCompanyDto;
import com.example.demo.person.dto.request.CreatePersonDto;
import com.example.demo.person.dto.request.UpdatePersonDto;
import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.person.service.PersonService;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.ApiResponse;
import com.example.demo.shared.response.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<ApiResponse<List<PersonDto>>> getPersons(){
        List<PersonDto> countries = personService.getAllPersons();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PersonDto>>builder()
                        .success(true)
                        .message("Lấy persons thành công")
                        .data(countries)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<PersonDto>>> getPersons(
            PaginationRequest paginationRequest
    ){
        Pageable pageable = paginationRequest.toPageable();
        PagedResponse<PersonDto> pagedPerson = personService.getAllPersons(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PagedResponse<PersonDto>>builder()
                        .success(true)
                        .message("Lấy persons thành công")
                        .data(pagedPerson)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<PersonDto>> createPerson(@Valid @RequestBody CreatePersonDto person){
        PersonDto created = personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Tạo person thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<PersonDto>> updatePerson(@Valid @RequestBody UpdatePersonDto personDto){
        PersonDto updated = personService.updatePerson(personDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Cập nhật person thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonDto>> deletePersonById(@PathVariable Long id) {
        PersonDto deleted = personService.deletePersonById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Xóa person thành công")
                        .data(deleted)
                        .build());
    }

    @PutMapping("/assign-company")
    public ResponseEntity<ApiResponse<PersonDto>> assignCompany(@Valid @RequestBody AssignCompanyDto assignCompanyDto){
        PersonDto assigned = personService.assignCompany(assignCompanyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Gán công ty thành công")
                        .data(assigned)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonDto>> getPersonById(@PathVariable Long id){
        PersonDto person = personService.getPersonById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Lấy person thành công")
                        .data(person)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<PersonDto>>> getPersonsByName(@PathVariable String name){
        List<PersonDto> persons = personService.getPersonsByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PersonDto>>builder()
                        .success(true)
                        .message("Lấy persons thành công")
                        .data(persons)
                        .build());
    }

    @GetMapping("/{id}/by-company")
    public ResponseEntity<ApiResponse<List<PersonDto>>> getPersonsByCompanyId(@PathVariable Long id){
        List<PersonDto> persons = personService.getPersonsByCompanyId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PersonDto>>builder()
                        .success(true)
                        .message("Lấy persons thành công")
                        .data(persons)
                        .build());
    }

    @GetMapping("/{phoneNumber}/by-phone")
    public ResponseEntity<ApiResponse<PersonDto>> getPersonByPhoneNumber(@PathVariable String phoneNumber){
        PersonDto person = personService.getPersonByPhoneNumber(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Lấy person thành công")
                        .data(person)
                        .build());
    }

    @GetMapping("/{userId}/by-user")
    public ResponseEntity<ApiResponse<PersonDto>> getPersonByUserId(@PathVariable Long userId){
        PersonDto person = personService.getPersonByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PersonDto>builder()
                        .success(true)
                        .message("Lấy person thành công")
                        .data(person)
                        .build());
    }

    @GetMapping("/{projectId}/by-project")
    public ResponseEntity<ApiResponse<List<PersonDto>>> getPersonsByProjectId(@PathVariable Long projectId){
        List<PersonDto> persons = personService.getPersonsByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PersonDto>>builder()
                        .success(true)
                        .message("Lấy persons theo dự án thành công")
                        .data(persons)
                        .build());
    }
}
