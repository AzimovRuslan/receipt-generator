package com.example.clevertec.controller;

import com.example.clevertec.model.dto.DiscountCardDTO;
import com.example.clevertec.service.DiscountCardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/discount_cards")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class DiscountCardController {

    private final DiscountCardService discountCardService;

    @GetMapping("/")
    public List<DiscountCardDTO> getAll() {

        return discountCardService.findAll();
    }

    @GetMapping("/{id}")
    public DiscountCardDTO get(@PathVariable("id") Long id) {

        return discountCardService.findById(id);
    }

    @PostMapping
    public DiscountCardDTO create(@Valid @RequestBody DiscountCardDTO discountCardDTO) {

        return discountCardService.create(discountCardDTO);
    }

    @PutMapping("/{id}")
    public DiscountCardDTO update(@PathVariable("id") Long id, @Valid @RequestBody DiscountCardDTO discountCardDTO) {

        return discountCardService.update(id, discountCardDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {

        discountCardService.deleteById(id);
    }
}
