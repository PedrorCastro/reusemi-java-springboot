package com.reusemi.service.impl;

import com.reusemi.service.ProductService;
import com.reusemi.dto.ProductDTO;
import com.reusemi.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());

    // Cache em memória para todos os dados
    private final List<ProductDTO> allProducts = new ArrayList<>();
    private final List<CategoryDTO> allCategories = new ArrayList<>();
    private final Map<Long, List<ProductDTO>> productsByCategoryId = new HashMap<>();

    public ProductServiceImpl() {
        initializeData();
    }

    private void initializeData() {
        // Inicializar categorias
        allCategories.add(new CategoryDTO(1L, "Eletrônicos", "Dispositivos eletrônicos e tecnologia"));
        allCategories.add(new CategoryDTO(2L, "Vestuário", "Roupas e acessórios"));
        allCategories.add(new CategoryDTO(3L, "Calçados", "Sapatos, tênis e botas"));
        allCategories.add(new CategoryDTO(4L, "Livros", "Livros de todos os gêneros"));
        allCategories.add(new CategoryDTO(5L, "Casa", "Produtos para casa e decoração"));

        // Inicializar produtos
        allProducts.add(new ProductDTO(1L, "Smartphone Premium", 899.99, "Eletrônicos", 15.0, true, 4.8, "smartphone-premium.jpg"));
        allProducts.add(new ProductDTO(2L, "Notebook Ultrafino", 1299.99, "Eletrônicos", 10.0, true, 4.7, "notebook-ultrafino.jpg"));
        allProducts.add(new ProductDTO(3L, "Tablet Android", 399.99, "Eletrônicos", 8.0, false, 4.3, "tablet-android.jpg"));
        allProducts.add(new ProductDTO(4L, "Fones Bluetooth", 149.99, "Eletrônicos", 12.0, true, 4.6, "fones-bluetooth.jpg"));

        allProducts.add(new ProductDTO(5L, "Camiseta Básica", 29.99, "Vestuário", 5.0, false, 4.5, "camiseta-basica.jpg"));
        allProducts.add(new ProductDTO(6L, "Jaqueta Jeans", 199.99, "Vestuário", 15.0, true, 4.7, "jaqueta-jeans.jpg"));
        allProducts.add(new ProductDTO(7L, "Calça Social", 89.99, "Vestuário", 7.0, false, 4.4, "calca-social.jpg"));

        allProducts.add(new ProductDTO(8L, "Tênis Esportivo", 199.99, "Calçados", 20.0, true, 4.6, "tenis-esportivo.jpg"));
        allProducts.add(new ProductDTO(9L, "Sapato Social", 249.99, "Calçados", 10.0, false, 4.8, "sapato-social.jpg"));
        allProducts.add(new ProductDTO(10L, "Chinelo Comfort", 49.99, "Calçados", 5.0, true, 4.2, "chinelo-comfort.jpg"));

        allProducts.add(new ProductDTO(11L, "Livro Best Seller", 24.99, "Livros", 0.0, false, 4.9, "livro-best-seller.jpg"));
        allProducts.add(new ProductDTO(12L, "Enciclopédia Digital", 79.99, "Livros", 25.0, true, 4.7, "enciclopedia-digital.jpg"));

        allProducts.add(new ProductDTO(13L, "Luminária Moderna", 129.99, "Casa", 15.0, true, 4.5, "luminaria-moderna.jpg"));
        allProducts.add(new ProductDTO(14L, "Jogo de Cama", 89.99, "Casa", 10.0, false, 4.3, "jogo-de-cama.jpg"));

        // Mapear produtos por categoria ID
        initializeCategoryMappings();
    }

    private void initializeCategoryMappings() {
        for (CategoryDTO category : allCategories) {
            List<ProductDTO> categoryProducts = allProducts.stream()
                    .filter(p -> {
                        // Encontrar o nome da categoria pelo ID
                        Optional<CategoryDTO> cat = allCategories.stream()
                                .filter(c -> c.getId().equals(category.getId()))
                                .findFirst();
                        return cat.isPresent() && p.getCategory().equalsIgnoreCase(cat.get().getName());
                    })
                    .collect(Collectors.toList());
            productsByCategoryId.put(category.getId(), categoryProducts);
        }
    }

    // Métodos para Produtos

    @Override
    public List<ProductDTO> getAllProducts() {
        logger.info("Retornando todos os produtos (modo autônomo)");
        return new ArrayList<>(allProducts);
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        logger.info("Buscando produto por ID: " + id);
        return allProducts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        logger.info("Buscando produtos por categoria: " + category);
        return allProducts.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword) {
        logger.info("Buscando produtos com palavra-chave: " + keyword);
        String searchTerm = keyword.toLowerCase();
        return allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(searchTerm) ||
                        p.getCategory().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getFeaturedProducts() {
        logger.info("Retornando produtos em destaque");
        return allProducts.stream()
                .filter(ProductDTO::isFeatured)
                .collect(Collectors.toList());
    }

    // Métodos para Categorias

    @Override
    public List<CategoryDTO> getAllCategories() {
        logger.info("Retornando todas as categorias");
        return new ArrayList<>(allCategories);
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        logger.info("Buscando categoria por ID: " + id);
        return allCategories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        logger.info("Buscando produtos por categoria ID: " + categoryId);
        return productsByCategoryId.getOrDefault(categoryId, Collections.emptyList());
    }

    // Métodos para produtos em promoção

    @Override
    public List<ProductDTO> getDiscountedProducts() {
        logger.info("Retornando produtos com desconto");
        return allProducts.stream()
                .filter(p -> p.getDiscount() > 0)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares para adicionar dados dinamicamente (opcional)
    public void addProduct(ProductDTO product) {
        // Gerar ID automático se não fornecido
        if (product.getId() == null) {
            Long newId = allProducts.stream()
                    .mapToLong(ProductDTO::getId)
                    .max()
                    .orElse(0L) + 1L;
            product.setId(newId);
        }

        allProducts.add(product);

        // Atualizar mapeamento por categoria
        initializeCategoryMappings();

        logger.info("Produto adicionado: " + product.getName());
    }

    public void addCategory(CategoryDTO category) {
        // Gerar ID automático se não fornecido
        if (category.getId() == null) {
            Long newId = allCategories.stream()
                    .mapToLong(CategoryDTO::getId)
                    .max()
                    .orElse(0L) + 1L;
            category.setId(newId);
        }

        allCategories.add(category);

        // Atualizar mapeamento por categoria
        initializeCategoryMappings();

        logger.info("Categoria adicionada: " + category.getName());
    }

    // Método para verificar status do serviço
    public Map<String, Object> getServiceStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("modo", "autônomo");
        status.put("totalProdutos", allProducts.size());
        status.put("totalCategorias", allCategories.size());
        status.put("timestamp", new Date());
        status.put("status", "ativo");
        return status;
    }
}