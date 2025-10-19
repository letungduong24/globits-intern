package com.example.demo.specification;

import com.example.demo.domain.Task;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TaskSpecification {

    public static Specification<Task> filter(
            Long companyId,
            Long projectId,
            Long personId,
            Integer status,
            Integer priority,
            String name
    ) {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (companyId != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("project").get("company").get("id"), companyId)
                    );
                }
                if (projectId != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("project").get("id"), projectId)
                    );
                }
                if (personId != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("person").get("id"), personId)
                    );
                }
                if (status != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("status"), status)
                    );
                }
                if (priority != null) {
                    predicate = cb.and(predicate,
                            cb.equal(root.get("priority"), priority)
                    );
                }
                if (name != null && !name.isBlank()) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")
                    );
                }

                return predicate;
            }
        };
    }
}
