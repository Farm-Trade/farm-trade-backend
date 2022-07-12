package com.farmtrade.filters;

import com.farmtrade.filters.FilterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.activation.UnsupportedDataTypeException;

@Data
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private FilterType type;
    private Object value;

    private SearchCriteria(String key, FilterType type, Object value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public static SearchCriteria searchCriteriaFactory(String key, FilterType type, Object value) throws UnsupportedDataTypeException {
        if (type.equals(FilterType.BETWEEN) && !value.getClass().isArray()) {
            String typeName = value.getClass().getTypeName();
            throw new UnsupportedDataTypeException(
                    "Only value with List type is allowed in combine with `BETWEEN` filer, but now it is " + typeName
            );
        }
        return new SearchCriteria(key, type, value);
    }
}
