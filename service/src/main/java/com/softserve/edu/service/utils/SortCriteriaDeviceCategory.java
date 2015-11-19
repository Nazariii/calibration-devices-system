package com.softserve.edu.service.utils;

import com.softserve.edu.entity.device.Device;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.sort.SortCriteria} interface instead<br/>
 * as it used in {@link com.softserve.edu.specification.sort.AgreementSortCriteria}
 */
public enum SortCriteriaDeviceCategory {
    ID() {
        public Order getSortOrder(Root<Device> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    UNDEFINED() {
        public Order getSortOrder(Root<Device> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("number"));
            } else {
                return cb.desc(root.get("number"));
            }
        }
    },
    NUMBER() {
        public Order getSortOrder(Root<Device> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("number"));
            } else {
                return cb.desc(root.get("number"));
            }
        }
    },
    DEVICETYPE() {
        public Order getSortOrder(Root<Device> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("deviceType"));
            } else {
                return cb.desc(root.get("deviceType"));
            }
        }
    },
    DEVICENAME() {
        public Order getSortOrder(Root<Device> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("deviceName"));
            } else {
                return cb.desc(root.get("deviceName"));
            }
        }
    };

    public Order getSortOrder(Root<Device> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
