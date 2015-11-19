package com.softserve.edu.specification.sort;

import com.softserve.edu.specification.AgreementSpecificationBuilder;
import org.springframework.data.domain.Sort;

public enum AgreementSortCriteria implements SortCriteria{
    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.NUMBER);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.NUMBER);
            }
        }
    },
    NUMBER {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.NUMBER);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.NUMBER);
            }
        }
    },
    DEVICE_TYPE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.DEVICE_TYPE);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.DEVICE_TYPE);
            }
        }
    },
    DEVICE_COUNT {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.DEVICE_COUNT);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.DEVICE_COUNT);
            }
        }
    },
    CUSTOMER_NAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.CUSTOMER_JOIN_NAME);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.CUSTOMER_JOIN_NAME);
            }
        }
    },
    EXECUTOR_NAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.EXECUTOR_JOIN_NAME);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.EXECUTOR_JOIN_NAME);
            }
        }
    },
    DATE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, AgreementSpecificationBuilder.DATE);
            } else {
                return new Sort(Sort.Direction.DESC, AgreementSpecificationBuilder.DATE);
            }
        }
    }
}
