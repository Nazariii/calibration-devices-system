package com.softserve.edu.service.utils;

import com.softserve.edu.entity.device.CounterType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.sort.SortCriteria} interface instead<br/>
 * as it used in {@link com.softserve.edu.specification.sort.AgreementSortCriteria}
 */
public enum SortCriteriaCounterType {
    ID(){
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    UNDEFINED() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    NAME() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("name"));
            } else {
                return cb.desc(root.get("name"));
            }
        }
    },
    SYMBOL() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("symbol"));
            } else {
                return cb.desc(root.get("symbol"));
            }
        }
    },
    STANDARDSIZE() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("standardSize"));
            } else {
                return cb.desc(root.get("standardSize"));
            }
        }
    },
    MANUFACTURER() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("manufacturer"));
            } else {
                return cb.desc(root.get("manufacturer"));
            }
        }
    },
    CALIBRATIONINTERVAL() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("calibrationInterval"));
            } else {
                return cb.desc(root.get("calibrationInterval"));
            }
        }
    },
    YEARINTRODUCTION() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("yearIntroduction"));
            } else {
                return cb.desc(root.get("yearIntroduction"));
            }
        }
    },
    GOST() {
        public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("gost"));
            } else {
                return cb.desc(root.get("gost"));
            }
        }
    };

    public Order getSortOrder(Root<CounterType> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
