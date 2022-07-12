package com.farmtrade.filters;

import com.farmtrade.filters.FilterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.activation.UnsupportedDataTypeException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private FilterType type;
    private Object value;

    public static SearchCriteria searchCriteriaFactory(String key, FilterType type, Object value) throws UnsupportedDataTypeException {
        if (type.equals(FilterType.BETWEEN) && value != null && value.getClass().getComponentType() == List.class) {
            String typeName = value.getClass().getTypeName();
            throw new UnsupportedDataTypeException(
                    "Only value with List type is allowed in combine with `BETWEEN` filer, but now it is " + typeName
            );
        }
        return new SearchCriteria(key, type, value);
    }
}
