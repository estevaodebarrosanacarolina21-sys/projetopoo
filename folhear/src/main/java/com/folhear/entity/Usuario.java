package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", length = 255)
    private String senhaHash;

    @Column(length = 20)
    private String cpf;

    @Column(length = 100)
    private String cidade;

    @Column(name = "foto_perfil", length = 255)
    private String fotoPerfil;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 20)
    private String tipo;

    @Column(name = "nota_media")
    private Float notaMedia;

    @Column(name = "total_transacoes")
    private Integer totalTransacoes;

    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "criado_em")
    private String criadoEm;

    @ElementCollection
    @CollectionTable(name = "usuario_interesse", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "interesse")
    @Builder.Default
    private List<String> interessesLiterarios = new ArrayList<>();
}
