package com.softserve.edu.specification;

import lombok.Getter;
import lombok.Setter;

/**
 * Class used for creation search criterion
 */
@Getter
@Setter
public class SearchCriterion<T extends Enum<T>> {
    private String key;
    private String entityField;
    private Operator operation;
    private String additionKey;
    private ValueType valueType;
    private String joinEntityField;
    private Class<T> enumKeyType;

    /**
     * @param key             key to filter
     * @param entityField     name of corresponding entity field
     * @param operation       type of operation
     * @param enumKeyType     type of Enum, if entity field is Enum
     * @param additionKey     addition key to filter (e.g: for between operation)
     * @param valueType       type of key value
     * @param joinEntityField name of entity field that need to be accessed by join if need to make join on entityField
     */
    public SearchCriterion(String key, String entityField, Operator operation, Class<T> enumKeyType, String additionKey, ValueType valueType, String joinEntityField) {
        this.key = key;
        this.entityField = entityField;
        this.operation = operation;
        this.enumKeyType = enumKeyType;
        this.additionKey = additionKey;
        this.valueType = valueType;
        this.joinEntityField = joinEntityField;
    }

    /**
     * @param key         key to filter
     * @param entityField name of corresponding entity field
     * @param operation   type of operation
     * @param enumKeyType type of Enum, if entity field is Enum
     */
    public SearchCriterion(String key, String entityField, Operator operation, Class<T> enumKeyType) {
        this(key, entityField, operation, enumKeyType, null, null, null);
    }

    /**
     * @param key         key to filter
     * @param entityField name of corresponding entity field
     * @param operation   type of operation
     * @param valueType   type of key value
     */
    public SearchCriterion(String key, String entityField, Operator operation, ValueType valueType) {
        this(key, entityField, operation, null, null, valueType, null);
    }

    /**
     * @param key             key to filter
     * @param entityField     name of corresponding entity field
     * @param operation       type of operation
     * @param valueType       type of key value
     * @param joinEntityField name of entity field that need to be accessed by join if need to make join on entityField
     */
    public SearchCriterion(String key, String entityField, Operator operation, ValueType valueType, String joinEntityField) {
        this(key, entityField, operation, null, null, valueType, joinEntityField);
    }

    /**
     * @param key         key to filter
     * @param entityField name of corresponding entity field
     * @param operation   type of operation
     * @param additionKey addition key to filter (e.g: for between operation)
     */
    public SearchCriterion(String key, String entityField, Operator operation, String additionKey) {
        this(key, entityField, operation, null, additionKey, null, null);
    }

    /**
     * Get Enum from value by enumKeyType class
     *
     * @param key value that converts to corresponding enum
     * @return Enum of enumKeyType class
     */
    public T getEnum(String key) {
        return Enum.valueOf(enumKeyType, key);
    }

    /**
     *
     */
    public enum Operator {
        EQUAL, EQUAL_BY_ENUM, BETWEEN_DATE, LIKE
    }

    public enum ValueType {
        STRING, LONG, INTEGER, BOOLEAN, DATE
    }

}
