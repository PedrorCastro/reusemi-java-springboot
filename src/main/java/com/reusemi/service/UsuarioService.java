package com.reusemi.service;

import com.reusemi.model.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public Usuario registrar(String nome, String email, String senha) {
        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(encoder.encode(senha));
        u.setNivel("user");
        return repo.save(u);
    }
}
