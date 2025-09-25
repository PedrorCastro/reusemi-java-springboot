CREATE DATABASE IF NOT EXISTS reusemi_db;
USE reusemi_db;

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100),
    nivel VARCHAR(20) DEFAULT 'USER',
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Senhas BCrypt válidas para "123456"
INSERT IGNORE INTO usuarios (email, senha, nome, nivel) VALUES
('admin@reusemi.com', '123456', 'Admin User', 'ADMIN'),
('teste@teste.com', '$2a$10$7Sz6b5B7O3uV2q1VkQhMz.XpY7h9n8GYM6sJk5c2rLd8nW1zP4QqC', 'Usuário Teste', 'USER'),
('user@user.com', '$2a$10$7Sz6b5B7O3uV2q1VkQhMz.XpY7h9n8GYM6sJk5c2rLd8nW1zP4QqC', 'Usuário Comum', 'USER');