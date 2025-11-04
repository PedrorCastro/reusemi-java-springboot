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
<<<<<<< HEAD
    List<ProductDTO> getRecentProducts();
    List<ProductDTO> getDiscountedProducts();

    // Método para salvar produto
    ProductDTO saveProduct(ProductDTO productDTO);
=======
>>>>>>> 56605cd3e29d058c1166042c73bc3ea6cd7d8064

    // Categorias
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(Long id);
    List<ProductDTO> getProductsByCategoryId(Long categoryId);
<<<<<<< HEAD
=======

    // Produtos em promoção
    List<ProductDTO> getDiscountedProducts();
>>>>>>> 56605cd3e29d058c1166042c73bc3ea6cd7d8064
}