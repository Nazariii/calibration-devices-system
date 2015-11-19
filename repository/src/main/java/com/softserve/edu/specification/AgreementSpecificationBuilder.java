package com.softserve.edu.specification;

import com.softserve.edu.entity.device.Device;
import com.softserve.edu.entity.organization.Agreement;
import com.softserve.edu.specification.sort.AgreementSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class AgreementSpecificationBuilder extends SpecificationBuilder<Agreement> {

    static Logger logger = Logger.getLogger(AgreementSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String CUSTOMER_JOIN_NAME = "customer.name";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String EXECUTOR_JOIN_NAME = "executor.name";
    public static final String EXECUTOR_NAME = "executorName";
    public static final String NUMBER = "number";
    public static final String DEVICE_TYPE = "deviceType";
    public static final String DEVICE_COUNT = "deviceCount";
    public static final String START_DATE_TO_SEARCH = "startDateToSearch";
    public static final String END_DATE_TO_SEARCH = "endDateToSearch";
    public static final String IS_AVAILABLE = "isAvailable";
    public static final String DATE = "date";

    public AgreementSpecificationBuilder(Map<String, String> searchValues) {
        super(searchValues);
    }

    /**
     * Initialize criteria for Agreement Specification
     * @return search criteria
     */
    @Override
    protected List<SearchCriterion> initCriteria(){
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriterion<>(NUMBER, "number", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(CUSTOMER_NAME, "customer", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "name"));
        searchCriteria.add(new SearchCriterion<>(EXECUTOR_NAME, "executor", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "name"));
        searchCriteria.add(new SearchCriterion<>(DEVICE_COUNT, "deviceCount", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.INTEGER));
        searchCriteria.add(new SearchCriterion<>(IS_AVAILABLE, "isAvailable", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.BOOLEAN));
        searchCriteria.add(new SearchCriterion<>(START_DATE_TO_SEARCH, "date", SearchCriterion.Operator.BETWEEN_DATE, END_DATE_TO_SEARCH ));
        searchCriteria.add(new SearchCriterion<>(DEVICE_TYPE, "deviceType", SearchCriterion.Operator.EQUAL_BY_ENUM, Device.DeviceType.class));
        return searchCriteria;
    }

    /**
     * Create Sort object for Specification executor by criteria and order
     * @param sortCriteria sorting criterion
     * @param sortOrder sorting order
     * @return Sort object
     */
    @Override
    public Sort getSort(String sortCriteria, String sortOrder) {
        if (checkValue(sortCriteria) && checkValue(sortOrder)) {
            return AgreementSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return AgreementSortCriteria.UNDEFINED.getSort("asc");
        }
    }

    /**
     * Creates a new PageRequest with sort parameters applied.
     * @param pageNumber zero-based page index.
     * @param itemsPerPage the size of the page to be returned.
     * @param sortCriteria sorting criterion
     * @param sortOrder sorting order
     * @return
     */
    @Override
    public Pageable constructPageSpecification(int pageNumber, int itemsPerPage, String sortCriteria, String sortOrder) {
        return new PageRequest(pageNumber, itemsPerPage, getSort(sortCriteria, sortOrder));
    }
}
