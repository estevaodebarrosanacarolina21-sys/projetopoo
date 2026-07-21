package com.folhear.desktop.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.folhear.desktop.api.Session;
import com.folhear.desktop.model.Usuario;
import com.folhear.desktop.util.Dialogs;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Tela inicial: login por e-mail (busca o usuário cadastrado na API) ou
 * cadastro de uma nova conta.
 *
 * OBS: o backend atual não expõe um endpoint de autenticação (/auth/login)
 * com verificação de senha — este login localiza o usuário pelo e-mail via
 * GET /usuarios para fins de identificação na interface.
 */
public class LoginView extends BorderPane {

    private final Stage stage;
    private final TextField campoBaseUrl = new TextField(Session.DEFAULT_BASE_URL);
    private final TextField campoEmail = new TextField();
    private final PasswordField campoSenha = new PasswordField();
    private final Label labelStatus = new Label();

    public LoginView(Stage stage) {
        this.stage = stage;
        setPadding(new Insets(0));

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
        VBox card = new VBox(14);
        card.getStyleClass().add("card");
        card.setMaxWidth(420);
        card.setAlignment(Pos.CENTER);

        Label h1 = new Label("Entrar no Folhear");
        h1.getStyleClass().add("screen-title");

        Label sub = new Label("Troque, venda e descubra livros usados perto de você.");
        sub.getStyleClass().add("screen-subtitle");
        sub.setWrapText(true);
        sub.setAlignment(Pos.CENTER);

        VBox campoUrlBox = campoComLabel("Endereço da API", campoBaseUrl);
        campoBaseUrl.setPromptText("http://localhost:8080/api");

        VBox campoEmailBox = campoComLabel("E-mail", campoEmail);
        campoEmail.setPromptText("voce@email.com");

        VBox campoSenhaBox = campoComLabel("Senha", campoSenha);
        campoSenha.setPromptText("••••••••");

        Button btnEntrar = new Button("Entrar");
        btnEntrar.getStyleClass().add("button-primary");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);
        btnEntrar.setOnAction(e -> entrar());

        Button btnCadastrar = new Button("Criar conta nova");
        btnCadastrar.getStyleClass().add("button-secondary");
        btnCadastrar.setMaxWidth(Double.MAX_VALUE);
        btnCadastrar.setOnAction(e -> stage.getScene().setRoot(new CadastroView(stage)));

        labelStatus.setWrapText(true);
        labelStatus.setStyle("-fx-text-fill: #b3432b; -fx-font-size: 12px;");

        Label aviso = new Label("Modo desenvolvimento: o login localiza sua conta pelo e-mail cadastrado na API.");
        aviso.setWrapText(true);
        aviso.setStyle("-fx-text-fill: #9c8f81; -fx-font-size: 11px;");
        aviso.setAlignment(Pos.CENTER);

        card.getChildren().addAll(h1, sub, campoUrlBox, campoEmailBox, campoSenhaBox,
                labelStatus, btnEntrar, btnCadastrar, aviso);

        StackPane wrapper = new StackPane(card);
        wrapper.setPadding(new Insets(60));
        wrapper.setStyle("-fx-background-color: #f6f1e7;");
        return wrapper;
    }

    private VBox campoComLabel(String texto, Control campo) {
        Label l = new Label(texto);
        l.getStyleClass().add("form-label");
        campo.setMaxWidth(Double.MAX_VALUE);
        VBox box = new VBox(4, l, campo);
        return box;
    }

    private void entrar() {
        String url = campoBaseUrl.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText();

        labelStatus.setText("");

        if (url.isEmpty() || email.isEmpty()) {
            labelStatus.setText("Informe o endereço da API e o e-mail.");
            return;
        }
        if (senha.isEmpty()) {
            labelStatus.setText("Informe a senha.");
            return;
        }

        Session.get().api().setBaseUrl(url.endsWith("/") ? url.substring(0, url.length() - 1) : url);

        try {
            List<Usuario> usuarios = Session.get().api().get("/usuarios", new TypeReference<List<Usuario>>() {});
            Usuario encontrado = usuarios.stream()
                    .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                    .findFirst()
                    .orElse(null);

            if (encontrado == null) {
                labelStatus.setText("Nenhuma conta encontrada com este e-mail. Crie uma conta nova.");
                return;
            }

            Session.get().setUsuarioLogado(encontrado);
            stage.getScene().setRoot(new MainView(stage));
        } catch (Exception ex) {
            Dialogs.erro("Falha ao conectar", ex.getMessage());
        }
    }
}
