package com.reusemi.controller;

import com.reusemi.entity.Item; // ← CORRIGIDO: entity, não model
import com.reusemi.entity.Usuario; // ← CORRIGIDO: entity, não model
import com.reusemi.repo.ItemRepository; // ← CORRIGIDO: repository, não repo
import com.reusemi.repo.UsuarioRepository; // ← CORRIGIDO: repository, não repo
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
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()) // ← CORRIGIDO: Usuario entity
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Item item = new Item();
        item.setTitulo(titulo);
        item.setDescricao(descricao);
        item.setCategoria(categoria);
        item.setCondicao(condicao);
        item.setUsuario(usuario);
        item.setDisponivel(true);
        item.setDataCriacao(LocalDateTime.now());

        itemRepository.save(item);
        return "redirect:/itens/meus-itens?sucesso=true";
    }

    @PostMapping("/{id}/disponivel")
    public String toggleDisponivel(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        item.setDisponivel(!item.getDisponivel()); // ← CORRIGIDO: getDisponivel()
        itemRepository.save(item);
        return "redirect:/itens/meus-itens";
    }

    // Método adicional para visualizar item
    @GetMapping("/{id}")
    public String verItem(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        model.addAttribute("item", item);
        return "itens/detalhes";
    }

    // Método para editar item
    @GetMapping("/{id}/editar")
    public String editarItem(@PathVariable Long id, Model model, Principal principal) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // Verificar se o usuário é o dono do item
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!item.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/itens/meus-itens?erro=acesso_negado";
        }

        model.addAttribute("item", item);
        return "itens/editar";
    }

    @PostMapping("/{id}/editar")
    public String atualizarItem(@PathVariable Long id,
                                @RequestParam String titulo,
                                @RequestParam String descricao,
                                @RequestParam String categoria,
                                @RequestParam String condicao,
                                Principal principal) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // Verificar se o usuário é o dono do item
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!item.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/itens/meus-itens?erro=acesso_negado";
        }

        item.setTitulo(titulo);
        item.setDescricao(descricao);
        item.setCategoria(categoria);
        item.setCondicao(condicao);
        item.setDataAtualizacao(LocalDateTime.now());

        itemRepository.save(item);
        return "redirect:/itens/meus-itens?sucesso=atualizado";
    }
}