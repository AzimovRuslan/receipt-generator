package com.example.clevertec.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "receipt_product_mapping",
            joinColumns = {@JoinColumn(name = "receipt_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "product_id")
    @Column(name = "amount")
    private Map<Long, Integer> purchases = new HashMap<>();

    @OneToOne
    private DiscountCard discountCard;

    private BigDecimal totalCoast = BigDecimal.valueOf(0.00);
}
