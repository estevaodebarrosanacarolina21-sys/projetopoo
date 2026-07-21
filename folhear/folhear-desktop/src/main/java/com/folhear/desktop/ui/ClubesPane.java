package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Clube;
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
import java.util.Optional;

public class ClubesPane extends BorderPane {

    private final TableView<Clube> tabela = new TableView<>();
    private final ObservableList<Clube> dados = FXCollections.observableArrayList();

    public ClubesPane() {
        setTop(cabecalho());
        setCenter(tabela());
        carregar();
    }

    private VBox cabecalho() {
        Label titulo = new Label("Clubes de Leitura");
        titulo.getStyleClass().add("screen-title");
        Label sub = new Label("Participe de clubes, veja o livro do mês e eventos");
        sub.getStyleClass().add("screen-subtitle");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Clube");
        btnNovo.getStyleClass().add("button-primary");
        btnNovo.setOnAction(e -> criarClube());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("button-secondary");
        btnAtualizar.setOnAction(e -> carregar());

        HBox linhaAcoes = new HBox(10, spacer, btnAtualizar, btnNovo);
        linhaAcoes.setAlignment(Pos.CENTER_LEFT);
        linhaAcoes.setPadding(new Insets(14, 0, 14, 0));

        return new VBox(4, titulo, sub, linhaAcoes);
    }

    private TableView<Clube> tabela() {
        TableColumn<Clube, String> colLivro = new TableColumn<>("Livro do mês");
        colLivro.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getLivroDoMes() == null ? "—" : c.getValue().getLivroDoMes()));
        colLivro.setPrefWidth(220);

        TableColumn<Clube, Number> colMembros = new TableColumn<>("Membros");
        colMembros.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
                c.getValue().getMembros() == null ? 0 : c.getValue().getMembros().size()));
        colMembros.setPrefWidth(90);

        TableColumn<Clube, Number> colEventos = new TableColumn<>("Eventos");
        colEventos.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
                c.getValue().getEventos() == null ? 0 : c.getValue().getEventos().size()));
        colEventos.setPrefWidth(80);

        TableColumn<Clube, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(220);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEntrar = new Button("Entrar no clube");
            private final Button btnEvento = new Button("+ Evento");
            private final HBox box = new HBox(6, btnEntrar, btnEvento);
            {
                btnEntrar.getStyleClass().add("button-secondary");
                btnEvento.getStyleClass().add("button-secondary");
                btnEntrar.setOnAction(e -> entrar(getTableView().getItems().get(getIndex())));
                btnEvento.setOnAction(e -> adicionarEvento(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tabela.getColumns().setAll(List.of(colLivro, colMembros, colEventos, colAcoes));
        tabela.setItems(dados);
        tabela.setPlaceholder(new Label("Nenhum clube ainda. Clique em \"+ Novo Clube\" para criar um."));
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tabela;
    }

    private void carregar() {
        try {
            List<Clube> clubes = Session.get().api().get("/clubes", new TypeReference<List<Clube>>() {});
            dados.setAll(clubes);
        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar clubes", ex.getMessage());
        }
    }

    private void criarClube() {
        Optional<String> livroDoMes = Dialogs.prompt("Novo clube", "Livro do mês (opcional):", "");
        if (livroDoMes.isEmpty()) return;

        Clube clube = new Clube();
        clube.setLivroDoMes(livroDoMes.get().isBlank() ? null : livroDoMes.get());
        try {
            Session.get().api().post("/clubes", clube, Clube.class);
            carregar();
        } catch (Exception ex) {
            Dialogs.erro("Erro ao criar clube", ex.getMessage());
        }
    }

    private void entrar(Clube clube) {
        Usuario logado = Session.get().getUsuarioLogado();
        if (logado == null) return;
        try {
            Session.get().api().post("/clubes/" + clube.getId() + "/membros/" + logado.getId(), null, Clube.class);
            Dialogs.info("Pronto!", "Você agora faz parte deste clube.");
            carregar();
        } catch (Exception ex) {
            Dialogs.erro("Erro ao entrar no clube", ex.getMessage());
        }
    }

    private void adicionarEvento(Clube clube) {
        Optional<String> evento = Dialogs.prompt("Novo evento", "Descrição do evento:", "");
        if (evento.isEmpty() || evento.get().isBlank()) return;
        clube.getEventos().add(evento.get().trim());
        try {
            Session.get().api().put("/clubes/" + clube.getId(), clube, Clube.class);
            carregar();
        } catch (Exception ex) {
            Dialogs.erro("Erro ao adicionar evento", ex.getMessage());
        }
    }
}
