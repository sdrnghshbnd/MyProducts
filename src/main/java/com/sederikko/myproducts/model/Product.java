package com.sederikko.myproducts.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Product {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1); // Atomic counter for ID generation
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;

    public Product(String name, BigDecimal price, String description) {
        this.id = ID_GENERATOR.getAndIncrement(); // Automatically generate unique ID
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}