package com.example.clevertec.controller;

import com.example.clevertec.mapper.DiscountCardMapper;
import com.example.clevertec.model.dto.DiscountCardDTO;
import com.example.clevertec.model.entity.DiscountCard;
import com.example.clevertec.repository.DiscountCardRepository;
import com.example.clevertec.service.DiscountCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
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

@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
public class DiscountCardControllerMockMvcIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DiscountCardService discountCardService;

    @Autowired
    private DiscountCardRepository discountCardRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiscountCardMapper discountCardMapper;

    private DiscountCard discountCard;

    @BeforeEach
    void init() {

        discountCard = new DiscountCard();
        discountCard.setNumber(1234);
        discountCard.setDiscountPercent(10);
    }

    @AfterEach
    void resetDB() {
        discountCardRepository.deleteAll();
    }

    @Test
    public void givenDiscountCard_whenAdd_thenStatus201andDiscountCardReturned() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/discount_cards")
                        .content(objectMapper.writeValueAsString(discountCard))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountPercent").value(10));
    }

    @Test
    public void givenId_whenGetExistingDiscountCard_thenStatus200andDiscountCardReturned() throws Exception {

        long id = createTestDiscountCard(4321, 13).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/discount_cards/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(4321))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountPercent").value(13));
    }

    @Test
    public void giveDiscountCard_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

        long id = createTestDiscountCard(4321, 13).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/discount_cards/{id}", id)
                        .content(objectMapper.writeValueAsString(discountCard))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountPercent").value(10));
    }

    @Test
    public void givenDiscountCard_whenDeleteDiscountCard_thenStatus200() throws Exception {

        DiscountCard testDiscountCard = discountCardMapper.toEntity(createTestDiscountCard(4321, 13));

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/discount_cards/{id}", testDiscountCard.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenDiscountCard_whenGetDiscountCard_thenStatus200() throws Exception {
        createTestDiscountCard(1234, 10);
        createTestDiscountCard(4321, 13);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/discount_cards/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public DiscountCardDTO createTestDiscountCard(int number, int discountPercent) {

        DiscountCard testDiscountCard = new DiscountCard();
        testDiscountCard.setNumber(number);
        testDiscountCard.setDiscountPercent(discountPercent);

        return discountCardService.create(discountCardMapper.toDto(testDiscountCard));
    }
}
