-- Database: reusemi_db
CREATE DATABASE IF NOT EXISTS reusemi_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE reusemi_db;

-- Table: role (Tabela de perfis/roles)
CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: user (Tabela de usuários)
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_email (email),
    INDEX idx_user_username (username)
);

-- Table: user_roles (Tabela de relacionamento usuário-roles)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    INDEX idx_user_roles_user (user_id),
    INDEX idx_user_roles_role (role_id)
);

-- Table: address (Tabela de endereços)
CREATE TABLE IF NOT EXISTS address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(50) DEFAULT 'Brasil',
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    INDEX idx_address_user (user_id)
);

-- Table: product (Tabela de produtos)
CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    sku VARCHAR(100) UNIQUE,
    stock_quantity INT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_product_sku (sku),
    INDEX idx_product_active (active)
);

-- Table: category (Tabela de categorias)
CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: product_categories (Relacionamento produto-categoria)
CREATE TABLE IF NOT EXISTS product_categories (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- Insert dados iniciais
INSERT IGNORE INTO role (name, description) VALUES
('ROLE_ADMIN', 'Administrador do sistema'),
('ROLE_USER', 'Usuário comum'),
('ROLE_MODERATOR', 'Moderador');

INSERT IGNORE INTO category (name, description) VALUES
('Electronics', 'Dispositivos eletrônicos'),
('Clothing', 'Roupas e acessórios'),
('Books', 'Livros e publicações'),
('Home', 'Produtos para casa');

-- Insert usuário admin inicial (senha: admin123)
INSERT IGNORE INTO user (username, email, password, first_name, last_name, active) VALUES
('admin', 'admin@reusemi.com', '$2a$10$TKh8H1.PfQx37YgCzwiKb.KjNyWgaHb9cbcoQgdIVFlYg7B77UdFm', 'Admin', 'System', TRUE);

-- Atribuir role de admin ao usuário admin
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM user u, role r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

-- Insert produtos de exemplo
INSERT IGNORE INTO product (name, description, price, sku, stock_quantity) VALUES
('Smartphone XYZ', 'Smartphone avançado com câmera de 48MP', 899.99, 'SMART-XYZ-001', 50),
('Notebook ABC', 'Notebook com 8GB RAM e SSD 256GB', 1299.99, 'NOTE-ABC-002', 30),
('Camiseta Básica', 'Camiseta 100% algodão', 29.99, 'CLOTH-CAM-003', 100);

-- Associar produtos a categorias
INSERT IGNORE INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM product p, category c
WHERE p.sku = 'SMART-XYZ-001' AND c.name = 'Electronics';

INSERT IGNORE INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM product p, category c
WHERE p.sku = 'NOTE-ABC-002' AND c.name = 'Electronics';

INSERT IGNORE INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM product p, category c
WHERE p.sku = 'CLOTH-CAM-003' AND c.name = 'Clothing';

-- Visualizar dados inseridos
SELECT '=== ROLES ===' as '';
SELECT * FROM role;

SELECT '=== CATEGORIAS ===' as '';
SELECT * FROM category;

SELECT '=== USUÁRIOS ===' as '';
SELECT u.*, r.name as role_name
FROM user u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN role r ON ur.role_id = r.id;

SELECT '=== PRODUTOS ===' as '';
SELECT p.*, c.name as category_name
FROM product p
LEFT JOIN product_categories pc ON p.id = pc.product_id
LEFT JOIN category c ON pc.category_id = c.id;