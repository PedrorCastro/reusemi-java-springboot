package com.reusemi.controller;

import com.reusemi.model.Item;
import com.reusemi.model.Usuario;
import com.reusemi.repo.ItemRepository;
import com.reusemi.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listarItens(Model model) {
        model.addAttribute("itens", itemRepository.findByDisponivelTrue());
        return "itens/lista";
    }

    @GetMapping("/meus-itens")
    public String meusItens(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Use o método correto - por ID do usuário (mais eficiente)
        model.addAttribute("itens", itemRepository.findByUsuarioId(usuario.getId()));
        return "itens/meus-itens";
    }

    @GetMapping("/novo")
    public String novoItem() {
        return "itens/novo";
    }

    @PostMapping("/novo")
    public String criarItem(@RequestParam String titulo,
                           @RequestParam String descricao,
                           @RequestParam String categoria,
                           @RequestParam String condicao,
                           Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Item item = new Item();
        item.setTitulo(titulo);
        item.setDescricao(descricao);
        item.setCategoria(categoria);
        item.setCondicao(condicao);
        item.setUsuario(usuario);
        item.setDataCriacao(LocalDateTime.now());
        
        itemRepository.save(item);
        return "redirect:/itens/meus-itens?sucesso=true";
    }

    @PostMapping("/{id}/disponivel")
    public String toggleDisponivel(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        item.setDisponivel(!item.isDisponivel());
        itemRepository.save(item);
        return "redirect:/itens/meus-itens";
    }
}