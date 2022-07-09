package com.farmtrade.filters;

import com.farmtrade.filters.FilterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private FilterType type;
    private Object value;
}
