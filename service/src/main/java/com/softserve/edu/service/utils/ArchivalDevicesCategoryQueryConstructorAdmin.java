package com.softserve.edu.service.utils;

import com.softserve.edu.entity.device.Device;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

/**
 * @deprecated this class have a lot of repeated code <br/>
 * {need to be replaced and removed}<br/>
 * use {@link com.softserve.edu.specification.SpecificationBuilder} instead
 */
@Deprecated
public class ArchivalDevicesCategoryQueryConstructorAdmin {
    static Logger logger = Logger.getLogger(ArchivalDevicesCategoryQueryConstructorAdmin.class);

    public static CriteriaQuery<Device> buildSearchQuery(Long id, String deviceType, String deviceName, String sortCriteria, String sortOrder, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> criteriaQuery = cb.createQuery(Device.class);
        Root<Device> root = criteriaQuery.from(Device.class);

        Predicate predicate = ArchivalDevicesCategoryQueryConstructorAdmin.buildPredicate(id, deviceType, deviceName, root, cb);
        if((sortCriteria != null)&&(sortOrder != null)) {
            criteriaQuery.orderBy(SortCriteriaDeviceCategory.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get("id")));
        }
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }

    public static CriteriaQuery<Long> buildCountQuery (Long id, String deviceType, String deviceName, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Device> root = countQuery.from(Device.class);

        Predicate predicate = ArchivalDevicesCategoryQueryConstructorAdmin.buildPredicate(id, deviceType, deviceName, root, cb);
        countQuery.select(cb.count(root));
        countQuery.where(predicate);

        return countQuery;
    }
    private static Predicate buildPredicate(Long id, String deviceType, String deviceName, Root<Device> root, CriteriaBuilder cb) {
        Predicate queryPredicate = cb.conjunction();
        if (id != null) {
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Long>(cb, root.get("id")),
                    "%" + id.toString() + "%"), queryPredicate);
        }
        if ((deviceType != null)&&(deviceType.length()>0)) {
            queryPredicate = cb.and(cb.equal(root.get("deviceType"),
                    Device.DeviceType.valueOf(deviceType.trim())), queryPredicate);
        }
        if ((deviceName != null)&&(deviceName.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("deviceName"), "%" + deviceName + "%"),
                    queryPredicate);
        }

        return queryPredicate;
    }
}
