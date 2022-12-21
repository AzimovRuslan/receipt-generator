package com.example.clevertec.service;

import com.example.clevertec.aspect.constant.Constants;
import com.example.clevertec.mapper.DiscountCardMapper;
import com.example.clevertec.mapper.ReceiptMapper;
import com.example.clevertec.model.dto.DiscountCardDTO;
import com.example.clevertec.model.dto.ReceiptDTO;
import com.example.clevertec.model.entity.DiscountCard;
import com.example.clevertec.model.entity.Receipt;
import com.example.clevertec.repository.ReceiptRepository;
import com.example.clevertec.service.utility.RecordRecipient;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class ReceiptService implements Service<ReceiptDTO>{

    private final ReceiptRepository receiptRepository;
    private final ProductService productService;
    private final DiscountCardService discountCardService;
    private final ReceiptMapper receiptMapper;
    private final DiscountCardMapper discountCardMapper;

    @Override
    public List<ReceiptDTO> findAll() {

        return receiptRepository.findAll().stream().map(receiptMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ReceiptDTO findById(Long id) {

        return receiptMapper.toDto(RecordRecipient.getRecordFromTable(id, receiptRepository, Constants.RECEIPT_NOT_FOUND));
    }

    @Override
    public ReceiptDTO create(ReceiptDTO receiptDTO) {

        Receipt receipt = receiptMapper.toEntity(receiptDTO);
        BigDecimal totalCoast = receipt.getTotalCoast();

        Map<Long, Integer> purchases = receipt.getPurchases();

        for (Map.Entry<Long, Integer> entry : purchases.entrySet()) {
            BigDecimal price = productService.findById(entry.getKey()).getPrice().multiply(BigDecimal.valueOf(entry.getValue()));

            if (entry.getValue() <= Constants.REQUIRED_NUMBER_PRODUCTS_FOR_DISCOUNT) {
                totalCoast = totalCoast.add(price);
            } else {
                BigDecimal priceWithDiscount = price.multiply(Constants.DISCOUNT_MULTIPLIER);
                totalCoast = totalCoast.add(priceWithDiscount);
            }
        }

        DiscountCard discountCard = null;

        if (receipt.getDiscountCard() != null) {
            totalCoast = totalCoast.multiply(BigDecimal.valueOf(1.00).subtract(BigDecimal.valueOf(receipt.getDiscountCard().getDiscountPercent()).setScale(Constants.SCALE_FOR_PRICE).divide(BigDecimal.valueOf(100.00))));

            for (DiscountCardDTO discountCardDto : discountCardService.findAll()) {
                DiscountCard discountCard1 = discountCardMapper.toEntity(discountCardDto);
                if (discountCard1.getNumber() == receipt.getDiscountCard().getNumber()) {
                    discountCard = discountCard1;
                }
            }
        }

        receipt.setDiscountCard(discountCard);
        receipt.setTotalCoast(totalCoast.setScale(Constants.SCALE_FOR_PRICE, BigDecimal.ROUND_CEILING));

        receipt = receiptRepository.save(receipt);

        return receiptMapper.toDto(receipt);
    }

    @Override
    public void deleteById(Long id) {

        receiptRepository.deleteById(id);
    }

    @Override
    public ReceiptDTO update(Long id, ReceiptDTO receiptDtoDetails) {

        Receipt receipt = RecordRecipient.getRecordFromTable(id, receiptRepository, Constants.RECEIPT_NOT_FOUND);
        Receipt receiptDetails = receiptMapper.toEntity(receiptDtoDetails);

        receipt.setPurchases(receiptDetails.getPurchases());
        receipt.setDiscountCard(receiptDetails.getDiscountCard());
        receipt.setTotalCoast(receiptDetails.getTotalCoast());

        receipt = receiptRepository.save(receipt);

        return receiptMapper.toDto(receipt);
    }
}
