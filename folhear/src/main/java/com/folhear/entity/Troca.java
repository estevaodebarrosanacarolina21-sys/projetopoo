package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "troca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Troca {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proponente_id")
    private Usuario proponente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;

    @Column(name = "data_agendamento")
    private String dataAgendamento;

    @Column(name = "qr_code_checkin")
    private String qrCodeCheckin;

    @Builder.Default
    private Float complemento = 0f;

    @Column(length = 20)
    private String status;

    @Column(name = "criado_em")
    private String criadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_encontro_id")
    private PontoEncontro pontoEncontro;

    @OneToMany(mappedBy = "troca", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TrocaItem> itens = new ArrayList<>();
}
