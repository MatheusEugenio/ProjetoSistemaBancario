package app;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Login - PACHECO's Bank");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(new Color(245, 245, 250));
        add(painelPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("PACHECO's Bank");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 51, 102));
        painelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtUsuario = new JTextField(15);
        painelPrincipal.add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField txtSenha = new JPasswordField(15);
        painelPrincipal.add(txtSenha, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10);

        JButton btnEntrar = new JButton("ACESSAR SISTEMA");
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEntrar.setBackground(new Color(0, 102, 204));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setFocusPainted(false);
        painelPrincipal.add(btnEntrar, gbc);

        btnEntrar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String senha = new String(txtSenha.getPassword());

            if (usuario.equals("admin") && senha.equals("1234")) {
                abrirSistemaPrincipal();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuário ou senha inválidos!",
                        "Acesso Negado",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        getRootPane().setDefaultButton(btnEntrar);
    }

    private void abrirSistemaPrincipal() {
        this.dispose();

        SwingUtilities.invokeLater(() -> {
            new BancoGUI().setVisible(true);
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
