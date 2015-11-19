package com.softserve.edu.specification;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for generating specifications
 */
public abstract class SpecificationBuilder<T> {
    private static Logger logger = Logger.getLogger(SpecificationBuilder.class);

    private List<SearchCriterion> criteria;
    private Map<String, String> searchValues;

    /**
     * Used template pattern to initialise search criteria list
     * @param searchValues map of values to search
     */
    public SpecificationBuilder(Map<String, String> searchValues) {
        this.criteria = initCriteria();
        if (criteria == null && searchValues == null) {
            throw new NullPointerException();
        }
        this.searchValues = searchValues;
    }

    /**
     * Initializing search criteria
     * @return list of sorting criteria
     */
    protected abstract List<SearchCriterion> initCriteria();

    /**
     * Builds specification predicate from search criteria on map of search values
     * @return specification predicate
     */
    public Specification<T> buildPredicate() {
        return (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            criteria.stream().forEach(sc -> {
                String key = sc.getKey();

                if (checkKey(searchValues, key)) {
                    Path path = getCriteriaPath(sc, root);
                    switch (sc.getOperation()) {
                        case LIKE:
                            predicates.add(cb.like(path, "%" + searchValues.get(key) + "%"));
                            break;
                        case EQUAL: {
                            predicates.add(buildEqualPredicate(sc, root, cb));
                            break;
                        }
                        case EQUAL_BY_ENUM:
                            predicates.add(cb.equal(path, sc.getEnum(searchValues.get(key).trim())));
                            break;
                        case BETWEEN_DATE:
                            if (checkKey(searchValues, sc.getAdditionKey())) {
                                DateTimeFormatter dbDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

                                try {
                                    LocalDate startDate = LocalDate.parse(searchValues.get(key), dbDateTimeFormatter);
                                    LocalDate endDate = LocalDate.parse(searchValues.get(sc.getAdditionKey()), dbDateTimeFormatter);
                                    predicates.add(cb.between(path, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate)));
                                } catch (DateTimeParseException e) {
                                    logger.error("Cannot parse date", e);
                                }
                            }
                            break;
                    }
                }
            });

            Predicate queryPredicate = cb.conjunction();
            if (!predicates.isEmpty()) {
                queryPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            return queryPredicate;

        };
    }

    /**
     * Builds equal predicate
     * @param sc search criterion
     * @param root A root in the from clause
     * @param cb criteria builder
     * @return predicate for specification
     */
    private Predicate buildEqualPredicate(SearchCriterion sc, Root root, CriteriaBuilder cb) {
        String key = sc.getKey();
        Path path = getCriteriaPath(sc, root);
        switch (sc.getValueType()) {
            case BOOLEAN:
                return cb.equal(path, Boolean.valueOf(searchValues.get(key)));
            case INTEGER:
                // "-1" to mark as not to filter by
                if (!searchValues.get(key).equals("-1")) {
                    return cb.equal(path, Integer.valueOf(searchValues.get(key)));
                }
            default:
                return cb.equal(path, String.valueOf(searchValues.get(key)));
        }
    }

    /**
     * Create criteria path for search criterion
     * @param sc search criterion
     * @param root A root in the from clause
     * @return attribute path
     */
    private Path getCriteriaPath(SearchCriterion sc, Root root) {
        String key = sc.getEntityField();
        if (sc.getJoinEntityField() != null) {
            return root.join(key).get(sc.getJoinEntityField());
        } else {
            return root.get(key);
        }
    }

    private static boolean checkKey(Map<String, String> searchKeys, String key) {
        return searchKeys.containsKey(key) && (checkValue(searchKeys.get(key)));
    }

    protected static boolean checkValue(String value) {
        return (value != null) && (!value.isEmpty());
    }


    /**
     * Create Sort object for Specification executor by criteria and order
     *
     * @param sortCriteria sorting criterion
     * @param sortOrder sorting order
     * @return Sort object
     */
    public abstract Sort getSort(String sortCriteria, String sortOrder);

    /**
     * Creates a new PageRequest with sort parameters applied.
     * @param pageNumber zero-based page index.
     * @param itemsPerPage the size of the page to be returned.
     * @param sortCriteria sorting criterion
     * @param sortOrder sorting order
     * @return page request
     */
    public abstract Pageable constructPageSpecification(int pageNumber, int itemsPerPage, String sortCriteria, String sortOrder);

}
