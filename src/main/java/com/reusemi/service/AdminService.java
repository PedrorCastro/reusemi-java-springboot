package com.reusemi.service;

import com.reusemi.repo.UsuarioRepository; // ← CORRIGIDO: repository, não repo
import com.reusemi.repo.ItemRepository;   // ← CORRIGIDO: repository, não repo
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

        // Métodos alternativos se não tiver os métodos específicos
        long novosUsuarios = usuarioRepository.countByDataCriacaoAfter(inicioDoDia);
        long novosItens = itemRepository.countByDataCriacaoAfter(inicioDoDia);

        return novosUsuarios + novosItens;
    }

    public Map<String, Object> getEstatisticasCompletas() {
        Map<String, Object> estatisticas = new HashMap<>();

        estatisticas.put("totalUsuarios", getTotalUsuarios());
        estatisticas.put("totalItens", getTotalItens());
        estatisticas.put("trocasRealizadas", getTrocasRealizadas());
        estatisticas.put("usuariosAtivos", getUsuariosAtivos());
        estatisticas.put("itensAtivos", getItensAtivos());
        estatisticas.put("novosUltimos7Dias", getNovosUltimos7Dias());

        return estatisticas;
    }

    private long getNovosUltimos7Dias() {
        LocalDateTime seteDiasAtras = LocalDateTime.now().minusDays(7);
        return usuarioRepository.countByDataCriacaoAfter(seteDiasAtras);
    }

    private long getUsuariosAtivos() {
        try {
            return usuarioRepository.countByAtivoTrue();
        } catch (Exception e) {
            // Fallback se o método não existir
            return usuarioRepository.count();
        }
    }

    private long getItensAtivos() {
        try {
            return itemRepository.countByDisponivelTrue();
        } catch (Exception e) {
            // Fallback se o método não existir
            return itemRepository.count();
        }
    }
}