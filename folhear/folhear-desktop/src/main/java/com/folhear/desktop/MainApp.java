package com.folhear.desktop;

import com.folhear.desktop.ui.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ponto de entrada da interface gráfica (JavaFX) do Folhear.
 * Consome a API REST do backend Spring Boot (módulo "folhear").
 */
public class MainApp extends Application {

    public static final double WIDTH = 1080;
    public static final double HEIGHT = 720;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Folhear — Marketplace de Livros Usados");
        Scene scene = new Scene(new LoginView(stage), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
