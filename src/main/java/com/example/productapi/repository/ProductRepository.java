package com.example.productapi.repository;

import com.example.productapi.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final AtomicLong sequence = new AtomicLong();
    private final ConcurrentSkipListMap<Long, Product> products = new ConcurrentSkipListMap<>();

    public Product create(Product product) {
        Long id = sequence.incrementAndGet();
        Product created = new Product(id, product.name(), product.description(), product.price(), product.quantity());
        products.put(id, created);
        return created;
    }

    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public boolean replace(Product product) {
        return products.replace(product.id(), product) != null;
    }

    public boolean deleteById(Long id) {
        return products.remove(id) != null;
    }
}
