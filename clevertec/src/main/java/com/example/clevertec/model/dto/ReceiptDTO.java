package com.example.clevertec.model.dto;

import com.example.clevertec.model.entity.DiscountCard;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ReceiptDTO {

    private Long id;
    private Map<Integer, Integer> purchases = new HashMap<>();
    private DiscountCard discountCard;
    private BigDecimal totalCoast = BigDecimal.valueOf(0.00);
}
