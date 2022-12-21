package com.example.clevertec.mapper;

import com.example.clevertec.model.dto.ProductDTO;
import com.example.clevertec.model.dto.ReceiptDTO;
import com.example.clevertec.model.entity.Product;
import com.example.clevertec.model.entity.Receipt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public ReceiptDTO toDto(Receipt receipt) {

        ModelMapper mapper = new ModelMapper();

        return mapper.map(receipt, ReceiptDTO.class);
    }

    public Receipt toEntity(ReceiptDTO receiptDTO) {

        ModelMapper mapper = new ModelMapper();

        return mapper.map(receiptDTO, Receipt.class);
    }
}
