package view;

import model.Cliente;
import model.Conta;
import service.Banco;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BancoGUI extends JFrame {

    private Banco banco;
    private JPanel painelConteudo;
    private CardLayout cardLayout;

    private PainelCadastro pCadastro;
    private PainelTransacoes pTransacoes;
    private PainelVisaoGeral pVisao;
    private PainelHistoricoDeTransacoes pHistorico;
    private JPanel pNovaConta;

    private JComboBox<String> comboClientesConta;
    private JComboBox<String> comboTipoConta;
    private JTextField txtDepositoConta;
    private JPasswordField txtSenhaConta;
    private JTextArea areaResConta;

    public BancoGUI() {
        banco = new Banco("PACHECO's Bank");

        setTitle("Sistema Bancário - Dashboard");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarPaineis();
        construirInterface();
        navegarPara("VISAO_GERAL");
    }

    private void inicializarPaineis() {
        Runnable acaoAtualizar = new Runnable() {
            @Override
            public void run() {
                atualizarComboClientes();
                if(pVisao != null) pVisao.recarregarDados();
                if(pTransacoes != null) pTransacoes.recarregarDados();
            }
        };

        pCadastro = new PainelCadastro(banco, acaoAtualizar);
        pTransacoes = new PainelTransacoes(banco);
        pVisao = new PainelVisaoGeral(banco);
        pHistorico = new PainelHistoricoDeTransacoes();
        pNovaConta = criarPainelNovaConta();

        atualizarComboClientes();
    }

    private void construirInterface() {
        JPanel menuLateral = new JPanel();
        menuLateral.setBackground(Estilo.COR_MENU);
        menuLateral.setPreferredSize(new Dimension(240, getHeight()));
        menuLateral.setLayout(new GridLayout(10, 1, 0, 5));
        menuLateral.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblLogo = new JLabel("PACHECO's");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        menuLateral.add(lblLogo);

        adicionarBotaoMenu(menuLateral, "Visão Geral", "VISAO_GERAL");
        adicionarBotaoMenu(menuLateral, "Clientes", "CADASTRO");
        adicionarBotaoMenu(menuLateral, "Abrir Conta", "NOVA_CONTA");
        adicionarBotaoMenu(menuLateral, "Transações", "TRANSACOES");
        adicionarBotaoMenu(menuLateral, "Histórico", "HISTORICO");

        menuLateral.add(new JLabel());

        JButton btnSair = Estilo.criarBotaoMenu("Sair do Sistema");
        btnSair.setForeground(new Color(255, 100, 100));
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
        menuLateral.add(btnSair);

        add(menuLateral, BorderLayout.WEST);

        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);
        painelConteudo.setBackground(Estilo.COR_FUNDO_CLARO);
        painelConteudo.setBorder(new EmptyBorder(20, 30, 20, 30));

        painelConteudo.add(pVisao, "VISAO_GERAL");
        painelConteudo.add(pCadastro, "CADASTRO");
        painelConteudo.add(pNovaConta, "NOVA_CONTA");
        painelConteudo.add(pTransacoes, "TRANSACOES");
        painelConteudo.add(pHistorico, "HISTORICO");

        add(painelConteudo, BorderLayout.CENTER);
    }

    private void adicionarBotaoMenu(JPanel painel, String texto, final String chaveCard) {
        JButton btn = Estilo.criarBotaoMenu(texto);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarPara(chaveCard);
            }
        });
        painel.add(btn);
    }

    private void navegarPara(String chave) {
        cardLayout.show(painelConteudo, chave);
        try {
            for (Component c : painelConteudo.getComponents()) {
                if (c.isVisible() && c instanceof Painel) {
                    ((Painel) c).recarregarDados();
                }
            }
        } catch (Exception e) {
        }

        if (chave.equals("NOVA_CONTA")) atualizarComboClientes();
    }

    private JPanel criarPainelNovaConta() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("Abertura de Conta");
        lblTitulo.setFont(Estilo.FONTE_TITULO);
        lblTitulo.setForeground(Estilo.COR_PRINCIPAL);
        gbc.gridwidth = 2;
        painel.add(lblTitulo, gbc);

        gbc.gridwidth = 1; gbc.gridy++;

        painel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        comboClientesConta = new JComboBox<>();
        comboClientesConta.setFont(Estilo.FONTE_GERAL);
        painel.add(comboClientesConta, gbc);

        gbc.gridy++; gbc.gridx = 0;
        painel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboTipoConta = new JComboBox<>(new String[]{"Conta Corrente", "Conta Poupança"});
        comboTipoConta.setFont(Estilo.FONTE_GERAL);
        painel.add(comboTipoConta, gbc);

        gbc.gridy++; gbc.gridx = 0;
        painel.add(new JLabel("Depósito Inicial (R$):"), gbc);
        gbc.gridx = 1;
        txtDepositoConta = Estilo.criarInput();
        painel.add(txtDepositoConta, gbc);

        gbc.gridy++; gbc.gridx = 0;
        painel.add(new JLabel("Senha (4 dígitos):"), gbc);
        gbc.gridx = 1;
        txtSenhaConta = new JPasswordField();
        txtSenhaConta.setBorder(txtDepositoConta.getBorder());
        painel.add(txtSenhaConta, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton btnSalvar = Estilo.criarBotao("CONFIRMAR ABERTURA");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoAbrirConta();
            }
        });
        painel.add(btnSalvar, gbc);

        gbc.gridy++;
        areaResConta = new JTextArea(4, 30);
        areaResConta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        areaResConta.setEditable(false);
        painel.add(new JScrollPane(areaResConta), gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Estilo.COR_FUNDO_CLARO);
        wrapper.add(painel, BorderLayout.NORTH);
        return wrapper;
    }

    private void acaoAbrirConta() {
        try {
            String nomeCliente = (String) comboClientesConta.getSelectedItem();
            if (nomeCliente == null) throw new Exception("Selecione um cliente!");

            String valorStr = txtDepositoConta.getText().replace(",", ".");
            if(valorStr.isEmpty()) valorStr = "0";
            double valor = Double.parseDouble(valorStr);

            String senha = new String(txtSenhaConta.getPassword());
            if (senha.length() != 4) throw new Exception("Senha deve ter 4 dígitos.");

            String tipo = comboTipoConta.getSelectedIndex() == 0 ? "CC" : "CP";

            Cliente alvo = null;
            for(Cliente c : banco.getClientesDoBanco()) {
                if(c.getNome().equals(nomeCliente)) {
                    alvo = c; break;
                }
            }

            if (alvo != null) {
                Conta nova = banco.abrirConta(alvo, tipo, valor);
                areaResConta.setText("SUCESSO!\nConta: " + nova.getNumero() + "\nSaldo: " + valor);
                txtDepositoConta.setText("");
                txtSenhaConta.setText("");

                if(pVisao != null) pVisao.recarregarDados();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void atualizarComboClientes() {
        if(comboClientesConta != null) {
            comboClientesConta.removeAllItems();
            for(Cliente c : banco.getClientesDoBanco()) {
                comboClientesConta.addItem(c.getNome());
            }
        }
    }
}