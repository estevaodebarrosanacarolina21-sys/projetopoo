package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Livro;
import com.folhear.desktop.model.Troca;
import com.folhear.desktop.model.TrocaItem;
import com.folhear.desktop.model.Usuario;
import com.folhear.desktop.util.Dialogs;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Janela para propor uma nova troca de livros com outro usuário.
 */
public class TrocaFormDialog extends Dialog<Troca> {

    private final ComboBox<Usuario> campoReceptor = new ComboBox<>();
    private final ListView<Livro> listaOferecidos = new ListView<>();
    private final ListView<Livro> listaSolicitados = new ListView<>();

    public TrocaFormDialog() {
        setTitle("Nova proposta de troca");
        setHeaderText(null);
        getDialogPane().getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        ((Button) getDialogPane().lookupButton(ButtonType.OK)).setText("Propor troca");
        ((Button) getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancelar");

        Usuario logado = Session.get().getUsuarioLogado();

        try {
            List<Usuario> usuarios = Session.get().api().get("/usuarios", new TypeReference<List<Usuario>>() {});
            List<Livro> livros = Session.get().api().get("/livros", new TypeReference<List<Livro>>() {});

            campoReceptor.setItems(FXCollections.observableArrayList(
                    usuarios.stream().filter(u -> logado == null || !u.getId().equals(logado.getId())).toList()));

            listaOferecidos.setItems(FXCollections.observableArrayList(
                    livros.stream().filter(l -> logado != null && l.getVendedor() != null
                            && logado.getId().equals(l.getVendedor().getId())).toList()));
            listaOferecidos.setCellFactory(cb -> cellDeLivro());
            listaOferecidos.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

            listaSolicitados.setItems(FXCollections.observableArrayList(
                    livros.stream().filter(l -> logado == null || l.getVendedor() == null
                            || !logado.getId().equals(l.getVendedor().getId())).toList()));
            listaSolicitados.setCellFactory(cb -> cellDeLivro());
            listaSolicitados.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        } catch (Exception ex) {
            Dialogs.erro("Erro ao carregar dados", ex.getMessage());
        }

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(10);
        grid.setPadding(new Insets(16));

        Label lReceptor = new Label("Trocar com:");
        lReceptor.getStyleClass().add("form-label");
        campoReceptor.setMaxWidth(Double.MAX_VALUE);
        campoReceptor.setPromptText("Selecione o outro leitor");

        VBox colOfer = new VBox(4, labelForm("Meus livros oferecidos"), listaOferecidos);
        VBox colSol = new VBox(4, labelForm("Livros que eu quero"), listaSolicitados);
        listaOferecidos.setPrefSize(260, 200);
        listaSolicitados.setPrefSize(260, 200);

        HBox colunas = new HBox(20, colOfer, colSol);

        VBox conteudo = new VBox(12, lReceptor, campoReceptor, colunas);
        getDialogPane().setContent(conteudo);

        setResultConverter(botao -> {
            if (botao != ButtonType.OK) return null;
            return salvar();
        });
    }

    private Label labelForm(String texto) {
        Label l = new Label(texto);
        l.getStyleClass().add("form-label");
        return l;
    }

    private javafx.scene.control.ListCell<Livro> cellDeLivro() {
        return new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Livro item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitulo() + " — " + item.getAutor());
            }
        };
    }

    private Troca salvar() {
        Usuario logado = Session.get().getUsuarioLogado();
        Usuario receptor = campoReceptor.getValue();

        if (logado == null || receptor == null) {
            Dialogs.erro("Dados incompletos", "Selecione com quem deseja propor a troca.");
            return null;
        }

        List<Livro> oferecidos = listaOferecidos.getSelectionModel().getSelectedItems();
        List<Livro> solicitados = listaSolicitados.getSelectionModel().getSelectedItems();

        if (oferecidos.isEmpty() && solicitados.isEmpty()) {
            Dialogs.erro("Selecione livros", "Escolha ao menos um livro oferecido ou solicitado.");
            return null;
        }

        Troca troca = new Troca();
        troca.setProponente(Usuario.ref(logado.getId()));
        troca.setReceptor(Usuario.ref(receptor.getId()));
        troca.setStatus("PROPOSTA");

        List<TrocaItem> itens = new ArrayList<>();
        for (Livro l : oferecidos) itens.add(new TrocaItem(livroRef(l), "O"));
        for (Livro l : solicitados) itens.add(new TrocaItem(livroRef(l), "S"));
        troca.setItens(itens);

        try {
            return Session.get().api().post("/trocas", troca, Troca.class);
        } catch (Exception ex) {
            Dialogs.erro("Erro ao propor troca", ex.getMessage());
            return null;
        }
    }

    private Livro livroRef(Livro l) {
        Livro ref = new Livro();
        ref.setId(l.getId());
        return ref;
    }
}
