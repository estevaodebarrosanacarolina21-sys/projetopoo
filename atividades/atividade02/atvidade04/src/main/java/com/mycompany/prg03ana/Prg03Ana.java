package com.mycompany.prg03ana;

// Componentes de layout, cores, dimensoes, fontes e espacamentos da interface.
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

// Classe principal da aplicacao de tela de login.
public class Prg03Ana {

    // Inicia a interface na fila de eventos do Swing.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Prg03Ana::criarTelaLogin);
    }

    // Monta e exibe todos os componentes da tela de login.
    private static void criarTelaLogin() {
        // Cria a janela principal e define seu comportamento ao fechar.
        JFrame janela = new JFrame("Tela de Login");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(440, 360);
        janela.setLocationRelativeTo(null);

        // Cria o painel externo com margem e fundo da janela.
        JPanel conteudo = new JPanel(new BorderLayout(12, 12));
        conteudo.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        conteudo.setBackground(new Color(245, 245, 245));

        // Configura o titulo exibido no topo da tela.
        JLabel titulo = new JLabel("Sistema Academico", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        titulo.setForeground(new Color(47, 91, 143));
        conteudo.add(titulo, BorderLayout.NORTH);

        // Cria o formulario que organiza os campos em linhas e colunas.
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setOpaque(false);
        GridBagConstraints posicao = new GridBagConstraints();
        posicao.insets = new Insets(8, 6, 8, 6);
        posicao.anchor = GridBagConstraints.WEST;

        // Cria o campo para o usuario informar o login.
        JLabel rotuloLogin = new JLabel("Login:");
        JTextField campoLogin = new JTextField();
        campoLogin.setPreferredSize(new Dimension(220, 30));
        adicionarCampo(formulario, posicao, rotuloLogin, campoLogin, 0);

        // Cria o campo protegido para o usuario informar a senha.
        JLabel rotuloSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setPreferredSize(new Dimension(220, 30));
        adicionarCampo(formulario, posicao, rotuloSenha, campoSenha, 1);

        // Cria e posiciona o botao usado para enviar o formulario.
        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setPreferredSize(new Dimension(110, 32));
        posicao.gridx = 1;
        posicao.gridy = 2;
        posicao.anchor = GridBagConstraints.CENTER;
        formulario.add(botaoEntrar, posicao);

        // Cria o label que exibira a mensagem ou os dados digitados.
        JLabel resultado = new JLabel(" ");
        resultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(208, 208, 170)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        resultado.setBackground(new Color(255, 255, 224));
        resultado.setOpaque(true);

        // Captura os valores e atualiza o resultado ao clicar no botao Entrar.
        botaoEntrar.addActionListener(evento -> {
            // Armazena primeiro os valores digitados em variaveis locais.
            String loginDigitado = campoLogin.getText().trim();
            String senhaDigitada = new String(campoSenha.getPassword());

            // Impede o envio do formulario quando algum campo esta vazio.
            if (loginDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                resultado.setText("Preencha o login e a senha.");
                return;
            }

            // Exibe o login e a senha capturados em linhas separadas.
            resultado.setText("<html>Login digitado: " + loginDigitado
                    + "<br>Senha digitada: " + senhaDigitada + "</html>");
        });

        // Adiciona o formulario e o resultado ao painel principal.
        conteudo.add(formulario, BorderLayout.CENTER);
        conteudo.add(resultado, BorderLayout.SOUTH);

        // Define o painel da janela e torna a interface visivel.
        janela.setContentPane(conteudo);
        janela.setVisible(true);
    }

    // Adiciona um rotulo e seu respectivo campo na linha informada.
    private static void adicionarCampo(JPanel formulario, GridBagConstraints posicao,
            JLabel rotulo, JTextField campo, int linha) {
        // Posiciona o rotulo na primeira coluna da linha.
        posicao.gridx = 0;
        posicao.gridy = linha;
        formulario.add(rotulo, posicao);

        // Posiciona o campo de entrada na segunda coluna da linha.
        posicao.gridx = 1;
        formulario.add(campo, posicao);
    }
}
