package com.reusemi.repo;

import com.reusemi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar produtos mais recentes (últimos 8)
    List<Product> findTop8ByOrderByIdDesc();

    // Buscar produtos em destaque
    List<Product> findByFeaturedTrue();

    // Buscar produtos por nome da categoria
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    // Buscar produtos por ID da categoria
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    // Buscar produtos por nome ou descrição
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(@Param("keyword") String keyword);

    // Buscar produtos com desconto
    List<Product> findByDiscountGreaterThan(Double discount);
}