package com.softserve.edu.service.utils;

import com.softserve.edu.entity.device.CounterType;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.SpecificationBuilder} instead
 */
@Deprecated
public class ArchivalCounterTypeQueryConstructorAdmin {
    static Logger logger = Logger.getLogger(ArchivalCounterTypeQueryConstructorAdmin.class);

    public static CriteriaQuery<CounterType> buildSearchQuery(String name, String symbol, String standardSize,
                                                              String manufacturer, Integer calibrationInterval,
                                                              Integer yearIntroduction, String gost,
                                                              String sortCriteria, String sortOrder, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CounterType> criteriaQuery = cb.createQuery(CounterType.class);
        Root<CounterType> root = criteriaQuery.from(CounterType.class);

        Predicate predicate = ArchivalCounterTypeQueryConstructorAdmin.buildPredicate(name, symbol, standardSize,
                manufacturer, calibrationInterval, yearIntroduction, gost, root, cb);
        if((sortCriteria != null)&&(sortOrder != null)) {
            criteriaQuery.orderBy(SortCriteriaCounterType.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get("id")));
        }
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }

    public static CriteriaQuery<Long> buildCountQuery (String name, String symbol, String standardSize,
                                                       String manufacturer, Integer calibrationInterval,
                                                       Integer yearIntroduction, String gost, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CounterType> root = countQuery.from(CounterType.class);

        Predicate predicate = ArchivalCounterTypeQueryConstructorAdmin.buildPredicate(name, symbol, standardSize,
                manufacturer, calibrationInterval, yearIntroduction, gost, root, cb);
        countQuery.select(cb.count(root));
        countQuery.where(predicate);

        return countQuery;
    }
    private static Predicate buildPredicate(String name, String symbol, String standardSize, String manufacturer,
                                            Integer calibrationInterval, Integer yearIntroduction, String gost,
                                            Root<CounterType> root, CriteriaBuilder cb) {
        Predicate queryPredicate = cb.conjunction();
        if ((name != null)&&(name.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("name"), "%" + name + "%"),
                    queryPredicate);
        }
        if ((symbol != null)&&(symbol.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("symbol"), "%" + symbol + "%"),
                    queryPredicate);
        }
        if ((standardSize != null)&&(standardSize.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("standardSize"), "%" + standardSize + "%"),
                    queryPredicate);
        }
        if ((manufacturer != null)&&(manufacturer.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("manufacturer"), "%" + manufacturer + "%"),
                    queryPredicate);
        }
        if (calibrationInterval != null) {
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Integer>(cb, root.get("calibrationInterval")),
                    "%" + calibrationInterval.toString() + "%"), queryPredicate);
        }
        if ((yearIntroduction != null)) {
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Integer>(cb, root.get("yearIntroduction")),
                    "%" + yearIntroduction.toString() + "%"), queryPredicate);
        }
        if ((gost != null)&&(gost.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("gost"), "%" + gost + "%"),
                    queryPredicate);
        }
        return queryPredicate;
    }
}
