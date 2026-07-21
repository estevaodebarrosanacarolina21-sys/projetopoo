package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senhaHash;
    private String cpf;
    private String cidade;
    private String fotoPerfil;
    private String bio;
    private String tipo = "LEITOR"; // LEITOR, AUTOR, AMBOS
    private Float notaMedia;
    private Integer totalTransacoes;
    private Boolean ativo = true;
    private String criadoEm;
    private List<String> interessesLiterarios = new ArrayList<>();

    public Usuario() {}

    public static Usuario ref(String id) {
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Float getNotaMedia() { return notaMedia; }
    public void setNotaMedia(Float notaMedia) { this.notaMedia = notaMedia; }

    public Integer getTotalTransacoes() { return totalTransacoes; }
    public void setTotalTransacoes(Integer totalTransacoes) { this.totalTransacoes = totalTransacoes; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getCriadoEm() { return criadoEm; }
    public void setCriadoEm(String criadoEm) { this.criadoEm = criadoEm; }

    public List<String> getInteressesLiterarios() { return interessesLiterarios; }
    public void setInteressesLiterarios(List<String> interessesLiterarios) { this.interessesLiterarios = interessesLiterarios; }

    @Override
    public String toString() {
        return nome != null ? nome : (email != null ? email : "Usuário");
    }
}
