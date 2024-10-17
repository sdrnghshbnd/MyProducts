package com.sederikko.myproducts.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class Product {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1); // Atomic counter for ID generation
    // Getters and Setters
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal salePrice;
    private String description;

    public Product(String name, BigDecimal price, BigDecimal salePrice,String description) {
        this.id = ID_GENERATOR.getAndIncrement(); // Automatically generate unique ID
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.description = description;
    }

}