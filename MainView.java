package com.folhear.desktop.ui;

import com.folhear.desktop.api.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Janela principal, exibida após o login: navegação lateral entre as
 * telas de Livros, Trocas, Clubes, Chat e Notificações.
 */
public class MainView extends BorderPane {

    private final Stage stage;
    private final StackPane conteudo = new StackPane();

    public MainView(Stage stage) {
        this.stage = stage;
        setTop(topo());
        setLeft(navegacao());
        conteudo.setStyle("-fx-background-color: #f6f1e7;");
        conteudo.setPadding(new Insets(24));
        setCenter(conteudo);

        mostrar(new LivrosPane());
    }

    private HBox topo() {
        Label titulo = new Label("📚 Folhear");
        titulo.getStyleClass().add("brand-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String nomeUsuario = Session.get().getUsuarioLogado() != null
                ? Session.get().getUsuarioLogado().getNome()
                : "Visitante";
        Label labelUsuario = new Label("Olá, " + nomeUsuario);
        labelUsuario.setStyle("-fx-text-fill: white;");

        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("button-secondary");
        btnSair.setStyle(btnSair.getStyle() + "; -fx-text-fill: white; -fx-border-color: white;");
        btnSair.setOnAction(e -> {
            Session.get().logout();
            stage.getScene().setRoot(new LoginView(stage));
        });

        HBox bar = new HBox(16, titulo, spacer, labelUsuario, btnSair);
        bar.getStyleClass().add("top-bar");
        bar.setAlignment(Pos.CENTER_LEFT);
        return bar;
    }

    private VBox navegacao() {
        ToggleGroup grupo = new ToggleGroup();

        ToggleButton btnLivros = navBotao("📖  Livros", grupo);
        ToggleButton btnTrocas = navBotao("🔄  Trocas", grupo);
        ToggleButton btnClubes = navBotao("👥  Clubes", grupo);
        ToggleButton btnChat = navBotao("💬  Chat", grupo);
        ToggleButton btnNotificacoes = navBotao("🔔  Notificações", grupo);

        btnLivros.setSelected(true);

        btnLivros.setOnAction(e -> mostrar(new LivrosPane()));
        btnTrocas.setOnAction(e -> mostrar(new TrocasPane()));
        btnClubes.setOnAction(e -> mostrar(new ClubesPane()));
        btnChat.setOnAction(e -> mostrar(new ChatPane()));
        btnNotificacoes.setOnAction(e -> mostrar(new NotificacoesPane()));

        VBox box = new VBox(btnLivros, btnTrocas, btnClubes, btnChat, btnNotificacoes);
        box.getStyleClass().add("side-nav");
        return box;
    }

    private ToggleButton navBotao(String texto, ToggleGroup grupo) {
        ToggleButton b = new ToggleButton(texto);
        b.setToggleGroup(grupo);
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private void mostrar(Region tela) {
        conteudo.getChildren().setAll(tela);
    }
}
