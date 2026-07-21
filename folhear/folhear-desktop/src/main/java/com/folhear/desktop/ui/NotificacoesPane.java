package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Notificacao;
import com.folhear.desktop.model.Usuario;
import com.folhear.desktop.util.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class NotificacoesPane extends BorderPane {

    private final TableView<Notificacao> tabela = new TableView<>();
    private final ObservableList<Notificacao> dados = FXCollections.observableArrayList();

    public NotificacoesPane() {
        setTop(cabecalho());
        setCenter(tabela());
        carregar();
    }

    private VBox cabecalho() {
        Label titulo = new Label("Notificações");
        titulo.getStyleClass().add("screen-title");
        Label sub = new Label("Avisos sobre trocas, chat, clubes e vendas");
        sub.getStyleClass().add("screen-subtitle");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("button-secondary");
        btnAtualizar.setOnAction(e -> carregar());

        HBox linhaAcoes = new HBox(10, spacer, btnAtualizar);
        linhaAcoes.setAlignment(Pos.CENTER_LEFT);
        linhaAcoes.setPadding(new Insets(14, 0, 14, 0));

        return new VBox(4, titulo, sub, linhaAcoes);
    }

    private TableView<Notificacao> tabela() {
        TableColumn<Notificacao, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo()));
        colTipo.setPrefWidth(100);

        TableColumn<Notificacao, String> colMensagem = new TableColumn<>("Mensagem");
        colMensagem.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMensagem()));
        colMensagem.setPrefWidth(380);

        TableColumn<Notificacao, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDataCriacao()));
        colData.setPrefWidth(160);

        TableColumn<Notificacao, Notificacao> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue()));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Notificacao n, boolean empty) {
                super.updateItem(n, empty);
                if (empty || n == null) { setGraphic(null); return; }
                boolean lida = Boolean.TRUE.equals(n.getLida());
                Label pill = new Label(lida ? "Lida" : "Não lida");
                pill.getStyleClass().addAll("pill", lida ? "pill-lida" : "pill-nao-lida");
                setGraphic(pill);
            }
        });
        colStatus.setPrefWidth(100);

        TableColumn<Notificacao, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(140);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnMarcar = new Button("Marcar como lida");
            {
                btnMarcar.getStyleClass().add("button-secondary");
                btnMarcar.setOnAction(e -> marcarLida(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setGraphic(null); return; }
                Notificacao n = getTableView().getItems().get(getIndex());
                setGraphic(Boolean.TRUE.equals(n.getLida()) ? null : btnMarcar);
            }
        });

        tabela.getColumns().setAll(List.of(colTipo, colMensagem, colData, colStatus, colAcoes));
        tabela.setItems(dados);
        tabela.setPlaceholder(new Label("Nenhuma notificação por aqui."));
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tabela;
    }

    private void carregar() {
        Usuario logado = Session.get().getUsuarioLogado();
        if (logado == null) return;
        try {
            List<Notificacao> todas = Session.get().api().get("/notificacoes", new TypeReference<List<Notificacao>>() {});
            dados.setAll(todas.stream()
                    .filter(n -> n.getUsuario() != null && logado.getId().equals(n.getUsuario().getId()))
                    .toList());
        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar notificações", ex.getMessage());
        }
    }

    private void marcarLida(Notificacao n) {
        try {
            Session.get().api().patch("/notificacoes/" + n.getId() + "/lida", Notificacao.class);
            carregar();
        } catch (Exception ex) {
            Dialogs.erro("Erro ao marcar notificação", ex.getMessage());
        }
    }
}
