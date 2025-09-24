package com.reusemi.config;

import com.reusemi.model.Usuario;
import com.reusemi.repo.UsuarioRepository;
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
            // Verifica se já existe o usuário teste
            if (usuarioRepository.findByEmail("teste@teste.com").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setEmail("teste@teste.com");
                usuario.setSenha(passwordEncoder.encode("123456")); // Senha: 123456
                usuario.setNome("Usuário Teste");
                usuario.setNivel("USER");

                usuarioRepository.save(usuario);
                System.out.println("=== USUÁRIO TESTE CRIADO ===");
                System.out.println("Email: teste@teste.com");
                System.out.println("Senha: 123456");
            }
        };
    }
}