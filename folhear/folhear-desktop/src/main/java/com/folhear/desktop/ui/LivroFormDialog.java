package com.folhear.desktop.ui;

import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Livro;
import com.folhear.desktop.model.Usuario;
import com.folhear.desktop.util.Dialogs;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

/**
 * Janela (Dialog) para criar ou editar um livro anunciado.
 */
public class LivroFormDialog extends Dialog<Livro> {

    private final TextField campoTitulo = new TextField();
    private final TextField campoAutor = new TextField();
    private final TextField campoIsbn = new TextField();
    private final TextField campoCategoria = new TextField();
    private final ComboBox<String> campoEstado = new ComboBox<>();
    private final ComboBox<String> campoTipoAnuncio = new ComboBox<>();
    private final TextField campoPreco = new TextField();

    private final Livro livroExistente;

    public LivroFormDialog(Livro livroParaEditar) {
        this.livroExistente = livroParaEditar;
        setTitle(livroParaEditar == null ? "Anunciar novo livro" : "Editar livro");
        setHeaderText(null);

        getDialogPane().getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        ((Button) getDialogPane().lookupButton(ButtonType.OK)).setText("Salvar");
        ((Button) getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancelar");

        campoEstado.getItems().addAll(Livro.ESTADOS);
        campoTipoAnuncio.getItems().addAll(Livro.TIPOS_ANUNCIO);

        if (livroParaEditar != null) {
            campoTitulo.setText(livroParaEditar.getTitulo());
            campoAutor.setText(livroParaEditar.getAutor());
            campoIsbn.setText(livroParaEditar.getIsbn());
            campoCategoria.setText(livroParaEditar.getCategoria());
            campoEstado.setValue(livroParaEditar.getEstado());
            campoTipoAnuncio.setValue(livroParaEditar.getTipoAnuncio());
            campoPreco.setText(livroParaEditar.getPreco() != null ? String.valueOf(livroParaEditar.getPreco()) : "");
        } else {
            campoEstado.setValue("BOM");
            campoTipoAnuncio.setValue("TROCA");
        }

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(16));

        addRow(grid, 0, "Título*", campoTitulo);
        addRow(grid, 1, "Autor*", campoAutor);
        addRow(grid, 2, "ISBN", campoIsbn);
        addRow(grid, 3, "Categoria", campoCategoria);
        addRow(grid, 4, "Estado de conservação", campoEstado);
        addRow(grid, 5, "Tipo de anúncio", campoTipoAnuncio);
        addRow(grid, 6, "Preço (R$, opcional)", campoPreco);

        campoTitulo.setPrefWidth(280);
        getDialogPane().setContent(grid);

        setResultConverter(botao -> {
            if (botao != ButtonType.OK) return null;
            return salvar();
        });
    }

    private void addRow(GridPane grid, int row, String label, Control campo) {
        Label l = new Label(label);
        l.getStyleClass().add("form-label");
        grid.add(l, 0, row);
        grid.add(campo, 1, row);
        campo.setMaxWidth(Double.MAX_VALUE);
    }

    private Livro salvar() {
        if (campoTitulo.getText().isBlank() || campoAutor.getText().isBlank()) {
            Dialogs.erro("Campos obrigatórios", "Preencha ao menos título e autor.");
            return null;
        }

        Livro livro = livroExistente != null ? livroExistente : new Livro();
        livro.setTitulo(campoTitulo.getText().trim());
        livro.setAutor(campoAutor.getText().trim());
        livro.setIsbn(blankToNull(campoIsbn.getText()));
        livro.setCategoria(blankToNull(campoCategoria.getText()));
        livro.setEstado(campoEstado.getValue());
        livro.setTipoAnuncio(campoTipoAnuncio.getValue());

        String precoTexto = campoPreco.getText().trim();
        if (!precoTexto.isEmpty()) {
            try {
                livro.setPreco(Float.parseFloat(precoTexto.replace(",", ".")));
            } catch (NumberFormatException e) {
                Dialogs.erro("Preço inválido", "Digite um número válido para o preço (ex.: 25.90).");
                return null;
            }
        }

        Usuario logado = Session.get().getUsuarioLogado();
        if (livro.getVendedor() == null && logado != null) {
            livro.setVendedor(Usuario.ref(logado.getId()));
        }

        try {
            Livro resultado;
            if (livroExistente == null) {
                resultado = Session.get().api().post("/livros", livro, Livro.class);
            } else {
                resultado = Session.get().api().put("/livros/" + livro.getId(), livro, Livro.class);
            }
            return resultado;
        } catch (Exception ex) {
            Dialogs.erro("Erro ao salvar livro", ex.getMessage());
            return null;
        }
    }

    private static String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
