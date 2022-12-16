package com.example.clevertec.service;

import com.example.clevertec.mapper.DiscountCardMapper;
import com.example.clevertec.model.dto.DiscountCardDTO;
import com.example.clevertec.model.entity.DiscountCard;
import com.example.clevertec.repository.DiscountCardRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class DiscountCardService implements Service<DiscountCardDTO>{

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardMapper discountCardMapper;

    @Override
    public List<DiscountCardDTO> findAll() {
        return discountCardRepository.findAll().stream().map(discountCardMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DiscountCardDTO findById(Long id) {
        return discountCardMapper.toDto(discountCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DiscountCard with id " + id + "not found")));
    }

    @Override
    public DiscountCardDTO create(DiscountCardDTO discountCardDTO) {
        DiscountCard discountCard = discountCardMapper.toEntity(discountCardDTO);
        discountCard = discountCardRepository.save(discountCard);
        return discountCardMapper.toDto(discountCard);
    }

    @Override
    public void deleteById(Long id) {
        discountCardRepository.deleteById(id);
    }

    @Override
    public DiscountCardDTO update(Long id, DiscountCardDTO discountCardDtoDetails) {
        DiscountCard discountCard = discountCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discount card with id " + id + "not found"));
        DiscountCard discountCardDetails = discountCardMapper.toEntity(discountCardDtoDetails);

        discountCard.setNumber(discountCardDetails.getNumber());
        discountCard.setDiscountPercent(discountCardDetails.getDiscountPercent());

        discountCard = discountCardRepository.save(discountCard);

        return discountCardMapper.toDto(discountCard);
    }
}
