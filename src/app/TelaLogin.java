package app;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Login - PACHECO's Bank");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);

        // Layout principal com bordas
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(new Color(245, 245, 250)); // Cor de fundo suave
        add(painelPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margem entre componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa 2 colunas

        // --- 1. Título / Logo ---
        JLabel lblTitulo = new JLabel("PACHECO's Bank");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 51, 102)); // Azul escuro
        painelPrincipal.add(lblTitulo, gbc);

        // --- 2. Campos de Usuário ---
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST; // Alinha texto à direita
        painelPrincipal.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Alinha campo à esquerda
        JTextField txtUsuario = new JTextField(15);
        painelPrincipal.add(txtUsuario, gbc);

        // --- 3. Campos de Senha ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField txtSenha = new JPasswordField(15);
        painelPrincipal.add(txtSenha, gbc);

        // --- 4. Botão Entrar ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Botão estica
        gbc.insets = new Insets(20, 10, 10, 10); // Mais espaço em cima

        JButton btnEntrar = new JButton("ACESSAR SISTEMA");
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEntrar.setBackground(new Color(0, 102, 204));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setFocusPainted(false);
        painelPrincipal.add(btnEntrar, gbc);

        // --- AÇÃO DO BOTÃO ---
        btnEntrar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String senha = new String(txtSenha.getPassword());

            // Lógica de autenticação simples (Hardcoded)
            // Num sistema real, você buscaria isso no banco de dados de funcionários
            if (usuario.equals("admin") && senha.equals("1234")) {
                abrirSistemaPrincipal();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuário ou senha inválidos!",
                        "Acesso Negado",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Permite apertar ENTER para logar
        getRootPane().setDefaultButton(btnEntrar);
    }

    private void abrirSistemaPrincipal() {
        // Fecha a tela de login
        this.dispose();

        // Abre a tela do Banco (BancoGUI)
        // SwingUtilities garante que a thread gráfica não trave
        SwingUtilities.invokeLater(() -> {
            new BancoGUI().setVisible(true);
        });
    }

    // O NOVO MAIN DO SEU PROJETO
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
