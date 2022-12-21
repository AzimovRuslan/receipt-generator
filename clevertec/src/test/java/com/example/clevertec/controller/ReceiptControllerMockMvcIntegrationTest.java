package com.example.clevertec.controller;

import com.example.clevertec.mapper.DiscountCardMapper;
import com.example.clevertec.mapper.ProductMapper;
import com.example.clevertec.mapper.ReceiptMapper;
import com.example.clevertec.model.dto.ReceiptDTO;
import com.example.clevertec.model.entity.DiscountCard;
import com.example.clevertec.model.entity.Product;
import com.example.clevertec.model.entity.Receipt;
import com.example.clevertec.repository.DiscountCardRepository;
import com.example.clevertec.repository.ProductRepository;
import com.example.clevertec.repository.ReceiptRepository;
import com.example.clevertec.service.DiscountCardService;
import com.example.clevertec.service.ProductService;
import com.example.clevertec.service.ReceiptService;
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
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerMockMvcIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountCardService discountCardService;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private DiscountCardRepository discountCardRepository;

    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private DiscountCardMapper discountCardMapper;

    @Autowired
    private ProductMapper productMapper;

    private Receipt receipt;
    private Map<Long, Integer> purchases1;
    private Map<Long, Integer> purchases2;
    private Product product1;
    private Product product2;
    private DiscountCard discountCard1;
    private DiscountCard discountCard2;

    @AfterEach
    void resetDB() {

        receiptRepository.deleteAll();
        productRepository.deleteAll();
        discountCardRepository.deleteAll();
    }

    @BeforeEach
    void init() {

        product1 = new Product();
        product1.setName("Chocolate");
        product1.setPrice(BigDecimal.valueOf(2.20));
        productService.create(productMapper.toDto(product1));

        product2 = new Product();
        product2.setName("Small snickers");
        product2.setPrice(BigDecimal.valueOf(1.30));
        productService.create(productMapper.toDto(product2));

        discountCard1 = new DiscountCard();
        discountCard1.setNumber(1234);
        discountCard1.setDiscountPercent(10);
        discountCardService.create(discountCardMapper.toDto(discountCard1));

        discountCard2 = new DiscountCard();
        discountCard2.setNumber(4321);
        discountCard2.setDiscountPercent(13);
        discountCardService.create(discountCardMapper.toDto(discountCard2));

        purchases1 = new HashMap<>();
        purchases1.put(1L, 3);
        purchases1.put(2L, 2);

        purchases2 = new HashMap<>();
        purchases2.put(1L, 5);
        purchases2.put(2L, 2);

        receipt = new Receipt();
        receipt.setPurchases(purchases1);
        receipt.setDiscountCard(discountCard1);
        receipt.setTotalCoast(BigDecimal.valueOf(0.00));
    }

    @Test
    public void givenReceipt_whenAdd_thenStatus201andReceiptReturned() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/receipts")
                        .content(objectMapper.writeValueAsString(receipt))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountCard.number").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountCard.discountPercent").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCoast").value(BigDecimal.valueOf(8.28)));
    }

    @Test
    public void givenId_whenGetExistingReceipt_thenStatus200andReceiptReturned() throws Exception {

        long id = createTestReceipt(purchases1, discountCard1, BigDecimal.valueOf(0.00)).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/receipts/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountCard.number").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountCard.discountPercent").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCoast").value(BigDecimal.valueOf(8.28)));
    }

    @Test
    public void givenReceipt_whenDeleteReceipt_thenStatus200() throws Exception {

        Receipt testReceipt = receiptMapper.toEntity(createTestReceipt(purchases1, discountCard1, BigDecimal.valueOf(0.00)));

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/receipts/{id}", testReceipt.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenReceipt_whenGetReceipt_thenStatus200() throws Exception {

        createTestReceipt(purchases1, discountCard1, BigDecimal.valueOf(0.00));
        createTestReceipt(purchases2, discountCard2, BigDecimal.valueOf(0.00));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/receipts/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ReceiptDTO createTestReceipt(Map<Long, Integer> purchases, DiscountCard discountCard, BigDecimal totalCoast) {

        Receipt receipt = new Receipt();
        receipt.setPurchases(purchases);
        receipt.setDiscountCard(discountCard);
        receipt.setTotalCoast(totalCoast);

        return receiptService.create(receiptMapper.toDto(receipt));
    }
}
