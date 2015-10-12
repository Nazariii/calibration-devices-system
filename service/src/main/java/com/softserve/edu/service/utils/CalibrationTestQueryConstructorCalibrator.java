package com.softserve.edu.service.utils;

import com.softserve.edu.entity.enumeration.device.DeviceType;
import com.softserve.edu.entity.enumeration.verification.CalibrationTestResult;
import com.softserve.edu.entity.enumeration.verification.Status;
import com.softserve.edu.entity.organization.Organization;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.entity.verification.Verification;
import com.softserve.edu.entity.verification.calibration.CalibrationTest;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CalibrationTestQueryConstructorCalibrator {
    static Logger logger = Logger.getLogger(CalibrationTestQueryConstructorCalibrator.class);


    public static CriteriaQuery<Verification> buildSearchQuery(Long employeeId, String startDateToSearch,
                                                               String endDateToSearch,String region, String district, String locality,
                                                               String idToSearch, String fullNameToSearch, String streetToSearch, String status, String employeeName,
                                                               Long protocolId, String protocolStatus,
                                                               Long measurementDeviceId,
                                                               String measurementDeviceType,
                                                               String sortCriteria, String sortOrder, User providerEmployee, EntityManager em) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Verification> criteriaQuery = cb.createQuery(Verification.class);
        Root<Verification> root = criteriaQuery.from(Verification.class);

        Join<Verification, Organization> calibratorJoin = root.join("calibrator");

        Predicate predicate = CalibrationTestQueryConstructorCalibrator.buildPredicate(root, cb, employeeId, startDateToSearch, endDateToSearch, idToSearch, fullNameToSearch, region, district, locality, streetToSearch,
                status, employeeName, protocolId, protocolStatus, measurementDeviceId, measurementDeviceType, providerEmployee, calibratorJoin);
        if ((sortCriteria != null) && (sortOrder != null)) {
            criteriaQuery.orderBy(SortCriteriaVerification.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get("initialDate")));
        }
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }


    public static CriteriaQuery<Long> buildCountQuery(Long employeeId, String startDateToSearch, String endDateToSearch,
                                                      String idToSearch, String fullNameToSearch, String region, String district, String locality,
                                                      String streetToSearch, String status, String employeeName,
                                                      Long protocolId, String protocolStatus, Long measurementDeviceId, String measurementDeviceType,
                                                      User providerEmployee, EntityManager em) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Verification> root = countQuery.from(Verification.class);
        Join<Verification, Organization> calibratorJoin = root.join("calibrator");
        Predicate predicate = CalibrationTestQueryConstructorCalibrator.buildPredicate(root, cb, employeeId, startDateToSearch, endDateToSearch,
                idToSearch, fullNameToSearch, region, district, locality, streetToSearch, status, employeeName, protocolId, protocolStatus, measurementDeviceId, measurementDeviceType, providerEmployee, calibratorJoin);
        countQuery.select(cb.count(root));
        countQuery.where(predicate);
        return countQuery;
    }

    private static Predicate buildPredicate(Root<Verification> root, CriteriaBuilder cb, Long employeeId,
                                            String startDateToSearch, String endDateToSearch, String idToSearch,
                                            String fullNameToSearch,String region, String district, String locality, String streetToSearch, String searchStatus,
                                            String employeeName, Long protocolId, String protocolStatus,
                                            Long measurementDeviceId, String measurementDeviceType,
                                            User employee, Join<Verification, Organization> calibratorJoin) {

        Predicate queryPredicate = cb.conjunction();
        queryPredicate = cb.and(cb.equal(calibratorJoin.get("id"), employeeId), queryPredicate);

        if (searchStatus != null) {
            queryPredicate = cb.and(cb.equal(root.get("status"), Status.valueOf(searchStatus.trim())), queryPredicate);
        } else {
            queryPredicate = cb.and(cb.not(cb.or(
                    Status.SENT.getQueryPredicate(root, cb),
                    Status.ACCEPTED.getQueryPredicate(root, cb),
                    Status.IN_PROGRESS.getQueryPredicate(root, cb),
                    Status.TEST_PLACE_DETERMINED.getQueryPredicate(root, cb),
                    Status.SENT_TO_TEST_DEVICE.getQueryPredicate(root, cb),
                    Status.TEST_COMPLETED.getQueryPredicate(root, cb)
            )), queryPredicate);
        }

        if (startDateToSearch != null && endDateToSearch != null) {
            DateTimeFormatter dbDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

            LocalDate startDate = null;
            LocalDate endDate = null;
            try {
                startDate = LocalDate.parse(startDateToSearch, dbDateTimeFormatter);
                endDate = LocalDate.parse(endDateToSearch, dbDateTimeFormatter);
            } catch (Exception pe) {
                logger.error("Cannot parse date", pe); //TODO: add exception catching
            }
            //verifications with date between these two dates
            queryPredicate = cb.and(cb.between(root.get("initialDate"), java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate)), queryPredicate);

        }

        if ((idToSearch != null) && (idToSearch.length() > 0)) {
            queryPredicate = cb.and(cb.like(root.get("id"), "%" + idToSearch + "%"), queryPredicate);
        }

        if ((fullNameToSearch != null) && (fullNameToSearch.length() > 0)) {
            Predicate searchByClientFirstName = cb.like(root.get("clientData").get("firstName"), "%" + fullNameToSearch + "%");
            Predicate searchByClientLastName = cb.like(root.get("clientData").get("lastName"), "%" + fullNameToSearch + "%");
            Predicate searchByClientMiddleName = cb.like(root.get("clientData").get("middleName"), "%" + fullNameToSearch + "%");
            Predicate searchPredicateByClientFullName = cb.or(searchByClientFirstName, searchByClientLastName, searchByClientMiddleName);
            queryPredicate = cb.and(searchPredicateByClientFullName, queryPredicate);
        }

        if ((streetToSearch != null) && (streetToSearch.length() > 0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("clientData").get("clientAddress").get("street"), "%" + streetToSearch + "%"),
                    queryPredicate);
        }

        if ((region != null) && (region.length() > 0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("clientData").get("clientAddress").get("region"), "%" + region + "%"),
                    queryPredicate);
        }

        if ((district != null) && (district.length() > 0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("clientData").get("clientAddress").get("district"), "%" + district + "%"),
                    queryPredicate);
        }

        if ((locality != null) && (locality.length() > 0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("clientData").get("clientAddress").get("locality"), "%" + locality + "%"),
                    queryPredicate);
        }

        if ((employeeName != null) && (employeeName.length() > 0)) {
            Join<Verification, User> joinCalibratorEmployee = root.join("calibratorEmployee");
            Predicate searchByCalibratorName = cb.like(joinCalibratorEmployee.get("firstName"),
                    "%" + employeeName + "%");
            Predicate searchByCalibratorSurname = cb.like(joinCalibratorEmployee.get("lastName"),
                    "%" + employeeName + "%");
            Predicate searchByCalibratorLastName = cb.like(joinCalibratorEmployee.get("middleName"),
                    "%" + employeeName + "%");
            Predicate searchPredicateByCalibratorEmployeeName = cb.or(searchByCalibratorName, searchByCalibratorSurname,
                    searchByCalibratorLastName);
            queryPredicate = cb.and(searchPredicateByCalibratorEmployeeName, queryPredicate);
        }
        if (measurementDeviceId != null) {
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Long>(cb, root.get("device").get("id")),
                    "%" + measurementDeviceId.toString() + "%"), queryPredicate);
        }
        if (measurementDeviceType != null) {
            queryPredicate = cb.and(cb.equal(root.get("device").get("deviceType"),
                    DeviceType.valueOf(measurementDeviceType.trim())), queryPredicate);
        }
        if (protocolId != null) {
            Join<Verification, CalibrationTest> joinCalibratorTest = root.join("calibrationTests");
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Long>(cb, joinCalibratorTest.get("id")),
                    "%" + protocolId.toString() + "%"), queryPredicate);

        }
        if (protocolStatus != null) {
            logger.debug("ArchiveVerificationQueryConstructorCalibrator : protocolStatus = " + protocolStatus);
            Join<Verification, CalibrationTest> joinCalibratorTest = root.join("calibrationTests");
            queryPredicate = cb.and(cb.equal(joinCalibratorTest.get("testResult"),
                    CalibrationTestResult.valueOf(protocolStatus.trim())), queryPredicate);
        }

        return queryPredicate;
    }
}