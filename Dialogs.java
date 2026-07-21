package com.folhear.desktop.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Dialogs {

    public static void info(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static void erro(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static boolean confirmar(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        a.setTitle(titulo);
        a.setHeaderText(null);
        Optional<ButtonType> res = a.showAndWait();
        return res.isPresent() && res.get() == ButtonType.YES;
    }

    public static Optional<String> prompt(String titulo, String label, String valorAtual) {
        TextInputDialog d = new TextInputDialog(valorAtual == null ? "" : valorAtual);
        d.setTitle(titulo);
        d.setHeaderText(null);
        d.setContentText(label);
        return d.showAndWait();
    }
}
