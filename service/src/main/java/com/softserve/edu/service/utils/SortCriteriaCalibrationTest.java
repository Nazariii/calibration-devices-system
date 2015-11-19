package com.softserve.edu.service.utils;

import com.softserve.edu.entity.verification.calibration.CalibrationTest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.sort.SortCriteria} interface instead<br/>
 * as it used in {@link com.softserve.edu.specification.sort.AgreementSortCriteria}
 */
@Deprecated
public enum SortCriteriaCalibrationTest {
    DATE() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("dateTest"));
            } else {
                return cb.desc(root.get("dateTest"));
            }
        }
    },
    NAME() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("name"));
            } else {
                return cb.desc(root.get("name"));
            }
        }
    },
    CLIENT_FULL_NAME() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.join("verification").get("clientData").get("lastName"));
            } else {
                return cb.desc(root.join("verification").get("clientData").get("lastName"));
            }
        }
    },
    STREET() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.join("verification").get("clientData").get("clientAddress").get("street"));
            } else {
                return cb.desc(root.join("verification").get("clientData").get("clientAddress").get("street"));
            }
        }
    },
    DISTRICT() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.join("verification").get("clientData").get("clientAddress").get("district"));
            } else {
                return cb.desc(root.join("verification").get("clientData").get("clientAddress").get("district"));
            }
        }
    },

    REGION() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.join("verification").get("clientData").get("clientAddress").get("region"));
            } else {
                return cb.desc(root.join("verification").get("clientData").get("clientAddress").get("region"));
            }
        }
    },

    LOCALITY() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if (sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.join("verification").get("clientData").get("clientAddress").get("locality"));
            } else {
                return cb.desc(root.join("verification").get("clientData").get("clientAddress").get("locality"));
            }
        }
    },
    MEASUREMENT_DEVICE_ID() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return (cb.asc(root.join("verification").join("device").get("id")));
            } else {
                return (cb.desc(root.join("verification").join("device").get("id")));
            }
        }
    },
    MEASUREMENT_DEVICE_TYPE() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return (cb.asc(root.join("verification").join("device").get("deviceType")));
            } else {
                return (cb.desc(root.join("verification").join("device").get("deviceType")));
            }
        }
    },
    PROTOCOL_ID(){
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return (cb.asc(root.get("id")));
            } else {
                return (cb.desc(root.get("id")));
            }
        }
    },
    TEST_RESULT() {
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return (cb.asc(root.get("testResult")));
            } else {
                return (cb.desc(root.get("testResult")));
            }
        }
    },
    SETTING_NUMBER(){
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder){
            if(sortOrder.equalsIgnoreCase("asc")) {
                return (cb.asc(root.get("settingNumber")));
            }else {
                return (cb.desc(root.get("settingNumber")));
            }
        }
    },
    CONSUMPTION_STATUS(){
        public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder){
            if(sortOrder.equalsIgnoreCase("asc")) {
                return (cb.asc(root.get("consumptionStatus")));
            }else {
                return (cb.desc(root.get("consumptionStatus")));
            }
        }
    };

    public Order getSortOrder(Root<CalibrationTest> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }

}
