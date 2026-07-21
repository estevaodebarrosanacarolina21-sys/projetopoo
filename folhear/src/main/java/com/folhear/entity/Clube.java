package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clube")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clube {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "livro_do_mes", length = 255)
    private String livroDoMes;

    @Column(name = "anunciado_em")
    private String anunciadoEm;

    @ElementCollection
    @CollectionTable(name = "clube_evento", joinColumns = @JoinColumn(name = "clube_id"))
    @Column(name = "evento")
    @Builder.Default
    private List<String> eventos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "clube_membro",
            joinColumns = @JoinColumn(name = "clube_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @Builder.Default
    private List<Usuario> membros = new ArrayList<>();
}
