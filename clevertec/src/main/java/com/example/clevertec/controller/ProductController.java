package com.example.clevertec.controller;

import com.example.clevertec.model.dto.ProductDTO;
import com.example.clevertec.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public List<ProductDTO> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductDTO create(@Valid @RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }
}
