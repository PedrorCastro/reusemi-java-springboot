package com.reusemi.repo;

import com.reusemi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Itens disponíveis para troca
    List<Item> findByDisponivelTrue();

    // Itens de um usuário específico
    List<Item> findByUsuarioId(Long usuarioId);

    // Itens por categoria
    List<Item> findByCategoriaAndDisponivelTrue(String categoria);

    // Itens por condição
    List<Item> findByCondicaoAndDisponivelTrue(String condicao);

    // ✅ NOVO: Contar itens disponíveis
    long countByDisponivelTrue();

    // ✅ NOVO: Contar itens por usuário
    long countByUsuarioId(Long usuarioId);

    // ✅ NOVO: Contar itens criados após uma data (para AdminService)
    long countByDataCriacaoAfter(LocalDateTime data);

    // ✅ NOVO: Contar itens criados antes de uma data
    long countByDataCriacaoBefore(LocalDateTime data);

    // ✅ NOVO: Contar itens criados entre duas datas
    @Query("SELECT COUNT(i) FROM Item i WHERE i.dataCriacao BETWEEN :dataInicio AND :dataFim")
    long countByDataCriacaoBetween(@Param("dataInicio") LocalDateTime dataInicio,
                                   @Param("dataFim") LocalDateTime dataFim);

    // Itens ordenados por data de criação (mais recentes primeiro)
    List<Item> findAllByOrderByDataCriacaoDesc();

    // Buscar itens por título
    List<Item> findByTituloContainingIgnoreCase(String titulo);

    // Buscar itens por descrição
    List<Item> findByDescricaoContainingIgnoreCase(String descricao);

    // Buscar itens por usuário e disponibilidade
    List<Item> findByUsuarioIdAndDisponivelTrue(Long usuarioId);

    // Estatísticas de itens por categoria
    @Query("SELECT i.categoria, COUNT(i) FROM Item i WHERE i.disponivel = true GROUP BY i.categoria")
    List<Object[]> countItensPorCategoria();

    // Estatísticas de itens por condição
    @Query("SELECT i.condicao, COUNT(i) FROM Item i WHERE i.disponivel = true GROUP BY i.condicao")
    List<Object[]> countItensPorCondicao();
}