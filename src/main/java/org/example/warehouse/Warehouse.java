package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {

    private static Warehouse instance;
    private final String name;
    private final List<ProductRecord> products; // Använd ArrayList för att bevara insättningsordningen
    private final Set<ProductRecord> changedProducts;

    private Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.changedProducts = new HashSet<>();
    }

    public static Warehouse getInstance(String name) {
        if (instance == null) {
            instance = new Warehouse(name);
        } else {
            instance.clear(); // Rensa produkter om instansen redan finns
        }
        return instance;
    }

    public static Warehouse getInstance() {
        return getInstance("MyStore");
    }

    public void clear() {
        products.clear();
        changedProducts.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products)); // Returnera en oföränderlig lista
    }

    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (id == null) {
            id = UUID.randomUUID(); // Generera UUID om det inte anges
        }
        if (price == null) {
            price = BigDecimal.ZERO; // Sätt default pris om det är null
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can't be null or negative.");
        }
        for (ProductRecord product : products) {
            if (product.uuid().equals(id)) {
                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
            }
        }

        ProductRecord product = new ProductRecord(id, name, category, price);
        products.add(product); // Lägg till produkten i listan
        return product; // Returnera den tillagda produkten
    }

    public Optional<ProductRecord> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can't be null or negative.");
        }
        ProductRecord product = getProductById(id).orElseThrow(() -> new IllegalArgumentException("Product with that id doesn't exist."));
        product.setPrice(newPrice);
        changedProducts.add(product);
    }

    public List<ProductRecord> getProductsBy(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        List<ProductRecord> result = new ArrayList<>();
        for (ProductRecord product : products) {
            if (product.category().equals(category)) {
                result.add(product);
            }
        }
        return result;
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> groupedProducts = new HashMap<>();
        for (ProductRecord product : products) {
            groupedProducts.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return groupedProducts;
    }

    public List<ProductRecord> getChangedProducts() {
        return new ArrayList<>(changedProducts);
    }
}
