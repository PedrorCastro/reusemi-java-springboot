package com.reusemi.service;

import com.reusemi.repo.UsuarioRepository;
import com.reusemi.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    public long getTotalUsuarios() {
        return usuarioRepository.count();
    }

    public long getTotalItens() {
        return itemRepository.count();
    }

    public long getTrocasRealizadas() {
        // Simulação - você precisará implementar a lógica real quando tiver entidade de trocas
        return 45L;
    }

    public long getNovosRegistrosHoje() {
        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
        
        long novosUsuarios = usuarioRepository.countByDataCriacaoAfter(inicioDoDia);
        long novosItens = itemRepository.countByDataCriacaoAfter(inicioDoDia);
        
        return novosUsuarios + novosItens;
    }

    public Map<String, Object> getEstatisticasCompletas() {
        Map<String, Object> estatisticas = new HashMap<>();
        
        estatisticas.put("totalUsuarios", getTotalUsuarios());
        estatisticas.put("totalItens", getTotalItens());
        estatisticas.put("trocasRealizadas", getTrocasRealizadas());
        estatisticas.put("usuariosAtivos", usuarioRepository.countByAtivoTrue());
        estatisticas.put("itensAtivos", itemRepository.countByDisponivelTrue());
        estatisticas.put("novosUltimos7Dias", getNovosUltimos7Dias());
        
        return estatisticas;
    }

    private long getNovosUltimos7Dias() {
        LocalDateTime seteDiasAtras = LocalDateTime.now().minusDays(7);
        return usuarioRepository.countByDataCriacaoAfter(seteDiasAtras);
    }
}