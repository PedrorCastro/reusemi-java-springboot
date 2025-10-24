package com.reusemi.service;

import com.reusemi.dto.ProductDTO;
import com.reusemi.dto.CategoryDTO;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    // Produtos
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProductById(Long id);
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> searchProducts(String keyword);
    List<ProductDTO> getFeaturedProducts();

    // Categorias
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(Long id);
    List<ProductDTO> getProductsByCategoryId(Long categoryId);

    // Produtos em promoção
    List<ProductDTO> getDiscountedProducts();
}