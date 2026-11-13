package com.example.productapi.service;

import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.exception.InvalidQuantityException;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        return response(repository.create(product(null, request)));
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(this::response).toList();
    }

    public List<ProductResponse> search(String name) {
        String query = name.strip().toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(product -> product.name().toLowerCase(Locale.ROOT).contains(query))
                .map(this::response)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        return repository.findById(id).map(this::response).orElseThrow(() -> notFound(id));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product updated = product(id, request);
        if (!repository.replace(updated)) {
            throw notFound(id);
        }
        return response(updated);
    }

    public void deleteProduct(Long id) {
        if (!repository.deleteById(id)) {
            throw notFound(id);
        }
    }

    private Product product(Long id, ProductRequest request) {
        if (request.quantity() > 1000) {
            throw new InvalidQuantityException("La quantité ne peut pas dépasser 1000");
        }
        return new Product(id, request.name(), request.description(), request.price(), request.quantity());
    }

    private ProductResponse response(Product product) {
        return new ProductResponse(product.id(), product.name(), product.description(), product.price(), product.quantity());
    }

    private ResourceNotFoundException notFound(Long id) {
        return new ResourceNotFoundException("Produit introuvable avec l'id: " + id);
    }
}
