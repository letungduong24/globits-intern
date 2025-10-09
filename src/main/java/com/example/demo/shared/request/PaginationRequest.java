package com.example.demo.shared.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationRequest {
    
    @Builder.Default
    Integer page = 0;
    
    @Builder.Default
    Integer size = 10;
    
    @Builder.Default
    String sortBy = "id";
    
    @Builder.Default
    String sortDirection = "ASC";
    
    public void validate() {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size <= 0 || size > 100) {
            size = 10;
        }
        if (sortDirection == null || 
            (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC"))) {
            sortDirection = "ASC";
        }
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
        }
    }
    
    public Pageable toPageable() {
        validate();
        
        Sort.Direction direction =
            sortDirection.equalsIgnoreCase("DESC") 
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        
        return PageRequest.of(
            page, 
            size, 
            Sort.by(direction, sortBy)
        );
    }
}

