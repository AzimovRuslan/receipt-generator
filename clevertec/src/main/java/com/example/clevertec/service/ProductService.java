package com.example.clevertec.service;

import com.example.clevertec.aspect.constant.Constants;
import com.example.clevertec.aspect.exception.SuchRecordAlreadyExistsException;
import com.example.clevertec.mapper.ProductMapper;
import com.example.clevertec.model.dto.ProductDTO;
import com.example.clevertec.model.entity.Product;
import com.example.clevertec.repository.ProductRepository;
import com.example.clevertec.service.utility.RecordRecipient;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class ProductService implements Service<ProductDTO> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAll() {

        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {

        return productMapper.toDto(RecordRecipient.getRecordFromTable(id, productRepository, Constants.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {

        final Product product = productMapper.toEntity(productDTO);

        Product productForCreate = productRepository.findAll()
                .stream()
                .filter(p -> p.getName().equals(product.getName()))
                .findFirst()
                .orElse(null);

        if (productForCreate == null) {
            productRepository.save(product);
        } else {
            throw new SuchRecordAlreadyExistsException(Constants.RECORD_ALREADY_EXISTS + this.getClass().getName());
        }

        return productMapper.toDto(product);
    }

    @Override
    public void deleteById(Long id) {

        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDtoDetails) {

        Product product = RecordRecipient.getRecordFromTable(id, productRepository, Constants.PRODUCT_NOT_FOUND);
        Product productDetails = productMapper.toEntity(productDtoDetails);

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        product = productRepository.save(product);

        return productMapper.toDto(product);
    }
}
