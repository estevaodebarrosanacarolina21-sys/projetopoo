package com.folhear.entity;

import com.folhear.entity.enums.TipoPonto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ponto_encontro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PontoEncontro {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 300)
    private String endereco;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    @Column(name = "horario_funcionamento", length = 255)
    private String horarioFuncionamento;

    @Column(name = "nota_media")
    @Builder.Default
    private Float notaMedia = 0f;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TipoPonto tipo = TipoPonto.OUTRO;
}
