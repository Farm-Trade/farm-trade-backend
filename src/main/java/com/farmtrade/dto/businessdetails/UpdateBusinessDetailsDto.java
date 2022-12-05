package com.farmtrade.dto.businessdetails;

import com.farmtrade.entities.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBusinessDetailsDto {
    private String name;
    private String address;
    private PaymentType paymentType;
}
