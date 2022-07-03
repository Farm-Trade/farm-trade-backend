package com.farmtrade.dto;

import com.farmtrade.entities.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductNameCreateUpdateDto {
    private String name;

    private ProductType type;
}
