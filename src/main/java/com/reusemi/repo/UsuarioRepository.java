package com.reusemi.repo;

import com.reusemi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Métodos para estatísticas
    long countByAtivoTrue();
    
    // Método para contar usuários criados após uma data
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.dataCriacao >= :data")
    long countByDataCriacaoAfter(LocalDateTime data);
}