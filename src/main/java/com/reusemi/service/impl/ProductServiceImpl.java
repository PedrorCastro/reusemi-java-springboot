package com.reusemi.service.impl;

import com.reusemi.service.ProductService;
import com.reusemi.dto.ProductDTO;
import com.reusemi.dto.CategoryDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    // Dados mockados - em produção viriam do banco de dados
    private final List<ProductDTO> products = Arrays.asList(
            new ProductDTO(1L, "Smartphone Reusemi", "Smartphone de última geração",
                    new BigDecimal("999.99"), "Eletrônicos", "/images/phone.jpg", 50),
            new ProductDTO(2L, "Notebook Pro", "Notbook para trabalho e jogos",
                    new BigDecimal("1999.99"), "Eletrônicos", "/images/laptop.jpg", 25),
            new ProductDTO(3L, "Camiseta Premium", "Camiseta de algodão 100%",
                    new BigDecimal("49.99"), "Roupas", "/images/shirt.jpg", 100)
    );

    private final List<CategoryDTO> categories = Arrays.asList(
            new CategoryDTO(1L, "Eletrônicos", "Dispositivos eletrônicos", "/images/electronics.jpg"),
            new CategoryDTO(2L, "Roupas", "Roupas e acessórios", "/images/clothing.jpg"),
            new CategoryDTO(3L, "Casa", "Produtos para casa", "/images/home.jpg")
    );

    @Override
    public List<ProductDTO> getAllProducts() {
        return products;
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword) {
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        product.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    @Override
    public List<ProductDTO> getFeaturedProducts() {
        return products.stream()
                .limit(3) // Apenas os primeiros 3 como featured
                .toList();
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categories;
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        Optional<CategoryDTO> category = getCategoryById(categoryId);
        if (category.isPresent()) {
            return getProductsByCategory(category.get().getName());
        }
        return List.of();
    }

    @Override
    public List<ProductDTO> getDiscountedProducts() {
        // Mock de produtos com desconto
        return products.stream()
                .filter(product -> product.getId() % 2 == 0) // Apenas produtos com ID par como exemplo
                .toList();
    }
}