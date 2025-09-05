package com.reusemi.controller;

import com.reusemi.model.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/perfil")
    public String perfil(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/perfil/atualizar")
    public String atualizarPerfil(@RequestParam String nome,
                                 @RequestParam String telefone,
                                 @RequestParam String endereco,
                                 Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        usuario.setNome(nome);
        usuario.setTelefone(telefone);
        usuario.setEndereco(endereco);
        usuarioRepository.save(usuario);
        return "redirect:/perfil?sucesso=true";
    }
}