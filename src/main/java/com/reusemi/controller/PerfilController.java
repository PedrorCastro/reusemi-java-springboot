package com.reusemi.controller;

import com.reusemi.entity.Usuario;
import com.reusemi.repo.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PerfilController {

    private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/perfil")
    public String perfil(Principal principal, Model model) {
        logger.info("=== TENTANDO ACESSAR PERFIL ===");

        try {
            // Verifica se o principal existe
            if (principal == null) {
                logger.error("PRINCIPAL É NULO - usuário não autenticado");
                return "redirect:/login";
            }

            logger.info("Principal name: {}", principal.getName());

            // Busca o usuário
            var usuarioOpt = usuarioRepository.findByEmail(principal.getName());
            if (usuarioOpt.isEmpty()) {
                logger.error("USUÁRIO NÃO ENCONTRADO no banco para email: {}", principal.getName());
                model.addAttribute("error", "Usuário não encontrado");
                return "error";
            }

            Usuario usuario = usuarioOpt.get();
            logger.info("Usuário encontrado: {} - {}", usuario.getNome(), usuario.getEmail());

            // Adiciona ao modelo
            model.addAttribute("usuario", usuario);
            logger.info("=== PERFIL CARREGADO COM SUCESSO ===");

            return "perfil";

        } catch (Exception e) {
            logger.error("ERRO CRÍTICO no perfil:", e);
            model.addAttribute("error", "Erro: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/perfil/atualizar")
    public String atualizarPerfil(@RequestParam String nome,
                                  @RequestParam String telefone,
                                  @RequestParam String endereco,
                                  Principal principal,
                                  Model model) {
        try {
            logger.info("Atualizando perfil para: {}", principal.getName());

            if (principal == null) {
                return "redirect:/login";
            }

            var usuarioOpt = usuarioRepository.findByEmail(principal.getName());
            if (usuarioOpt.isEmpty()) {
                model.addAttribute("error", "Usuário não encontrado");
                return "error";
            }

            Usuario usuario = usuarioOpt.get();
            usuario.setNome(nome);
            usuario.setTelefone(telefone);
            usuario.setEndereco(endereco);

            usuarioRepository.save(usuario);

            model.addAttribute("success", "Perfil atualizado com sucesso!");
            model.addAttribute("usuario", usuario);

            return "perfil";

        } catch (Exception e) {
            logger.error("Erro ao atualizar perfil:", e);
            model.addAttribute("error", "Erro ao atualizar: " + e.getMessage());
            return "perfil";
        }
    }
}