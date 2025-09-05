package com.reusemi.controller;

import com.reusemi.model.Usuario;
import com.reusemi.repo.UsuarioRepository;
import com.reusemi.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam @NotBlank String nome,
                            @RequestParam @Email String email,
                            @RequestParam @NotBlank String senha,
                            Model model) {
        try {
            usuarioService.registrar(nome, email, senha);
            model.addAttribute("flash", "Cadastro realizado com sucesso!");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/perfil")
    public String perfil(@AuthenticationPrincipal User user, Model model) {
        if (user == null) return "redirect:/login";
        Usuario u = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();
        model.addAttribute("usuario", u);
        return "perfil";
    }
}
