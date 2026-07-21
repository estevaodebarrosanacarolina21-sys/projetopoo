package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_mensagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remetente_id")
    private Usuario remetente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "data_envio")
    private String dataEnvio;

    @Builder.Default
    private Boolean lida = false;

    @Column(name = "data_leitura")
    private String dataLeitura;

    @Column(name = "troca_id")
    private Long trocaId;

    @Column(name = "transacao_id")
    private Long transacaoId;
}
