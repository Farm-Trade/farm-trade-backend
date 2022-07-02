package com.farmtrade.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ApproveProductNameDto implements Serializable {
    private boolean approved;
}
