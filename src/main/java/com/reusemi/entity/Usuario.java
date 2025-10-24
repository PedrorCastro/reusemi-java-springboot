package com.reusemi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "uk_usuario_email")
        })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Column(name = "endereco", length = 255)
    private String endereco;

    @Column(name = "nivel", nullable = false, length = 20)
    private String nivel = "USER";

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtores
    public Usuario() {
        this.nivel = "USER";
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public Usuario(String nome, String email, String senha) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha, String nivel) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel != null ? nivel : "USER";
    }

    // Pré-persistência - executado antes de salvar no banco
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        if (this.nivel == null) {
            this.nivel = "USER";
        }
        if (this.ativo == null) {
            this.ativo = true;
        }
    }

    // Pré-atualização - executado antes de atualizar
    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    // Métodos utilitários
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.nivel);
    }

    public void tornarAdmin() {
        this.nivel = "ADMIN";
    }

    public void tornarUsuario() {
        this.nivel = "USER";
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    // equals e hashCode baseados no ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // toString para debugging
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", nivel='" + nivel + '\'' +
                ", ativo=" + ativo +
                ", dataCriacao=" + dataCriacao +
                '}';
    }

    // Builder pattern para criação facilitada
    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    // Classe Builder interna
    public static class UsuarioBuilder {
        private String nome;
        private String email;
        private String senha;
        private String telefone;
        private String endereco;
        private String nivel;

        public UsuarioBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public UsuarioBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UsuarioBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public UsuarioBuilder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public UsuarioBuilder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public UsuarioBuilder nivel(String nivel) {
            this.nivel = nivel;
            return this;
        }

        public Usuario build() {
            Usuario usuario = new Usuario();
            usuario.setNome(this.nome);
            usuario.setEmail(this.email);
            usuario.setSenha(this.senha);
            usuario.setTelefone(this.telefone);
            usuario.setEndereco(this.endereco);
            usuario.setNivel(this.nivel != null ? this.nivel : "USER");
            return usuario;
        }
    }
}