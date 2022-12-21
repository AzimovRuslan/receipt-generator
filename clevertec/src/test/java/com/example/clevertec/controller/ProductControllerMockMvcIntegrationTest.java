package com.example.clevertec.controller;

import com.example.clevertec.mapper.ProductMapper;
import com.example.clevertec.model.dto.ProductDTO;
import com.example.clevertec.model.entity.Product;
import com.example.clevertec.repository.ProductRepository;
import com.example.clevertec.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerMockMvcIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductMapper productMapper;

    private Product product;

    @BeforeEach
    void init() {

        product = new Product();
        product.setName("Chocolate");
        product.setPrice(BigDecimal.valueOf(2.20));
    }

    @AfterEach
    void resetDB() {
        productRepository.deleteAll();
    }

    @Test
    public void givenProduct_whenAdd_thenStatus201andPersonReturned() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/products")
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Chocolate"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(2.20)));
    }

    @Test
    public void givenId_whenGetExistingProduct_thenStatus200andProductReturned() throws Exception {

        long id = createTestProduct("Small snickers", BigDecimal.valueOf(1.30)).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Small snickers"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(1.30)));
    }

    @Test
    public void giveProduct_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

        long id = createTestProduct("Small snickers", BigDecimal.valueOf(1.30)).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/products/{id}", id)
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Chocolate"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(2.20)));
    }

    @Test
    public void givenProduct_whenDeleteProduct_thenStatus200() throws Exception {

        Product testProduct = productMapper.toEntity(createTestProduct("Small snickers", BigDecimal.valueOf(1.30)));

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/products/{id}", testProduct.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenProducts_whenGetProducts_thenStatus200() throws Exception {
        createTestProduct("Small snickers", BigDecimal.valueOf(1.30));
        createTestProduct("Chocolate", BigDecimal.valueOf(2.20));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ProductDTO createTestProduct(String name, BigDecimal price) {

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);

        return productService.create(productMapper.toDto(testProduct));
    }
}
