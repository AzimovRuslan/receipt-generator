package com.example.clevertec.mapper;

import com.example.clevertec.model.dto.DiscountCardDTO;
import com.example.clevertec.model.entity.DiscountCard;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardMapper {

    public DiscountCardDTO toDto(DiscountCard discountCard) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(discountCard, DiscountCardDTO.class);
    }

    public DiscountCard toEntity(DiscountCardDTO discountCardDTO) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(discountCardDTO, DiscountCard.class);
    }
}
