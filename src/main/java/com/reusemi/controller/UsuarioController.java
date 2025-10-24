package com.reusemi.controller;

import com.reusemi.entity.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção via construtor (mais seguro)
    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Página de cadastro
    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    // Processar cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute Usuario usuario, Model model) {
        try {
            System.out.println("=== TENTANDO SALVAR USUÁRIO ===");
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Nome: " + usuario.getNome());

            // Verificar se email já existe
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                model.addAttribute("erro", "Este email já está cadastrado!");
                return "cadastro";
            }

            // Criptografar senha
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);

            // Garantir que o nível está definido
            if (usuario.getNivel() == null) {
                usuario.setNivel("USER");
            }

            // Salvar usuário
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            System.out.println("Usuário salvo com ID: " + usuarioSalvo.getId());

            model.addAttribute("sucesso", "Cadastro realizado com sucesso! Faça login.");
            return "login";

        } catch (Exception e) {
            System.out.println("=== ERRO AO SALVAR USUÁRIO ===");
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            return "cadastro";
        }
    }

    // API para criar usuário (para testes)
    @PostMapping("/api/criar")
    @ResponseBody
    public String criarUsuario(@RequestParam String nome,
                               @RequestParam String email,
                               @RequestParam String senha) {
        try {
            System.out.println("=== CRIANDO USUÁRIO VIA API ===");

            // Verificar se email já existe
            if (usuarioRepository.existsByEmail(email)) {
                return "Erro: Este email já está cadastrado!";
            }

            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario.setNivel("USER");

            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            return "Usuário criado com sucesso! ID: " + usuarioSalvo.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }

    // Método para listar usuários (para teste)
    @GetMapping("/listar")
    @ResponseBody
    public String listarUsuarios() {
        long count = usuarioRepository.count();
        return "Total de usuários no banco: " + count;
    }
}