package com.softserve.edu.service.utils;


import com.softserve.edu.entity.enumeration.user.UserRole;
import com.softserve.edu.entity.user.User;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.SpecificationBuilder} instead
 */
@Deprecated
public class ArchivalEmployeeQueryConstructorAdmin {
    static Logger logger = Logger.getLogger(ArchivalEmployeeQueryConstructorAdmin.class);

    public static CriteriaQuery<User> buildSearchQuery(String username, String role, String firstName, String lastName, String organization, String phone,
                                                        String sortCriteria, String sortOrder, EntityManager em) {


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        Predicate predicate = ArchivalEmployeeQueryConstructorAdmin.buildPredicate(root, cb, username,
                role, firstName, lastName, organization, phone);

        if ((sortCriteria != null) && (sortOrder != null)) {
            criteriaQuery.orderBy(SortCriteriaUser.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
        } else {
//            criteriaQuery.orderBy(cb.desc(root.get("username")));
        }
        criteriaQuery.select(root).distinct(true);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }


    private static Predicate buildPredicate(Root<User> root, CriteriaBuilder cb, String username, String role,
                                            String firstName, String lastName, String organization, String phone) {
        Predicate queryPredicate = cb.conjunction();

        if (StringUtils.isNotEmpty(role)) {
            UserRole uRole = UserRole.valueOf(role.trim());
            queryPredicate = cb.and(cb.isMember(uRole, root.get("userRoles")), queryPredicate);
        }else {
            queryPredicate = cb.and(cb.not
                                        (cb.or(
                                                cb.isMember(UserRole.SUPER_ADMIN, root.get("userRoles")),
                                                cb.isMember(UserRole.SYS_ADMIN, root.get("userRoles")))));
        }
        if (StringUtils.isNotEmpty(username)) {
            queryPredicate = cb.and(cb.like(root.get("username"), "%" + username + "%"), queryPredicate);
        }
        if (StringUtils.isNotEmpty(firstName)) {
            queryPredicate = cb
                                .and
                                    (cb.like(root.get("firstName"), "%" + firstName + "%"), queryPredicate);
        }
        if (StringUtils.isNotEmpty(lastName)) {
            queryPredicate = cb.and(cb.like(root.get("lastName"), "%" + lastName + "%"), queryPredicate);
        }
        if (StringUtils.isNotEmpty(organization)) {
            queryPredicate = cb.and(cb.like(root.get("organization").get("name"), "%" + organization + "%"), queryPredicate);
        }
        if (StringUtils.isNotEmpty(phone)) {
            queryPredicate = cb.and(cb.like(root.get("phone"), "%" + phone + "%"), queryPredicate);
        }
        return queryPredicate;
    }

    public static CriteriaQuery<Long> buildCountQuery(String username, String role, String firstName,
                                                      String lastName, String organization, String phone, EntityManager em) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> root = countQuery.from(User.class);
        Predicate predicate = ArchivalEmployeeQueryConstructorAdmin.buildPredicate(root, cb, username, role,
                firstName, lastName, organization, phone);

        countQuery.select(cb.countDistinct(root));
        countQuery.where(predicate);
        return countQuery;
    }
}
