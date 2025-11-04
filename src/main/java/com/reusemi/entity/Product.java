package com.reusemi.entity;

import jakarta.persistence.*;

<<<<<<< HEAD
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

=======
@Entity
@Table(name = "products")
public class Product {
>>>>>>> 56605cd3e29d058c1166042c73bc3ea6cd7d8064
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private boolean featured;
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Construtores
    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(String name, String description, Double price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Método auxiliar para pré-atualização
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



    // Adicione este método para obter o nome da categoria
    public String getCategoryName() {
        if (category != null) {
            return category.getName();
        }
        return null;
    }

    // Método para calcular o preço final (se ainda não tiver)
    public Double getFinalPrice() {
        if (discount != null && discount > 0) {
            return price - (price * discount / 100);
        }
        return price;
    }

    // Método auxiliar para verificar se está em promoção
    public boolean isOnSale() {
        return discount != null && discount > 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price + '\''+
                '}';
    }
=======
    @Column(nullable = false)
    private String name;

    private String description;

    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    // Construtores
    public Product() {}

    public Product(String name, String description, Double price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Método auxiliar para obter o caminho completo da imagem
    @Transient
    public String getImagePath() {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            return "/images/" + imageUrl;
        }
        return null;
    }
>>>>>>> 56605cd3e29d058c1166042c73bc3ea6cd7d8064
}