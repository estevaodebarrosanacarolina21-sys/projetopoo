package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(length = 255)
    private String autor;

    @Column(length = 100)
    private String isbn;

    @Column(length = 150)
    private String categoria;

    @Column(length = 20)
    private String estado;

    @Column(name = "tipo_anuncio", length = 20)
    private String tipoAnuncio;

    private Float preco;

    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "expiracao_em")
    private String expiracaoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @Column(name = "criado_em")
    private String criadoEm;

    @ElementCollection
    @CollectionTable(name = "livro_foto", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "url")
    @Builder.Default
    private List<String> fotos = new ArrayList<>();
}
