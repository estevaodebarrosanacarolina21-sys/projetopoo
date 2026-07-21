package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Troca;
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

public class TrocasPane extends BorderPane {

    private final TableView<Troca> tabela = new TableView<>();
    private final ObservableList<Troca> dados = FXCollections.observableArrayList();

    public TrocasPane() {
        setTop(cabecalho());
        setCenter(tabela());
        carregar();
    }

    private VBox cabecalho() {
        Label titulo = new Label("Trocas");
        titulo.getStyleClass().add("screen-title");
        Label sub = new Label("Propostas de troca de livros entre leitores");
        sub.getStyleClass().add("screen-subtitle");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNova = new Button("+ Nova Troca");
        btnNova.getStyleClass().add("button-primary");
        btnNova.setOnAction(e -> abrirFormulario());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("button-secondary");
        btnAtualizar.setOnAction(e -> carregar());

        HBox linhaAcoes = new HBox(10, spacer, btnAtualizar, btnNova);
        linhaAcoes.setAlignment(Pos.CENTER_LEFT);
        linhaAcoes.setPadding(new Insets(14, 0, 14, 0));

        return new VBox(4, titulo, sub, linhaAcoes);
    }

    private TableView<Troca> tabela() {
        TableColumn<Troca, String> colProponente = new TableColumn<>("Proponente");
        colProponente.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getProponente() != null ? c.getValue().getProponente().toString() : "-"));
        colProponente.setPrefWidth(160);

        TableColumn<Troca, String> colReceptor = new TableColumn<>("Receptor");
        colReceptor.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getReceptor() != null ? c.getValue().getReceptor().toString() : "-"));
        colReceptor.setPrefWidth(160);

        TableColumn<Troca, Number> colItens = new TableColumn<>("Itens");
        colItens.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
                c.getValue().getItens() == null ? 0 : c.getValue().getItens().size()));
        colItens.setPrefWidth(60);

        TableColumn<Troca, Troca> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue()));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Troca t, boolean empty) {
                super.updateItem(t, empty);
                if (empty || t == null) { setGraphic(null); return; }
                Label pill = new Label(t.getStatus());
                pill.getStyleClass().addAll("pill", "pill-" + t.getStatus().toLowerCase().replace("_", "-"));
                setGraphic(pill);
            }
        });
        colStatus.setPrefWidth(130);

        TableColumn<Troca, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(260);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnAceitar = new Button("Aceitar");
            private final Button btnRecusar = new Button("Recusar");
            private final Button btnConcluir = new Button("Concluir");
            private final HBox box = new HBox(6, btnAceitar, btnRecusar, btnConcluir);
            {
                btnAceitar.getStyleClass().add("button-secondary");
                btnRecusar.getStyleClass().add("button-danger");
                btnConcluir.getStyleClass().add("button-primary");
                btnAceitar.setOnAction(e -> alterarStatus(getTableView().getItems().get(getIndex()), "ACEITA"));
                btnRecusar.setOnAction(e -> alterarStatus(getTableView().getItems().get(getIndex()), "RECUSADA"));
                btnConcluir.setOnAction(e -> alterarStatus(getTableView().getItems().get(getIndex()), "CONCLUIDA"));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tabela.getColumns().setAll(List.of(colProponente, colReceptor, colItens, colStatus, colAcoes));
        tabela.setItems(dados);
        tabela.setPlaceholder(new Label("Nenhuma troca ainda. Clique em \"+ Nova Troca\" para propor uma."));
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tabela;
    }

    private void carregar() {
        try {
            List<Troca> trocas = Session.get().api().get("/trocas", new TypeReference<List<Troca>>() {});
            dados.setAll(trocas);
        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar trocas", ex.getMessage());
        }
    }

    private void abrirFormulario() {
        TrocaFormDialog dialog = new TrocaFormDialog();
        dialog.showAndWait().ifPresent(r -> carregar());
    }

    private void alterarStatus(Troca troca, String novoStatus) {
        troca.setStatus(novoStatus);
        try {
            Session.get().api().put("/trocas/" + troca.getId(), troca, Troca.class);
            carregar();
        } catch (Exception ex) {
            Dialogs.erro("Erro ao atualizar troca", ex.getMessage());
        }
    }
}
