package com.softserve.edu.service.utils;

import com.softserve.edu.entity.user.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.sort.SortCriteria} interface instead<br/>
 * as it used in {@link com.softserve.edu.specification.sort.AgreementSortCriteria}
 */
public enum SortCriteriaUser {
    USERNAME() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("username"));
            } else {
                return cb.desc(root.get("username"));
            }
        }
    },
    LASTNAME() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("lastName"));
            } else {
                return cb.desc(root.get("lastName"));
            }
        }
    },
    FIRSTNAME() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("firstName"));
            } else {
                return cb.desc(root.get("firstName"));
            }
        }
    },
    PHONE() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("phone"));
            } else {
                return cb.desc(root.get("phone"));
            }
        }
    },
    EMAIL() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("email"));
            } else {
                return cb.desc(root.get("email"));
            }
        }
    },
    STREET() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("street"));
            } else {
                return cb.desc(root.get("address").get("street"));
            }
        }
    },
    DISTRICT() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("district"));
            } else {
                return cb.desc(root.get("address").get("district"));
            }
        }
    },

    REGION() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("region"));
            } else {
                return cb.desc(root.get("address").get("region"));
            }
        }
    },
    LOCALITY() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("locality"));
            } else {
                return cb.desc(root.get("address").get("locality"));
            }
        }
    },
    ORGANIZATION() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder){
            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("organization").get("name"));
            } else {
                return cb.desc(root.get("organization").get("name"));
            }
        }
    };

    public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}

