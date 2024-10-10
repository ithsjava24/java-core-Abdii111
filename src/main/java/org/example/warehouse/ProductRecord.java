package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    // Konstruktor för att skapa en produkt
    public ProductRecord(UUID id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public UUID uuid() {
        return id; // Returnerar produktens ID
    }

    public String name() {
        return name; // Returnerar produktens namn
    }

    public Category category() {
        return category; // Returnerar produktens kategori
    }

    public BigDecimal price() {
        return price; // Returnerar produktens pris
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can't be null or negative.");
        }
        this.price = price; // Metod för att uppdatera priset
    }
}
