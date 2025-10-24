package com.reusemi.controller;

import com.reusemi.entity.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Email ou senha inválidos!");
        }
        if (logout != null) {
            model.addAttribute("message", "Você foi desconectado com sucesso!");
        }

        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@RequestParam String nome,
                                   @RequestParam String email,
                                   @RequestParam String telefone,
                                   @RequestParam String senha,
                                   RedirectAttributes redirectAttributes) {

        System.out.println("📝 Tentando cadastrar usuário: " + email);

        try {
            // Verificar se email já existe
            if (usuarioRepository.findByEmail(email).isPresent()) {
                redirectAttributes.addFlashAttribute("erro", "Email já cadastrado");
                return "redirect:/cadastro";
            }

            // Criar novo usuário usando a entidade completa
            Usuario novoUsuario = Usuario.builder()
                    .nome(nome)
                    .email(email)
                    .telefone(telefone)
                    .senha(passwordEncoder.encode(senha))
                    .nivel("USER")
                    .build();

            usuarioRepository.save(novoUsuario);

            System.out.println("✅ Usuário cadastrado com sucesso: " + email);
            System.out.println("📊 Detalhes: " + novoUsuario.toString());

            redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado com sucesso! Faça login.");
            return "redirect:/login";

        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            return "redirect:/cadastro";
        }
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/como-funciona")
    public String comoFunciona() {
        return "como-funciona";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}