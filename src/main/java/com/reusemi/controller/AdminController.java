package com.reusemi.controller;

import com.reusemi.entity.Usuario;
import com.reusemi.repo.UsuarioRepository;
import com.reusemi.repo.ItemRepository;
import com.reusemi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public String painelAdmin(Model model, Principal principal) {
        // Estatísticas para o painel
        model.addAttribute("totalUsuarios", adminService.getTotalUsuarios());
        model.addAttribute("totalItens", adminService.getTotalItens());
        model.addAttribute("trocasRealizadas", adminService.getTrocasRealizadas());
        model.addAttribute("novosHoje", adminService.getNovosRegistrosHoje());

        // Informações do admin logado
        Usuario admin = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
        model.addAttribute("adminNome", admin.getNome());

        return "admin/dashboard"; // ← CORRIGIDO: caminho mais específico
    }

    @GetMapping("/usuarios")
    public String gerenciarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    @GetMapping("/itens")
    public String gerenciarItens(Model model) {
        model.addAttribute("itens", itemRepository.findAll());
        return "admin/itens";
    }

    @GetMapping("/estatisticas")
    public String estatisticas(Model model) {
        model.addAttribute("estatisticas", adminService.getEstatisticasCompletas());
        return "admin/estatisticas";
    }

    @GetMapping("/moderacao")
    public String moderacao(Model model) {
        // Adicione dados de moderação se necessário
        model.addAttribute("itensReportados", itemRepository.count()); // Exemplo
        return "admin/moderacao";
    }

    @GetMapping("/configuracoes")
    public String configuracoes(Model model) {
        // Adicione configurações do sistema se necessário
        model.addAttribute("appName", "Reusemi");
        return "admin/configuracoes";
    }

    @GetMapping("/relatorios")
    public String relatorios(Model model) {
        // Adicione dados para relatórios
        model.addAttribute("estatisticas", adminService.getEstatisticasCompletas());
        return "admin/relatorios";
    }

    // Método adicional para alternar status de usuário
    @GetMapping("/usuarios/{id}/toggle-status")
    public String toggleStatusUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(!usuario.getAtivo());
        usuarioRepository.save(usuario);

        return "redirect:/admin/usuarios?sucesso=status_alterado";
    }

    // Método adicional para tornar usuário admin
    @GetMapping("/usuarios/{id}/tornar-admin")
    public String tornarAdmin(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNivel("ADMIN");
        usuarioRepository.save(usuario);

        return "redirect:/admin/usuarios?sucesso=admin_adicionado";
    }
}