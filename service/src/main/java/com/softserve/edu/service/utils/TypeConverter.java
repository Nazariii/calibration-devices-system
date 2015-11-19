package com.softserve.edu.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for converting types
 */
public class TypeConverter {

    /**
     * Converts set of enums to set of their string equivalents
     *
     * @param enums set witch need to be converted
     * @return set of string equivalents
     */
    public static <T extends Enum> Set<String> enumToString(Set<T> enums) {

        return enums.stream().map(Enum::name).collect(Collectors.toSet());
    }

    /**
     * Convert set of strings to set of their enum equivalents
     *
     * @param strings set witch need to be converted
     * @param clazz   object of enum class to which need to be converted
     * @param <T>     type of enum class to which need to be converted
     * @return set of converted enums
     */
    public static <T extends Enum<T>> Set<T> stringToEnum(Set<String> strings, Class<T> clazz) {
        Set<T> res = new HashSet<>();
        strings.stream()
                .forEach(s -> res.add(T.valueOf(clazz, s)));
        return res;
    }

    /**
     * Convert object to map of its fields and values
     * @param object for converting
     * @return map of fields and values
     */
    public static Map<String, String> ObjectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        //noinspection unchecked
        Map<String, String> map = objectMapper.convertValue(object, Map.class);
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, newEntry -> String.valueOf(newEntry.getValue())));
    }

}
