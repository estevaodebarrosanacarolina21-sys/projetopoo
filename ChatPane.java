package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.ChatMensagem;
import com.folhear.desktop.model.Usuario;
import com.folhear.desktop.util.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

public class ChatPane extends BorderPane {

    private final ListView<Usuario> listaConversas = new ListView<>();
    private final VBox areaMensagens = new VBox(8);
    private final ScrollPane scrollMensagens = new ScrollPane();
    private final TextField campoMensagem = new TextField();
    private final Label tituloConversa = new Label("Selecione uma conversa");

    private List<ChatMensagem> todasMensagens = new ArrayList<>();
    private Usuario conversaAtual;

    public ChatPane() {
        setLeft(painelConversas());
        setCenter(painelMensagens());
        carregar();
    }

    private VBox painelConversas() {
        Label titulo = new Label("Conversas");
        titulo.getStyleClass().add("screen-title");

        Button btnNova = new Button("+ Nova conversa");
        btnNova.getStyleClass().add("button-secondary");
        btnNova.setMaxWidth(Double.MAX_VALUE);
        btnNova.setOnAction(e -> novaConversa());

        listaConversas.setPrefWidth(230);
        listaConversas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Usuario u, boolean empty) {
                super.updateItem(u, empty);
                setText(empty || u == null ? null : u.toString());
            }
        });
        listaConversas.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
            if (val != null) abrirConversa(val);
        });
        VBox.setVgrow(listaConversas, Priority.ALWAYS);

        VBox box = new VBox(10, titulo, btnNova, listaConversas);
        box.setPadding(new Insets(0, 16, 0, 0));
        box.setPrefWidth(250);
        return box;
    }

    private VBox painelMensagens() {
        tituloConversa.getStyleClass().add("screen-title");

        scrollMensagens.setContent(areaMensagens);
        scrollMensagens.setFitToWidth(true);
        areaMensagens.setPadding(new Insets(12));
        VBox.setVgrow(scrollMensagens, Priority.ALWAYS);

        campoMensagem.setPromptText("Digite sua mensagem...");
        campoMensagem.setOnAction(e -> enviar());
        Button btnEnviar = new Button("Enviar");
        btnEnviar.getStyleClass().add("button-primary");
        btnEnviar.setOnAction(e -> enviar());

        HBox linhaEnvio = new HBox(8, campoMensagem, btnEnviar);
        HBox.setHgrow(campoMensagem, Priority.ALWAYS);
        linhaEnvio.setPadding(new Insets(10, 0, 0, 0));

        VBox card = new VBox(10, tituloConversa, scrollMensagens, linhaEnvio);
        card.getStyleClass().add("card");
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    private void carregar() {
        Usuario logado = Session.get().getUsuarioLogado();
        if (logado == null) return;
        try {
            todasMensagens = Session.get().api().get("/mensagens", new TypeReference<List<ChatMensagem>>() {});

            Map<String, Usuario> correspondentes = new LinkedHashMap<>();
            for (ChatMensagem m : todasMensagens) {
                Usuario outro = null;
                if (m.getRemetente() != null && logado.getId().equals(m.getRemetente().getId())) outro = m.getDestinatario();
                else if (m.getDestinatario() != null && logado.getId().equals(m.getDestinatario().getId())) outro = m.getRemetente();
                if (outro != null && outro.getId() != null) correspondentes.putIfAbsent(outro.getId(), outro);
            }
            listaConversas.setItems(FXCollections.observableArrayList(correspondentes.values()));
        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar conversas", ex.getMessage());
        }
    }

    private void novaConversa() {
        try {
            List<Usuario> usuarios = Session.get().api().get("/usuarios", new TypeReference<List<Usuario>>() {});
            Usuario logado = Session.get().getUsuarioLogado();
            ChoiceDialog<Usuario> dialog = new ChoiceDialog<>(null,
                    usuarios.stream().filter(u -> logado == null || !u.getId().equals(logado.getId())).collect(Collectors.toList()));
            dialog.setTitle("Nova conversa");
            dialog.setHeaderText(null);
            dialog.setContentText("Conversar com:");
            dialog.showAndWait().ifPresent(this::abrirConversa);
        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar usuários", ex.getMessage());
        }
    }

    private void abrirConversa(Usuario outro) {
        conversaAtual = outro;
        tituloConversa.setText("Conversa com " + outro);
        if (!listaConversas.getItems().contains(outro)) {
            listaConversas.getItems().add(outro);
        }
        listaConversas.getSelectionModel().select(outro);
        renderMensagens();
    }

    private void renderMensagens() {
        Usuario logado = Session.get().getUsuarioLogado();
        areaMensagens.getChildren().clear();
        if (conversaAtual == null || logado == null) return;

        List<ChatMensagem> conversa = todasMensagens.stream()
                .filter(m -> pertenceAConversa(m, logado, conversaAtual))
                .sorted(Comparator.comparing(m -> Optional.ofNullable(m.getDataEnvio()).orElse("")))
                .toList();

        for (ChatMensagem m : conversa) {
            boolean minha = m.getRemetente() != null && logado.getId().equals(m.getRemetente().getId());
            Label texto = new Label(m.getConteudo());
            texto.setWrapText(true);
            texto.setMaxWidth(340);
            HBox bolha = new HBox(texto);
            bolha.getStyleClass().add(minha ? "chat-bubble-mine" : "chat-bubble-other");
            HBox linha = new HBox(bolha);
            linha.setAlignment(minha ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
            areaMensagens.getChildren().add(linha);
        }
    }

    private boolean pertenceAConversa(ChatMensagem m, Usuario logado, Usuario outro) {
        String remId = m.getRemetente() != null ? m.getRemetente().getId() : null;
        String destId = m.getDestinatario() != null ? m.getDestinatario().getId() : null;
        return (logado.getId().equals(remId) && outro.getId().equals(destId))
                || (logado.getId().equals(destId) && outro.getId().equals(remId));
    }

    private void enviar() {
        String texto = campoMensagem.getText().trim();
        Usuario logado = Session.get().getUsuarioLogado();
        if (texto.isEmpty() || conversaAtual == null || logado == null) return;

        ChatMensagem msg = new ChatMensagem();
        msg.setRemetente(Usuario.ref(logado.getId()));
        msg.setDestinatario(Usuario.ref(conversaAtual.getId()));
        msg.setConteudo(texto);

        try {
            Session.get().api().post("/mensagens", msg, ChatMensagem.class);
            campoMensagem.clear();
            carregar();
            abrirConversa(conversaAtual);
        } catch (Exception ex) {
            Dialogs.erro("Erro ao enviar mensagem", ex.getMessage());
        }
    }
}
