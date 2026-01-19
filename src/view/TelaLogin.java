package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Login - PACHECO's Bank");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel painelFundo = new JPanel(new GridBagLayout());
        painelFundo.setBackground(Estilo.COR_FUNDO_CLARO);

        setContentPane(painelFundo);
        JPanel painelCard = new JPanel(new GridBagLayout());
        painelCard.setBackground(Color.WHITE);
        painelCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(40, 50, 40, 50)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Bem-vindo");
        lblTitulo.setFont(Estilo.FONTE_TITULO);
        lblTitulo.setForeground(Estilo.COR_PRINCIPAL);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painelCard.add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblSub = new JLabel("PACHECO's Bank");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 25, 0);
        painelCard.add(lblSub, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        JLabel lblUser = new JLabel("Usuário");
        lblUser.setFont(Estilo.FONTE_BOTAO);
        painelCard.add(lblUser, gbc);
        gbc.gridy++;
        JTextField txtUsuario = Estilo.criarInput();
        txtUsuario.setPreferredSize(new Dimension(280, 40));
        painelCard.add(txtUsuario, gbc);
        gbc.gridy++;
        JLabel lblPass = new JLabel("Senha");
        lblPass.setFont(Estilo.FONTE_BOTAO);
        painelCard.add(lblPass, gbc);
        gbc.gridy++;
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setFont(Estilo.FONTE_GERAL);
        txtSenha.setBorder(txtUsuario.getBorder());
        txtSenha.setPreferredSize(new Dimension(280, 40));
        painelCard.add(txtSenha, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(25, 0, 0, 0);
        JButton btnEntrar = Estilo.criarBotao("ACESSAR SISTEMA");
        btnEntrar.setPreferredSize(new Dimension(280, 45));
        painelCard.add(btnEntrar, gbc);
        painelFundo.add(painelCard);
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BancoGUI().setVisible(true);
                dispose();
            }
        });
    }
}
