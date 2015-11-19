package com.softserve.edu.service.utils;

import com.softserve.edu.entity.organization.Organization;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.sort.SortCriteria} interface instead<br/>
 * as it used in {@link com.softserve.edu.specification.sort.AgreementSortCriteria}
 */
public enum SortCriteriaOrganization {
    ID() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    UNDEFINED() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    NAME() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("name"));
            } else {
                return cb.desc(root.get("name"));
            }
        }
    },
    EMAIL() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("email"));
            } else {
                return cb.desc(root.get("email"));
            }
        }
    },
    PHONE_NUMBER() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("phone"));
            } else {
                return cb.desc(root.get("phone"));
            }
        }
    },
    STREET() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("street"));
            } else {
                return cb.desc(root.get("address").get("street"));
            }
        }
    },
    DISTRICT() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("district"));
            } else {
                return cb.desc(root.get("address").get("district"));
            }
        }
    },

    REGION() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("region"));
            } else {
                return cb.desc(root.get("address").get("region"));
            }
        }
    },

    LOCALITY() {
        public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("address").get("locality"));
            } else {
                return cb.desc(root.get("address").get("locality"));
            }
        }
    };

    public Order getSortOrder(Root<Organization> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
