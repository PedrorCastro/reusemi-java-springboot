package com.reusemi.repo;

import com.reusemi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Buscar categoria por nome
    Optional<Category> findByName(String name);

    // Buscar categorias ativas
    List<Category> findByActiveTrue();

    // Buscar categorias ativas ordenadas por nome
    List<Category> findByActiveTrueOrderByNameAsc();

    // Buscar categoria por nome (case insensitive)
    Optional<Category> findByNameIgnoreCase(String name);

    // Verificar se existe categoria com determinado nome
    boolean existsByName(String name);

    // Buscar categorias com produtos
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products WHERE c.active = true")
    List<Category> findCategoriesWithProducts();

    // Buscar categorias populares (com mais produtos)
    @Query("SELECT c, COUNT(p) as productCount FROM Category c LEFT JOIN c.products p WHERE c.active = true GROUP BY c ORDER BY productCount DESC")
    List<Object[]> findPopularCategories();

    // Buscar categorias por parte do nome
    List<Category> findByNameContainingIgnoreCase(String name);

    // Contar produtos por categoria
    @Query("SELECT c.name, COUNT(p) FROM Category c LEFT JOIN c.products p WHERE c.active = true GROUP BY c.id, c.name")
    List<Object[]> countProductsByCategory();
}