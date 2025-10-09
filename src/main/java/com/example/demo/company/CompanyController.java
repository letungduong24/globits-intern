package com.example.demo.company;

import com.example.demo.company.dto.CreateCompanyDto;
import com.example.demo.company.dto.ResponseCompanyDto;
import com.example.demo.company.dto.UpdateCompanyDto;
import com.example.demo.company.service.CompanyService;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.ApiResponse;
import com.example.demo.shared.response.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ResponseCompanyDto>>> getCompanies(){
        List<ResponseCompanyDto> countries = companyService.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseCompanyDto>>builder()
                        .success(true)
                        .message("Lấy companies thành công")
                        .data(countries)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<ResponseCompanyDto>>> getCompaniesPaged(
            PaginationRequest paginationRequest
    ){
        Pageable pageable = paginationRequest.toPageable();
        PagedResponse<ResponseCompanyDto> pagedCompanies = companyService.getAllCompanies(pageable);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PagedResponse<ResponseCompanyDto>>builder()
                        .success(true)
                        .message("Lây công ty thành công")
                        .data(pagedCompanies)
                        .build());
    }


    @PostMapping()
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> createCompany(@Valid @RequestBody CreateCompanyDto company){
        ResponseCompanyDto created = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Tạo công ty thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> updateCompany(@Valid @RequestBody UpdateCompanyDto companyDto){
        ResponseCompanyDto updated = companyService.updateCompany(companyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Cập nhật công ty thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> deleteCompanyById(@PathVariable Long id) {
        ResponseCompanyDto deleted = companyService.deleteCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Xóa công ty thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> getCompanyById(@PathVariable Long id){
        ResponseCompanyDto company = companyService.getCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Lấy company thành công")
                        .data(company)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<ResponseCompanyDto>>> getCompaniesByName(@PathVariable String name){
        List<ResponseCompanyDto> companies = companyService.getCompaniesByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseCompanyDto>>builder()
                        .success(true)
                        .message("Lấy companies thành công")
                        .data(companies)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> getCompanyByCode(@PathVariable String code){
        ResponseCompanyDto company = companyService.getCompanyByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Lấy company thành công")
                        .data(company)
                        .build());
    }
}
