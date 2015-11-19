package com.softserve.edu.specification.sort;


import org.springframework.data.domain.Sort;

public interface SortCriteria {
    Sort getSort(String sortOrder);
}
