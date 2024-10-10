package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public final class Category {
    private static final Map<String, Category> instances = new HashMap<>();
    private final String name;

    // Privat konstruktor för att förhindra extern instansiering
    private Category(String name) {
        this.name = capitalize(name); // Använder en metod för att hantera namnformatering
    }

    // Statisk metod för att hämta eller skapa en kategori
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        return instances.computeIfAbsent(name.toLowerCase(), Category::new);
    }

    // Returnerar kategorins namn
    public String getName() {
        return name;
    }

    // Metod för att kapitalisera första bokstaven i namnet
    private String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
