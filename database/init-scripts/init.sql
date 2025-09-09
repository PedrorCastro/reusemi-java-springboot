CREATE DATABASE IF NOT EXISTS reusemi_db;
USE reusemi_db;

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Inserir usuário de exemplo
INSERT IGNORE INTO user (username, email, password, first_name, last_name)
VALUES ('admin', 'admin@reusemi.com', 'temp_password', 'Admin', 'User');