package com.reusemi.repo;

import com.reusemi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuário por email (case insensitive)
    Optional<Usuario> findByEmail(String email);

    // Buscar usuário por email ignorando maiúsculas/minúsculas
    Optional<Usuario> findByEmailIgnoreCase(String email);

    // Verificar se email existe
    boolean existsByEmail(String email);

    // Verificar se email existe (case insensitive)
    boolean existsByEmailIgnoreCase(String email);

    // Buscar usuários por nome
    List<Usuario> findByNomeContaining(String nome);

    // Buscar usuários por nome (case insensitive)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Buscar usuários por nível (role)
    List<Usuario> findByNivel(String nivel);

    // Buscar usuário por email e senha (para login sem criptografia - não recomendado)
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
    Optional<Usuario> findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);

    // Buscar usuário por email com senha criptografada
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findUsuarioComSenha(@Param("email") String email);

    // Contar usuários por nível
    long countByNivel(String nivel);

    // Contar usuários ativos
    long countByAtivoTrue();

    // Contar usuários inativos
    long countByAtivoFalse();

    // ✅ NOVO MÉTODO: Contar usuários criados após uma data específica
    long countByDataCriacaoAfter(LocalDateTime data);

    // ✅ NOVO MÉTODO: Contar usuários criados antes de uma data específica
    long countByDataCriacaoBefore(LocalDateTime data);

    // ✅ NOVO MÉTODO: Contar usuários criados entre duas datas
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.dataCriacao BETWEEN :dataInicio AND :dataFim")
    long countByDataCriacaoBetween(@Param("dataInicio") LocalDateTime dataInicio,
                                   @Param("dataFim") LocalDateTime dataFim);

    // Buscar usuários ativos com paginação
    // Page<Usuario> findByAtivoTrue(Pageable pageable);

    // Deletar usuário por email
    void deleteByEmail(String email);

    // Verificar se existe usuário com ID
    boolean existsById(Long id);

    // Buscar usuários ordenados por nome
    List<Usuario> findAllByOrderByNomeAsc();

    // Buscar usuários ordenados por data de criação (mais recentes primeiro)
    List<Usuario> findAllByOrderByDataCriacaoDesc();

    // Buscar por parte do email
    List<Usuario> findByEmailContaining(String email);

    // Buscar usuários com telefone
    List<Usuario> findByTelefoneIsNotNull();

    // Buscar usuários sem telefone
    List<Usuario> findByTelefoneIsNull();

    // Buscar por nome ou email
    @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %:termo% OR u.email LIKE %:termo%")
    List<Usuario> findByNomeOrEmail(@Param("termo") String termo);

    // Buscar usuários criados nos últimos N dias
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.dataCriacao >= :dataLimite")
    long countUsuariosRecentes(@Param("dataLimite") LocalDateTime dataLimite);

    // Estatísticas de usuários por nível
    @Query("SELECT u.nivel, COUNT(u) FROM Usuario u GROUP BY u.nivel")
    List<Object[]> countUsuariosPorNivel();
}