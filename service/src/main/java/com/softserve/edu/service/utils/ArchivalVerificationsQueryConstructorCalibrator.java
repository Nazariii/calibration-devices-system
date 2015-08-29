package com.softserve.edu.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.softserve.edu.entity.Organization;
import com.softserve.edu.entity.Verification;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.entity.util.Status;

public class ArchivalVerificationsQueryConstructorCalibrator {
static Logger logger = Logger.getLogger(ArchivalVerificationsQueryConstructorProvider.class);
	
	
	public static CriteriaQuery<Verification> buildSearchQuery (Long employeeId, String dateToSearch,
									String idToSearch, String lastNameToSearch, String streetToSearch, String status, String employeeName, 
									 String sortCriteria, String sortOrder, User providerEmployee, EntityManager em) {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Verification> criteriaQuery = cb.createQuery(Verification.class);
			Root<Verification> root = criteriaQuery.from(Verification.class);

			Join<Verification, Organization> calibratorJoin = root.join("calibrator");

			Predicate predicate = ArchivalVerificationsQueryConstructorCalibrator.buildPredicate(root, cb, employeeId, dateToSearch, idToSearch, lastNameToSearch, streetToSearch, status,
																															employeeName, providerEmployee, calibratorJoin);
			if((sortCriteria != null)&&(sortOrder != null)) {
				criteriaQuery.orderBy(SortCriteria.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
			} else {
				criteriaQuery.orderBy(cb.desc(root.get("initialDate")));
			}
			criteriaQuery.select(root);
			criteriaQuery.where(predicate);
			return criteriaQuery;
	}
	
	
	public static CriteriaQuery<Long> buildCountQuery (Long employeeId, String dateToSearch, String idToSearch, String lastNameToSearch, String streetToSearch, String status, String employeeName,
							User providerEmployee, EntityManager em) {
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
			Root<Verification> root = countQuery.from(Verification.class);
			Join<Verification, Organization> calibratorJoin = root.join("calibrator");
			Predicate predicate = ArchivalVerificationsQueryConstructorCalibrator.buildPredicate(root, cb, employeeId, dateToSearch, idToSearch,
																		lastNameToSearch, streetToSearch, status, employeeName, providerEmployee, calibratorJoin);
			countQuery.select(cb.count(root));
			countQuery.where(predicate);
			return countQuery;
			}
	
	private static Predicate buildPredicate (Root<Verification> root, CriteriaBuilder cb, Long employeeId, String dateToSearch,String idToSearch, 
																String lastNameToSearch, String streetToSearch, String searchStatus, 
																String employeeName, User employee, Join<Verification, Organization> calibratorJoin) {

		Predicate queryPredicate = cb.conjunction();
		queryPredicate = cb.and(cb.equal(calibratorJoin.get("id"), employeeId), queryPredicate);
	
		if (searchStatus != null) {
			queryPredicate = cb.and(cb.equal(root.get("status"), Status.valueOf(searchStatus.trim())), queryPredicate);
		} 
		
		if (dateToSearch != null) {
			Date date = null;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateToSearch.substring(0, 10));
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DATE, 1);
				date = c.getTime();
			} catch (ParseException pe) {
				logger.error("Cannot parse date", pe);
			}
			queryPredicate = cb.and(cb.equal(root.get("initialDate"), date), queryPredicate);
		}

		if ((idToSearch != null)&&(idToSearch.length()>0)) {
			queryPredicate = cb.and(cb.like(root.get("id"), "%" + idToSearch + "%"), queryPredicate);
		}
		if ((lastNameToSearch != null)&&(lastNameToSearch.length()>0)) {
			queryPredicate = cb.and(cb.like(root.get("clientData").get("lastName"), "%" + lastNameToSearch + "%"),
					queryPredicate);
		}
		if ((streetToSearch != null)&&(streetToSearch.length()>0)) {
			queryPredicate = cb.and(
					cb.like(root.get("clientData").get("clientAddress").get("street"), "%" + streetToSearch + "%"),
					queryPredicate);
		}
		if ((employeeName != null)&&(employeeName.length()>0)) {
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

		return queryPredicate;
	}
}
