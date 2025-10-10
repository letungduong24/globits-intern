package com.example.demo.task.specification;

import com.example.demo.shared.request.PaginationRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskSpecificationRequest {

    Long companyId;
    Long projectId;
    Long personId;
    Integer status;
    Integer priority;
    String name;

    @Builder.Default
    PaginationRequest pageRequest = new PaginationRequest();
}
