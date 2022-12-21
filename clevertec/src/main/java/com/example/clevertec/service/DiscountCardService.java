package com.example.clevertec.service;

import com.example.clevertec.aspect.constant.Constants;
import com.example.clevertec.aspect.exception.SuchRecordAlreadyExistsException;
import com.example.clevertec.mapper.DiscountCardMapper;
import com.example.clevertec.model.dto.DiscountCardDTO;
import com.example.clevertec.model.entity.DiscountCard;
import com.example.clevertec.repository.DiscountCardRepository;
import com.example.clevertec.service.utility.RecordRecipient;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
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

        return discountCardMapper.toDto(RecordRecipient.getRecordFromTable(id, discountCardRepository, Constants.DISCOUNT_CARD_NOT_FOUND));
    }

    @Override
    public DiscountCardDTO create(DiscountCardDTO discountCardDTO) {

        final DiscountCard discountCard = discountCardMapper.toEntity(discountCardDTO);

        DiscountCard discountCardForCreate = discountCardRepository.findAll()
                .stream()
                .filter(d -> d.getNumber() == discountCard.getNumber())
                .findFirst()
                .orElse(null);

        if (discountCardForCreate == null) {
            discountCardRepository.save(discountCard);
        } else {
            throw new SuchRecordAlreadyExistsException(Constants.RECORD_ALREADY_EXISTS + this.getClass().getName());
        }

        return discountCardMapper.toDto(discountCard);
    }

    @Override
    public void deleteById(Long id) {

        discountCardRepository.deleteById(id);
    }

    @Override
    public DiscountCardDTO update(Long id, DiscountCardDTO discountCardDtoDetails) {

        DiscountCard discountCard = RecordRecipient.getRecordFromTable(id, discountCardRepository, Constants.DISCOUNT_CARD_NOT_FOUND);
        DiscountCard discountCardDetails = discountCardMapper.toEntity(discountCardDtoDetails);

        discountCard.setNumber(discountCardDetails.getNumber());
        discountCard.setDiscountPercent(discountCardDetails.getDiscountPercent());

        discountCard = discountCardRepository.save(discountCard);

        return discountCardMapper.toDto(discountCard);
    }
}
