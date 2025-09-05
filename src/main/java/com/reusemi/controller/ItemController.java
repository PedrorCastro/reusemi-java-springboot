package com.reusemi.controller;

import com.reusemi.model.Item;
import com.reusemi.model.Usuario;
import com.reusemi.repo.ItemRepository;
import com.reusemi.repo.UsuarioRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Validated
public class ItemController {
    private final ItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;

    public ItemController(ItemRepository itemRepository, UsuarioRepository usuarioRepository) {
        this.itemRepository = itemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/categorias")
    public String categorias(Model model) {
        List<String> categorias = itemRepository.findDistinctCategorias();
        model.addAttribute("categorias", categorias);
        return "categorias";
    }

    @GetMapping("/anunciar")
    public String anunciarPage() { return "anunciar"; }

    @PostMapping("/anunciar")
    public String anunciar(@AuthenticationPrincipal User user,
                           @RequestParam @NotBlank String nome,
                           @RequestParam(required = false) String descricao,
                           @RequestParam(required = false) String categoria,
                           @RequestParam(required = false) String cidade) {
        if (user == null) return "redirect:/login";
        Usuario dono = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();
        Item item = new Item();
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setCategoria(categoria);
        item.setCidade(cidade);
        item.setDono(dono);
        itemRepository.save(item);
        return "redirect:/perfil";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() { return "admin"; }
}
