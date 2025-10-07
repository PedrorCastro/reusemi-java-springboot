package com.reusemi.service;

import com.reusemi.entity.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔐 Tentando carregar usuário: " + username);

        try {
            Usuario usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(() -> {
                        System.out.println("❌ Usuário não encontrado: " + username);
                        return new UsernameNotFoundException("Usuário não encontrado: " + username);
                    });

            System.out.println("✅ Usuário encontrado: " + usuario.getEmail());
            System.out.println("📝 Nível: " + usuario.getNivel());
            System.out.println("🔑 Senha (hash): " + (usuario.getSenha() != null ? "[HASH PRESENTE]" : "[SENHA AUSENTE]"));

            // Verificar se a senha existe
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                System.out.println("❌ Senha não configurada para o usuário: " + username);
                throw new UsernameNotFoundException("Senha não configurada para o usuário: " + username);
            }

            // Determinar role
            String role = "USER";
            if (usuario.getNivel() != null && usuario.getNivel().equalsIgnoreCase("ADMIN")) {
                role = "ADMIN";
            }

            // Criar UserDetails
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getSenha())
                    .roles(role)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();

        } catch (Exception e) {
            System.out.println("💥 ERRO ao carregar usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}