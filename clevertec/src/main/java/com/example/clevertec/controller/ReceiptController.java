package com.example.clevertec.controller;

import com.example.clevertec.model.dto.ReceiptDTO;
import com.example.clevertec.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping("/")
    public List<ReceiptDTO> getAll() {

        return receiptService.findAll();
    }

    @GetMapping("/{id}")
    public ReceiptDTO get(@PathVariable("id") Long id) {

        return receiptService.findById(id);
    }

    @PostMapping
    public ReceiptDTO create(@Valid @RequestBody ReceiptDTO receiptDTO) {

        return receiptService.create(receiptDTO);
    }

    @PutMapping("/{id}")
    public ReceiptDTO update(@PathVariable("id") Long id, @Valid @RequestBody ReceiptDTO receiptDTO) {

        return receiptService.update(id, receiptDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {

        receiptService.deleteById(id);
    }
}
