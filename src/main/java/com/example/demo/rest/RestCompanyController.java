package com.example.demo.rest;

import com.example.demo.dto.CompanyDto;
import com.example.demo.service.CompanyService;
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
@RequestMapping("/api/companies")
public class RestCompanyController {

    private final CompanyService companyService;

    public RestCompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CompanyDto>>> getCompanies(){
        List<CompanyDto> countries = companyService.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CompanyDto>>builder()
                        .success(true)
                        .message("Lấy companies thành công")
                        .data(countries)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<CompanyDto>>> getCompaniesPaged(
            PaginationRequest paginationRequest
    ){
        Pageable pageable = paginationRequest.toPageable();
        PagedResponse<CompanyDto> pagedCompanies = companyService.getAllCompanies(pageable);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PagedResponse<CompanyDto>>builder()
                        .success(true)
                        .message("Lây công ty thành công")
                        .data(pagedCompanies)
                        .build());
    }


    @PostMapping()
    public ResponseEntity<ApiResponse<CompanyDto>> createCompany(@Valid @RequestBody CompanyDto company){
        CompanyDto created = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CompanyDto>builder()
                        .success(true)
                        .message("Tạo công ty thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<CompanyDto>> updateCompany(@Valid @RequestBody CompanyDto companyDto){
        CompanyDto updated = companyService.updateCompany(companyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CompanyDto>builder()
                        .success(true)
                        .message("Cập nhật công ty thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> deleteCompanyById(@PathVariable Long id) {
        CompanyDto deleted = companyService.deleteCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CompanyDto>builder()
                        .success(true)
                        .message("Xóa công ty thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> getCompanyById(@PathVariable Long id){
        CompanyDto company = companyService.getCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CompanyDto>builder()
                        .success(true)
                        .message("Lấy company thành công")
                        .data(company)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<CompanyDto>>> getCompaniesByName(@PathVariable String name){
        List<CompanyDto> companies = companyService.getCompaniesByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CompanyDto>>builder()
                        .success(true)
                        .message("Lấy companies thành công")
                        .data(companies)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<CompanyDto>> getCompanyByCode(@PathVariable String code){
        CompanyDto company = companyService.getCompanyByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CompanyDto>builder()
                        .success(true)
                        .message("Lấy company thành công")
                        .data(company)
                        .build());
    }
}
