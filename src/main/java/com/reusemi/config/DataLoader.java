package com.reusemi.config;

import com.reusemi.entity.Usuario; // ← CORRIGIDO: entity, não model
import com.reusemi.repo.UsuarioRepository; // ← CORRIGIDO: repository, não repo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner loadData(UsuarioRepository usuarioRepository) {
        return args -> {
            // Verifica se já existe o usuário admin
            if (usuarioRepository.findByEmail("admin@reusemi.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail("admin@reusemi.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setNivel("ADMIN");
                admin.setTelefone("(11) 99999-9999");
                admin.setEndereco("Endereço do Administrador");

                usuarioRepository.save(admin);
                System.out.println("=== USUÁRIO ADMIN CRIADO ===");
                System.out.println("Email: admin@reusemi.com");
                System.out.println("Senha: admin123");
                System.out.println("Nível: ADMIN");
            }

            // Verifica se já existe o usuário teste
            if (usuarioRepository.findByEmail("teste@reusemi.com").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNome("Usuário Teste");
                usuario.setEmail("teste@reusemi.com");
                usuario.setSenha(passwordEncoder.encode("123456"));
                usuario.setNivel("USER");
                usuario.setTelefone("(11) 98888-8888");
                usuario.setEndereco("Endereço do Usuário Teste");

                usuarioRepository.save(usuario);
                System.out.println("=== USUÁRIO TESTE CRIADO ===");
                System.out.println("Email: teste@reusemi.com");
                System.out.println("Senha: 123456");
                System.out.println("Nível: USER");
            }

            // Verifica se já existe algum usuário, se não, cria um básico
            if (usuarioRepository.count() == 0) {
                Usuario usuarioBasico = new Usuario();
                usuarioBasico.setNome("João Silva");
                usuarioBasico.setEmail("joao@reusemi.com");
                usuarioBasico.setSenha(passwordEncoder.encode("senha123"));
                usuarioBasico.setNivel("USER");

                usuarioRepository.save(usuarioBasico);
                System.out.println("=== USUÁRIO BÁSICO CRIADO ===");
                System.out.println("Email: joao@reusemi.com");
                System.out.println("Senha: senha123");
            }

            // Mostra estatísticas no console
            long totalUsuarios = usuarioRepository.count();
            long adminCount = usuarioRepository.countByNivel("ADMIN");
            long userCount = usuarioRepository.countByNivel("USER");

            System.out.println("=== ESTATÍSTICAS DO BANCO ===");
            System.out.println("Total de usuários: " + totalUsuarios);
            System.out.println("Administradores: " + adminCount);
            System.out.println("Usuários comuns: " + userCount);
        };
    }
}