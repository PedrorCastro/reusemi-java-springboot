package com.reusemi.controller;

import com.reusemi.model.Usuario;
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
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    // ESTE método deve ficar APENAS no AuthController
    @PostMapping("/cadastro")
    public String cadastrarUsuario(@RequestParam String nome,
                                  @RequestParam String email,
                                  @RequestParam String senha,
                                  RedirectAttributes redirectAttributes) {
        
        if (usuarioRepository.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("erro", "Email já cadastrado");
            return "redirect:/cadastro";
        }
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        novoUsuario.setNivel("USER");
        
        usuarioRepository.save(novoUsuario);
        redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado com sucesso!");
        return "redirect:/login";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/como-funciona")
    public String comoFunciona() {
        return "como-funciona";
    }
}