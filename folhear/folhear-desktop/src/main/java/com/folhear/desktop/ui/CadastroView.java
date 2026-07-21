package com.folhear.desktop.ui;

import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Usuario;
import com.folhear.desktop.util.Dialogs;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class CadastroView extends BorderPane {

    private final Stage stage;
    private final TextField campoNome = new TextField();
    private final TextField campoEmail = new TextField();
    private final PasswordField campoSenha = new PasswordField();
    private final TextField campoCidade = new TextField();
    private final ComboBox<String> campoTipo = new ComboBox<>();
    private final Label labelStatus = new Label();

    public CadastroView(Stage stage) {
        this.stage = stage;
        setTop(topo());
        setCenter(centro());
    }

    private Node topo() {
        Label titulo = new Label("📚 Folhear");
        titulo.getStyleClass().add("brand-title");
        HBox bar = new HBox(titulo);
        bar.getStyleClass().add("top-bar");
        bar.setAlignment(Pos.CENTER_LEFT);
        return bar;
    }

    private Node centro() {
        VBox card = new VBox(12);
        card.getStyleClass().add("card");
        card.setMaxWidth(440);

        Label h1 = new Label("Criar conta");
        h1.getStyleClass().add("screen-title");

        campoTipo.getItems().addAll("LEITOR", "AUTOR", "AMBOS");
        campoTipo.setValue("LEITOR");

        card.getChildren().addAll(
                h1,
                campo("Nome", campoNome, "Seu nome completo"),
                campo("E-mail", campoEmail, "voce@email.com"),
                campo("Senha", campoSenha, "••••••••"),
                campo("Cidade", campoCidade, "Sua cidade"),
                comboComLabel("Perfil", campoTipo),
                labelStatus
        );

        labelStatus.setWrapText(true);
        labelStatus.setStyle("-fx-text-fill: #b3432b; -fx-font-size: 12px;");

        HBox botoes = new HBox(10);
        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button-secondary");
        btnVoltar.setOnAction(e -> stage.getScene().setRoot(new LoginView(stage)));

        Button btnCriar = new Button("Criar conta");
        btnCriar.getStyleClass().add("button-primary");
        btnCriar.setOnAction(e -> criar());

        botoes.getChildren().addAll(btnVoltar, btnCriar);
        card.getChildren().add(botoes);

        StackPane wrapper = new StackPane(card);
        wrapper.setPadding(new Insets(50));
        wrapper.setStyle("-fx-background-color: #f6f1e7;");
        return wrapper;
    }

    private VBox campo(String texto, Control campo, String prompt) {
        Label l = new Label(texto);
        l.getStyleClass().add("form-label");
        if (campo instanceof TextField tf) tf.setPromptText(prompt);
        campo.setMaxWidth(Double.MAX_VALUE);
        return new VBox(4, l, campo);
    }

    private VBox comboComLabel(String texto, ComboBox<String> combo) {
        Label l = new Label(texto);
        l.getStyleClass().add("form-label");
        combo.setMaxWidth(Double.MAX_VALUE);
        return new VBox(4, l, combo);
    }

    private void criar() {
        labelStatus.setText("");
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText();
        String cidade = campoCidade.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            labelStatus.setText("Preencha nome, e-mail e senha.");
            return;
        }

        Usuario novo = new Usuario();
        novo.setNome(nome);
        novo.setEmail(email);
        novo.setSenhaHash(sha256(senha));
        novo.setCidade(cidade.isEmpty() ? null : cidade);
        novo.setTipo(campoTipo.getValue());
        novo.setAtivo(true);

        try {
            Usuario criado = Session.get().api().post("/usuarios", novo, Usuario.class);
            Session.get().setUsuarioLogado(criado);
            Dialogs.info("Bem-vindo(a)!", "Conta criada com sucesso.");
            stage.getScene().setRoot(new MainView(stage));
        } catch (Exception ex) {
            Dialogs.erro("Erro ao criar conta", ex.getMessage());
        }
    }

    /** Hash simples apenas para não enviar a senha em texto puro pela rede local. */
    private static String sha256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return texto;
        }
    }
}
