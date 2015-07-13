package com.softserve.edu.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.softserve.edu.entity.Organization;
import com.softserve.edu.entity.Verification;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.entity.util.Status;


public class QueryConstructor {

	/**
	 * Method dynamically builds query to database depending on input parameters specified. 
	 * 
	 * @param providerId
	 * 		search by organization ID
	 * @param dateToSearch
	 * 		search by initial date of verification (optional)
	 * @param idToSearch
	 * 		search by verification ID
	 * @param lastNameToSearch
	 * 		search by client's last name 
	 * @param streetToSearch
	 * 		search by client's street
	 * @param providerEmployee
	 * 		used to additional query restriction if logged user is simple employee (not admin)
	 * @param em
	 * 		EntityManager needed to have a possibility to create query
 	 * @return CriteriaQuery<Verification>
	 */
	public static CriteriaQuery<Verification> buildSearchQuery (Long providerId, String dateToSearch,
									String idToSearch, String lastNameToSearch, String streetToSearch,
									User providerEmployee, EntityManager em) {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Verification> criteriaQuery = cb.createQuery(Verification.class);
			Root<Verification> root = criteriaQuery.from(Verification.class);
			Join<Verification, Organization> joinSearch = root.join("provider");
			Predicate predicate = QueryConstructor.buildPredicate(root, cb, joinSearch, providerId, dateToSearch, idToSearch,
																		lastNameToSearch, streetToSearch, providerEmployee);
			criteriaQuery.orderBy(cb.desc(root.get("initialDate")));
			criteriaQuery.select(root);
			criteriaQuery.where(predicate);
			return criteriaQuery;
	}
	
	/**
	 * Method dynamically builds query to database depending on input parameters specified. 
	 * Needed to get max count of rows with current predicates for pagination 
	 * 
	 * @param providerId
	 * 		search by organization ID
	 * @param dateToSearch
	 * 		search by initial date of verification (optional)
	 * @param idToSearch
	 * 		search by verification ID
	 * @param lastNameToSearch
	 * 		search by client's last name 
	 * @param streetToSearch
	 * 		search by client's street
	 * @param providerEmployee
	 * 		used to additional query restriction if logged user is simple employee (not admin)
	 * @param em
	 * 		EntityManager needed to have a possibility to create query
 	 * @return CriteriaQuery<Long>
	 */
	public static CriteriaQuery<Long> buildCountQuery (Long providerId, String dateToSearch,
							String idToSearch, String lastNameToSearch, String streetToSearch,
							User providerEmployee, EntityManager em) {
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
			Root<Verification> root = countQuery.from(Verification.class);
			Join<Verification, Organization> joinSearch = root.join("provider");
			Predicate predicate = QueryConstructor.buildPredicate(root, cb, joinSearch, providerId, dateToSearch, idToSearch,
																		lastNameToSearch, streetToSearch, providerEmployee);
			countQuery.select(cb.count(root));
			countQuery.where(predicate);
			return countQuery;
			}
	/**
	 * Method builds list of predicates depending on parameters passed
	 * Rule for predicates compounding - conjunction (AND)
	 * 
	 * @param root
	 * @param cb
	 * @param joinSearch
	 * @param providerId
	 * @param dateToSearch
	 * @param idToSearch
	 * @param lastNameToSearch
	 * @param streetToSearch
	 * @param providerEmployee
	 * @return Predicate 
	 */
	private static Predicate buildPredicate (Root<Verification> root, CriteriaBuilder cb, Join<Verification, Organization> joinSearch,
													Long providerId, String dateToSearch,String idToSearch, String lastNameToSearch,
																			String streetToSearch, User providerEmployee) {
			Predicate queryPredicate = cb.conjunction();
//			String role= providerEmployee.getRole();
			String userName = providerEmployee.getUsername();
			
//				if(role.equalsIgnoreCase("PROVIDER_EMPLOYEE")) {
//					Join<Verification, Employee> joinSearchProviderEmployee = root.join("providerEmployee", JoinType.LEFT);
//					Predicate searchPredicateByUsername =cb.equal(joinSearchProviderEmployee.get("username"), userName);
//					Predicate searchPredicateByEmptyField = cb.isNull(joinSearchProviderEmployee.get("username"));
//					Predicate searchPredicateByProviderEmployee=cb.or(searchPredicateByUsername,searchPredicateByEmptyField);
//					queryPredicate=cb.and(searchPredicateByProviderEmployee);
//				}
			
			Predicate sentStatus = cb.equal(root.get("status"), Status.SENT);
			Predicate acceptedStatus = cb.equal(root.get("status"), Status.ACCEPTED);
			
			queryPredicate = cb.and(cb.or(sentStatus, acceptedStatus), queryPredicate);
			//queryPredicate = cb.and(cb.equal(root.get("status"), Status.SENT),queryPredicate);
			
			queryPredicate = cb.and(cb.equal(joinSearch.get("id"), providerId), queryPredicate);
				
				 if (!(dateToSearch.equalsIgnoreCase("null"))) {
					 SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
					 Date date = null;
					 try {
					    date = form.parse(dateToSearch);
					 } catch (ParseException e) {
					    e.printStackTrace();
					 }
					 queryPredicate = cb.and(cb.equal(root.get("initialDate"), date), queryPredicate);
				}
	
				 if (!(idToSearch.equalsIgnoreCase("null"))) {
					 queryPredicate = cb.and(cb.like(root.get("id"), "%" + idToSearch + "%"), queryPredicate);
				 }
				 if (!(lastNameToSearch.equalsIgnoreCase("null"))) {
				   queryPredicate = cb.and(cb.like(root.get("clientData").get("lastName"), "%" + lastNameToSearch + "%"), queryPredicate);
				 }
				 if (!(streetToSearch.equalsIgnoreCase("null"))) {
				   queryPredicate = cb.and(cb.like(root.get("clientData").get("clientAddress").get("street"), "%" + streetToSearch + "%"), queryPredicate);
				 }
	
			return queryPredicate;
	}
}
