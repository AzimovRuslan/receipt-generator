package com.example.clevertec.aspect.constant;

import java.math.BigDecimal;

public class Constants {

    public static final int REQUIRED_NUMBER_PRODUCTS_FOR_DISCOUNT = 5;
    public static final BigDecimal DISCOUNT_MULTIPLIER = BigDecimal.valueOf(0.90);
    public static final int SCALE_FOR_PRICE = 2;

    public static final String RECEIVED_ALL = "Received all -> ";
    public static final String RECEIVED_BY_ID = "Received by id -> ";
    public static final String CREATE_RECORD = "Create record -> ";
    public static final String DELETE_BY_ID = "Delete by id -> ";
    public static final String UPDATE = "Update -> ";

    public static final String RECEIPT_NOT_FOUND = "Receipt not found -> id=";
    public static final String PRODUCT_NOT_FOUND = "Product not found -> id=";
    public static final String DISCOUNT_CARD_NOT_FOUND = "Discount card not found -> id=";
    public static final String RECORD_ALREADY_EXISTS = "Such record already exists -> ";
}
