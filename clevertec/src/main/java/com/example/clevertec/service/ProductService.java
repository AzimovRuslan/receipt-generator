package com.example.clevertec.service;

import com.example.clevertec.mapper.ProductMapper;
import com.example.clevertec.model.dto.ProductDTO;
import com.example.clevertec.model.entity.Product;
import com.example.clevertec.repository.ProductRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityNotFoundException;
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
        return productMapper.toDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + "not found")));
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDtoDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + "not found"));
        Product productDetails = productMapper.toEntity(productDtoDetails);

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        product = productRepository.save(product);

        return productMapper.toDto(product);
    }
}
