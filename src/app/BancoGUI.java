package app;

import model.Cliente;
import model.Conta;
import service.Banco;
import view.*;

import javax.swing.*;
import java.awt.*;

public class BancoGUI extends JFrame {

    private Banco banco;
    private JTabbedPane abas;

    private JComboBox<String> comboClientes;

    public BancoGUI() {
        // 1. Configurações Iniciais
        banco = new Banco("PACHECO's Bank");
        setTitle("Sistema Bancário - " + banco.getNomeDoBanco());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Instanciando os Painéis
        PainelTransacoes pTransacoes = new PainelTransacoes(banco);
        PainelVisaoGeral pVisao = new PainelVisaoGeral(banco);
        PainelHistoricoDeTransacoes pHistorico = new PainelHistoricoDeTransacoes();

        PainelCadastro pCadastro = new PainelCadastro(banco, () -> {

            atualizarListaClientesCombo();
            pVisao.recarregarDados();
            pTransacoes.recarregarDados();
        });

        //Abas
        abas = new JTabbedPane();
        abas.addTab("Cadastro de Clientes", pCadastro);
        abas.addTab("Cadastrar Conta", criarPainelContas());
        abas.addTab("Transações", pTransacoes);
        abas.addTab("Visão Geral", pVisao);
        abas.addTab("Histórico", pHistorico);

        add(abas);

        abas.addChangeListener(e -> {
            Component c = abas.getSelectedComponent();
            if (c instanceof Painel) {
                ((Painel) c).recarregarDados();
            }
        });

        atualizarListaClientesCombo();
    }

    // MÉTODOS RESTANTES (Abertura de Conta)
    private void atualizarListaClientesCombo() {
        if (comboClientes != null) {
            comboClientes.removeAllItems();
            for (Cliente c : banco.getClientesDoBanco()) {
                comboClientes.addItem(c.getNome());
            }
        }
    }

    private JPanel criarPainelContas() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);

        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("ABERTURA DE NOVA CONTA");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblTitulo, gbc);

        // Reset
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;

        // Cliente
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        painel.add(new JLabel("Selecione o Cliente:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        comboClientes = new JComboBox<>();
        comboClientes.setFont(fonteCampos);
        painel.add(comboClientes, gbc);

        // Tipo Conta
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        painel.add(new JLabel("Tipo de Conta:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> comboTipoConta = new JComboBox<>(new String[]{"Conta Corrente", "Conta Poupança"});
        comboTipoConta.setFont(fonteCampos);
        painel.add(comboTipoConta, gbc);

        // Depósito
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        painel.add(new JLabel("Depósito Inicial (R$):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField txtDeposito = new JTextField("0.00");
        txtDeposito.setFont(fonteCampos);
        painel.add(txtDeposito, gbc);

        // Senha
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        painel.add(new JLabel("Crie uma Senha (4 dígitos):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setFont(fonteCampos);
        painel.add(txtSenha, gbc);

        // Botão
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAbrir = new JButton("Confirmar Abertura");
        btnAbrir.setPreferredSize(new Dimension(200, 40));
        painel.add(btnAbrir, gbc);

        // Área Resultado
        gbc.gridy++; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea areaResultado = new JTextArea(4, 20);
        areaResultado.setEditable(false);
        areaResultado.setBorder(BorderFactory.createTitledBorder("Dados da Conta Criada"));
        painel.add(new JScrollPane(areaResultado), gbc);

        // Lógica
        btnAbrir.addActionListener(e -> {
            try {
                String nomeCliente = (String) comboClientes.getSelectedItem();
                if (nomeCliente == null) throw new Exception("Selecione um cliente!");

                double valor = Double.parseDouble(txtDeposito.getText().replace(",", "."));
                String senha = new String(txtSenha.getPassword());

                if (senha.length() != 4) throw new Exception("A senha deve ter 4 dígitos.");

                String tipoSel = (String) comboTipoConta.getSelectedItem();
                String tipoFinal = tipoSel.equals("Conta Corrente") ? "CC" : "CP";

                // Busca cliente
                Cliente alvo = banco.getClientesDoBanco().stream()
                        .filter(c -> c.getNome().equalsIgnoreCase(nomeCliente))
                        .findFirst()
                        .orElse(null);

                if (alvo != null) {
                    Conta nova = banco.abrirConta(alvo, tipoFinal, valor);
                    if (nova != null) {

                        areaResultado.setText("SUCESSO!\nConta: " + nova.getNumero() + "\nSaldo: " + valor);
                        txtSenha.setText("");
                        txtDeposito.setText("0.00");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        return painel;
    }
}