package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(length = 50)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @Builder.Default
    private Boolean lida = false;

    @Column(name = "data_criacao")
    private String dataCriacao;

    @Column(name = "data_leitura")
    private String dataLeitura;

    @Column(name = "troca_id")
    private Long trocaId;

    @Column(name = "transacao_id")
    private Long transacaoId;

    @Column(name = "chat_mensagem_id")
    private Long chatMensagemId;

    @Column(name = "clube_id")
    private Long clubeId;
}
