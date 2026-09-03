package com.mycompany.prg03ana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Prg03Ana {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Prg03Ana::criarTelaLogin);
    }

    private static void criarTelaLogin() {
        JFrame janela = new JFrame("Tela de Login");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(440, 360);
        janela.setLocationRelativeTo(null);

        JPanel conteudo = new JPanel(new BorderLayout(12, 12));
        conteudo.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        conteudo.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Sistema Academico", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        titulo.setForeground(new Color(47, 91, 143));
        conteudo.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setOpaque(false);
        GridBagConstraints posicao = new GridBagConstraints();
        posicao.insets = new Insets(8, 6, 8, 6);
        posicao.anchor = GridBagConstraints.WEST;

        JLabel rotuloLogin = new JLabel("Login:");
        JTextField campoLogin = new JTextField();
        campoLogin.setPreferredSize(new Dimension(220, 30));
        adicionarCampo(formulario, posicao, rotuloLogin, campoLogin, 0);

        JLabel rotuloSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setPreferredSize(new Dimension(220, 30));
        adicionarCampo(formulario, posicao, rotuloSenha, campoSenha, 1);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setPreferredSize(new Dimension(110, 32));
        posicao.gridx = 1;
        posicao.gridy = 2;
        posicao.anchor = GridBagConstraints.CENTER;
        formulario.add(botaoEntrar, posicao);

        JLabel resultado = new JLabel(" ");
        resultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(208, 208, 170)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        resultado.setBackground(new Color(255, 255, 224));
        resultado.setOpaque(true);

        // Captura os valores dos campos ao clicar no botao Entrar.
        botaoEntrar.addActionListener(evento -> {
            String loginDigitado = campoLogin.getText().trim();
            String senhaDigitada = new String(campoSenha.getPassword());
            if (loginDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                resultado.setText("Preencha o login e a senha.");
                return;
            }
            resultado.setText("<html>Login digitado: " + loginDigitado
                    + "<br>Senha digitada: " + senhaDigitada + "</html>");
        });

        conteudo.add(formulario, BorderLayout.CENTER);
        conteudo.add(resultado, BorderLayout.SOUTH);
        janela.setContentPane(conteudo);
        janela.setVisible(true);
    }

    private static void adicionarCampo(JPanel formulario, GridBagConstraints posicao,
            JLabel rotulo, JTextField campo, int linha) {
        posicao.gridx = 0;
        posicao.gridy = linha;
        formulario.add(rotulo, posicao);
        posicao.gridx = 1;
        formulario.add(campo, posicao);
    }
}
