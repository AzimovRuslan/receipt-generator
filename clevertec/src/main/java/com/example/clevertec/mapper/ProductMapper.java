package com.example.clevertec.mapper;

import com.example.clevertec.model.dto.ProductDTO;
import com.example.clevertec.model.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {

        ModelMapper mapper = new ModelMapper();

        return mapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {

        ModelMapper mapper = new ModelMapper();

        return mapper.map(productDTO, Product.class);
    }
}
