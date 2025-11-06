package com.reusemi.service.impl;

import com.reusemi.dto.ProductDTO;
import com.reusemi.dto.CategoryDTO;
import com.reusemi.entity.Product;
import com.reusemi.entity.Category;
import com.reusemi.repo.ProductRepository;
import com.reusemi.repo.CategoryRepository;
import com.reusemi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return List.of();
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ProductDTO> getRecentProducts() {
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .limit(8)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory() != null &&
                        product.getCategory().getName().equalsIgnoreCase(category))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findAll().stream()
                .filter(product ->
                        (product.getName() != null && product.getName().toLowerCase().contains(keyword.toLowerCase())) ||
                                (product.getDescription() != null && product.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                )
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findAll().stream()
                .filter(Product::isFeatured)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory() != null &&
                        product.getCategory().getId().equals(categoryId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getDiscountedProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.getDiscount() != null && product.getDiscount() > 0)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Métodos de conversão - CORRIGIDOS
    private ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setFeatured(product.isFeatured());
        dto.setImageUrl(product.getImageUrl());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setFeatured(dto.isFeatured());
        product.setImageUrl(dto.getImageUrl());

        // Se for um novo produto (id null), setar datas de criação
        if (dto.getId() == null) {
            product.setCreatedAt(LocalDateTime.now());
        } else {
            product.setCreatedAt(dto.getCreatedAt());
        }
        product.setUpdatedAt(LocalDateTime.now());

        // Se tiver categoryId, buscar a categoria
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            product.setCategory(category);
        }

        return product;
    }


}