package com.reusemi.repo;

import com.reusemi.model.Item;
import com.reusemi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByDisponivelTrue();
    List<Item> findByUsuario(Usuario usuario);
    List<Item> findByCategoria(String categoria);
    List<Item> findByUsuarioId(Long usuarioId);
    
    // Métodos para estatísticas
    long countByDisponivelTrue();
    
    // Método para contar itens criados após uma data
    @Query("SELECT COUNT(i) FROM Item i WHERE i.dataCriacao >= :data")
    long countByDataCriacaoAfter(LocalDateTime data);

}
