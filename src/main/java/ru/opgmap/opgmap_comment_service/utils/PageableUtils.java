package ru.opgmap.opgmap_comment_service.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableUtils {

    public static Sort.Direction getSortDirection(int asc) {
        return (asc == 0) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public static Pageable getPageable(int cursor,
                                       int size,
                                       String sortBy,
                                       int asc) {
        Sort.Direction sortDirection = getSortDirection(asc);
        return PageRequest.of(cursor, size, Sort.by(sortDirection, sortBy));
    }

}
