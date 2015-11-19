	package com.softserve.edu.service.utils;

	import com.softserve.edu.entity.organization.Organization;
	import com.softserve.edu.entity.verification.Verification;
	import com.softserve.edu.entity.user.User;
	import com.softserve.edu.entity.enumeration.user.UserRole;
	import com.softserve.edu.entity.enumeration.verification.Status;
	import org.apache.log4j.Logger;

	import javax.persistence.EntityManager;
	import javax.persistence.criteria.*;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.Set;

	/**
	 * @deprecated this class have a lot of repeated code <br/>
	 * {need to be replaced and removed}<br/>
	 * use {@link com.softserve.edu.specification.SpecificationBuilder} instead
	 */
	@Deprecated
	public class NewVerificationsQueryConstructorCalibrator {

		static Logger logger = Logger.getLogger(NewVerificationsQueryConstructorProvider.class);
		
		/**
		 * Method dynamically builds query to database depending on input parameters specified. 
		 *  @param lastNameToSearch
		 * 		search by client's last name
		 * @param providerEmployee
		 * 		used to additional query restriction if logged user is simple employee (not admin)
		 * @param dateToSearch
 * 		search by initial date of verification (optional)
		 * @param providerId
* 		search by organization ID
		 * @param startDateToSearch
		 * @param endDateToSearch
		 * @param idToSearch
		 * 		search by verification ID
		 * @param fullNameToSearch
		 * @param streetToSearch
* 		search by client's street
		 * @param em
		 * */
		public static CriteriaQuery<Verification> buildSearchQuery(Long providerId, String startDateToSearch,
																   String endDateToSearch, String idToSearch, String fullNameToSearch, String streetToSearch, String region,
																   String district, String locality, String status,
																   User calibratorEmployee, String sortCriteria, String sortOrder, String employeeSearchName, EntityManager em) {

				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Verification> criteriaQuery = cb.createQuery(Verification.class);
				Root<Verification> root = criteriaQuery.from(Verification.class);
				Join<Verification, Organization> calibratorJoin = root.join("calibrator");

				Predicate predicate = NewVerificationsQueryConstructorCalibrator.buildPredicate(root, cb, calibratorJoin, providerId, startDateToSearch, endDateToSearch,
						idToSearch, fullNameToSearch, streetToSearch, region, district, locality, status, calibratorEmployee, employeeSearchName);
				if((sortCriteria != null)&&(sortOrder != null)) {
					criteriaQuery.orderBy(SortCriteriaVerification.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
				} else {
					criteriaQuery.orderBy(cb.desc(root.get("initialDate")));
				}
				criteriaQuery.select(root);
				criteriaQuery.where(predicate);
				return criteriaQuery;
		}
		
		/**
		 * Method dynamically builds query to database depending on input parameters specified. 
		 * Needed to get max count of rows with current predicates for pagination 
		 *  @param providerId
		 * 		search by organization ID
		 * @param lastNameToSearch
		 * 		search by client's last name
		 * @param providerEmployee
 * 		used to additional query restriction if logged user is simple employee (not admin)
		 * @param startDateToSearch
* 		search by initial date of verification (optional)
		 * @param endDateToSearch
		 * @param idToSearch
		 * 		search by verification ID
		 * @param fullNameToSearch
		 * @param streetToSearch
* 		search by client's street
		 * @param em
		 * */
		public static CriteriaQuery<Long> buildCountQuery(Long calibratorId, String startDateToSearch, String endDateToSearch, String idToSearch, String fullNameToSearch, String streetToSearch, String region,
														  String district, String locality, String status,
														  User calibratorEmployee, String employeeSearchName, EntityManager em) {
			
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
				Root<Verification> root = countQuery.from(Verification.class);
				Join<Verification, Organization> calibratorJoin = root.join("calibrator");
				Predicate predicate = NewVerificationsQueryConstructorCalibrator.buildPredicate(root, cb, calibratorJoin, calibratorId, startDateToSearch, endDateToSearch,
						idToSearch, fullNameToSearch, streetToSearch, region, district, locality, status, calibratorEmployee, employeeSearchName);
				countQuery.select(cb.count(root));
				countQuery.where(predicate);
				return countQuery;
				}
		/**
		 * Method builds list of predicates depending on parameters passed
		 * Rule for predicates compounding - conjunction (AND)
		 *  @param providerId
		 * @param lastNameToSearch
		 * @param providerEmployee
		 * @param dateToSearch
		 * @param root
		 * @param cb
		 * @param joinSearch
		 * @param startDateToSearch
		 * @param endDateToSearch
		 * @param idToSearch
		 * @param fullNameToSearch
		 * @param streetToSearch  @return Predicate
		 */
	private static Predicate buildPredicate(Root<Verification> root, CriteriaBuilder cb, Join<Verification, Organization> joinSearch, Long calibratorId, String startDateToSearch, String endDateToSearch, String idToSearch,
											String fullNameToSearch, String streetToSearch, String region, String district, String locality, String status, User calibratorEmployee, String employeeSearchName) {

		String userName = calibratorEmployee.getUsername();
		Predicate queryPredicate = cb.conjunction();
		Set<UserRole> roles = calibratorEmployee.getUserRoles();
		for (UserRole userRole : roles) {
			String role = userRole.name();
			if (role.equalsIgnoreCase("CALIBRATOR_EMPLOYEE")) {
				Join<Verification, User> joinCalibratorEmployee = root.join("calibratorEmployee", JoinType.LEFT);
				Predicate searchPredicateByUsername = cb.equal(joinCalibratorEmployee.get("username"), userName);
				Predicate searchPredicateByEmptyField = cb.isNull(joinCalibratorEmployee.get("username"));
				Predicate searchByCalibratorEmployee = cb.or(searchPredicateByUsername, searchPredicateByEmptyField);
				queryPredicate = cb.and(searchByCalibratorEmployee);
			}
		}

		if (status != null) {
			queryPredicate = cb.and(cb.equal(root.get("status"), Status.valueOf(status.trim())), queryPredicate);
		} else {
			queryPredicate = cb.and(cb.or(Status.IN_PROGRESS.getQueryPredicate(root, cb),
					Status.SENT_TO_TEST_DEVICE.getQueryPredicate(root, cb),
					Status.TEST_PLACE_DETERMINED.getQueryPredicate(root, cb),
					Status.TEST_COMPLETED.getQueryPredicate(root, cb)), queryPredicate);
		}

		queryPredicate = cb.and(cb.equal(joinSearch.get("id"), calibratorId), queryPredicate);

		if (startDateToSearch != null && endDateToSearch != null) {
			DateTimeFormatter dbDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

			LocalDate startDate = null;
			LocalDate endDate = null;
			try {
				startDate = LocalDate.parse(startDateToSearch, dbDateTimeFormatter);
				endDate = LocalDate.parse(endDateToSearch, dbDateTimeFormatter);
			}
			catch (Exception pe) {
				logger.error("Cannot parse date", pe); //TODO: add exception catching
			}
			//verifications with date between these two dates
			queryPredicate = cb.and(cb.between(root.get("initialDate"), java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate)), queryPredicate);

		}

		if ((idToSearch != null)&&(idToSearch.length()>0)) {
			queryPredicate = cb.and(cb.like(root.get("id"), "%" + idToSearch + "%"), queryPredicate);
		}

		if ((fullNameToSearch != null)&&(fullNameToSearch.length()>0)) {
			Predicate searchByClientFirstName = cb.like(root.get("clientData").get("firstName"), "%" + fullNameToSearch + "%");
			Predicate searchByClientLastName = cb.like(root.get("clientData").get("lastName"), "%" + fullNameToSearch + "%");
			Predicate searchByClientMiddleName = cb.like(root.get("clientData").get("middleName"), "%" + fullNameToSearch + "%");
			Predicate searchPredicateByClientFullName = cb.or(searchByClientFirstName, searchByClientLastName, searchByClientMiddleName);
			queryPredicate = cb.and(searchPredicateByClientFullName, queryPredicate);
		}
		if ((streetToSearch != null)&&(streetToSearch.length()>0)) {
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

		if ((employeeSearchName != null)&&(employeeSearchName.length()>0)) {
			Join<Verification, User> joinCalibratorEmployee = root.join("calibratorEmployee");
			Predicate searchByCalibratorName = cb.like(joinCalibratorEmployee.get("firstName"),
					"%" + employeeSearchName + "%");
			Predicate searchByCalibratorSurname = cb.like(joinCalibratorEmployee.get("lastName"),
					"%" + employeeSearchName + "%");
			Predicate searchByCalibratorLastName = cb.like(joinCalibratorEmployee.get("middleName"),
					"%" + employeeSearchName + "%");
			Predicate searchPredicateByCalibratorEmployeeName = cb.or(searchByCalibratorName, searchByCalibratorSurname,
					searchByCalibratorLastName);
			queryPredicate = cb.and(searchPredicateByCalibratorEmployeeName, queryPredicate);
		}

		return queryPredicate;
	}
}


