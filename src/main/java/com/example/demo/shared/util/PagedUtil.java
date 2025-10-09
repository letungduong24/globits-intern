package com.example.demo.shared.util;

import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagedUtil {
    public static <E, T> PagedResponse<T> ToPagedResponse(Page<E> page, List<T> mappedContent){
        return PagedResponse.<T>builder()
                .content(mappedContent)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
}
