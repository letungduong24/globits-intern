package com.example.demo.specification.request;

import com.example.demo.shared.request.PaginationRequest;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskPagedRequest {
    
    @Valid
    private TaskSpecificationRequest taskSpecification;
    
    @Valid
    private PaginationRequest pagination;
}
