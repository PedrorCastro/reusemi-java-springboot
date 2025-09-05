INSERT INTO usuarios (nome, email, senha, nivel, data_criacao) VALUES
('Admin', 'admin@reusemi.com', '$2a$10$ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789', 'ADMIN', NOW()),
('João Silva', 'joao@email.com', '$2a$10$ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789', 'USER', NOW());

INSERT INTO itens (titulo, descricao, categoria, condicao, disponivel, usuario_id, data_criacao) VALUES
('Bicicleta Caloi', 'Bicicleta em bom estado, pouco uso', 'Transporte', 'Usado', true, 2, NOW()),
('Livro Java Spring', 'Livro sobre Spring Boot completo', 'Livros', 'Novo', true, 2, NOW()),
('iPhone X', 'iPhone X 64GB, funciona perfeitamente', 'Eletrônicos', 'Usado', true, 2, NOW());