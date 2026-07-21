package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Livro;
import com.folhear.desktop.util.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class LivrosPane extends BorderPane {

    private final TableView<Livro> tabela = new TableView<>();
    private final ObservableList<Livro> dados = FXCollections.observableArrayList();
    private final TextField campoBusca = new TextField();

    public LivrosPane() {
        setTop(cabecalho());
        setCenter(tabela());
        carregar();
    }

    private VBox cabecalho() {
        Label titulo = new Label("Catálogo de Livros");
        titulo.getStyleClass().add("screen-title");
        Label sub = new Label("Livros anunciados para venda ou troca");
        sub.getStyleClass().add("screen-subtitle");

        campoBusca.setPromptText("Buscar por título, autor ou categoria...");
        campoBusca.textProperty().addListener((obs, old, val) -> filtrar(val));
        campoBusca.setPrefWidth(320);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNovo = new Button("+ Novo Livro");
        btnNovo.getStyleClass().add("button-primary");
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("button-secondary");
        btnAtualizar.setOnAction(e -> carregar());

        HBox linhaAcoes = new HBox(10, campoBusca, spacer, btnAtualizar, btnNovo);
        linhaAcoes.setAlignment(Pos.CENTER_LEFT);
        linhaAcoes.setPadding(new Insets(14, 0, 14, 0));

        VBox box = new VBox(4, titulo, sub, linhaAcoes);
        return box;
    }

    @SuppressWarnings("unchecked")
    private TableView<Livro> tabela() {
        TableColumn<Livro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setPrefWidth(220);

        TableColumn<Livro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAutor.setPrefWidth(160);

        TableColumn<Livro, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(120);

        TableColumn<Livro, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(90);

        TableColumn<Livro, String> colTipo = new TableColumn<>("Anúncio");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoAnuncio"));
        colTipo.setPrefWidth(90);

        TableColumn<Livro, Float> colPreco = new TableColumn<>("Preço (R$)");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colPreco.setPrefWidth(90);

        TableColumn<Livro, String> colVendedor = new TableColumn<>("Vendedor");
        colVendedor.setCellValueFactory(cell -> {
            Livro l = cell.getValue();
            String nome = (l.getVendedor() != null) ? l.getVendedor().toString() : "-";
            return new javafx.beans.property.SimpleStringProperty(nome);
        });
        colVendedor.setPrefWidth(150);

        TableColumn<Livro, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(160);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(6, btnEditar, btnExcluir);
            {
                btnEditar.getStyleClass().add("button-secondary");
                btnExcluir.getStyleClass().add("button-danger");
                btnEditar.setOnAction(e -> abrirFormulario(getTableView().getItems().get(getIndex())));
                btnExcluir.setOnAction(e -> excluir(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tabela.getColumns().setAll(List.of(colTitulo, colAutor, colCategoria, colEstado, colTipo, colPreco, colVendedor, colAcoes));
        tabela.setItems(dados);
        tabela.setPlaceholder(new Label("Nenhum livro encontrado. Clique em \"+ Novo Livro\" para anunciar."));
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tabela;
    }

    private void carregar() {
        try {
            List<Livro> livros = Session.get().api().get("/livros", new TypeReference<List<Livro>>() {});
            dados.setAll(livros);
        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar livros", ex.getMessage());
        }
    }

    private void filtrar(String termo) {
        if (termo == null || termo.isBlank()) {
            carregar();
            return;
        }
        String t = termo.toLowerCase();
        try {
            List<Livro> livros = Session.get().api().get("/livros", new TypeReference<List<Livro>>() {});
            dados.setAll(livros.stream().filter(l ->
                    (l.getTitulo() != null && l.getTitulo().toLowerCase().contains(t)) ||
                    (l.getAutor() != null && l.getAutor().toLowerCase().contains(t)) ||
                    (l.getCategoria() != null && l.getCategoria().toLowerCase().contains(t))
            ).toList());
        } catch (Exception ex) {
            Dialogs.erro("Erro ao buscar livros", ex.getMessage());
        }
    }

    private void abrirFormulario(Livro livro) {
        LivroFormDialog dialog = new LivroFormDialog(livro);
        dialog.showAndWait().ifPresent(resultado -> carregar());
    }

    private void excluir(Livro livro) {
        if (!Dialogs.confirmar("Excluir livro", "Tem certeza que deseja excluir \"" + livro.getTitulo() + "\"?")) return;
        try {
            Session.get().api().delete("/livros/" + livro.getId());
            carregar();
        } catch (Exception ex) {
            Dialogs.erro("Erro ao excluir", ex.getMessage());
        }
    }
}
