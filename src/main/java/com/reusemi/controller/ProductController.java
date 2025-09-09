package com.reusemi.controller;

import com.reusemi.service.ProductService;
import com.reusemi.dto.ProductDTO;
import com.reusemi.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Permite requests de qualquer origem (ajuste para produção)
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/products - Todos os produtos
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET /api/products/{id} - Produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/products/category/{category} - Produtos por categoria
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    // GET /api/products/search?q={keyword} - Buscar produtos
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String q) {
        return ResponseEntity.ok(productService.searchProducts(q));
    }

    // GET /api/products/featured - Produtos em destaque
    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        return ResponseEntity.ok(productService.getFeaturedProducts());
    }

    // GET /api/products/discounted - Produtos com desconto
    @GetMapping("/discounted")
    public ResponseEntity<List<ProductDTO>> getDiscountedProducts() {
        return ResponseEntity.ok(productService.getDiscountedProducts());
    }

    // GET /api/categories - Todas as categorias
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

    // GET /api/categories/{id} - Categoria por ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> category = productService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/categories/{id}/products - Produtos por ID de categoria
    @GetMapping("/categories/{id}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(id));
    }
}