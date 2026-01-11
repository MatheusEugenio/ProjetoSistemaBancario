package app;

import model.Cliente;
import model.Conta;
import service.Banco;
import view.*; // Certifique-se que seus painéis estão neste pacote

import javax.swing.*;
import java.awt.*;

public class BancoGUI extends JFrame {

    private Banco banco;
    private JTabbedPane abas;

    // A ÚNICA variável de componente que sobrou (porque o método criarPainelContas ainda está aqui)
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
        PainelHistoricoDeTransacoes pHistorico = new PainelHistoricoDeTransacoes(); // Confirme se o nome da classe é PainelHistorico ou PainelHistoricoDeTransacoes

        // 3. Painel Cadastro com Callback (Avisa os outros quando salvar)
        PainelCadastro pCadastro = new PainelCadastro(banco, () -> {
            // AQUI ESTAVA O ERRO: Não chamamos mais métodos locais, chamamos os painéis!
            atualizarListaClientesCombo(); // Atualiza a aba "Criar Conta" (local)
            pVisao.recarregarDados();      // Atualiza Visão Geral
            pTransacoes.recarregarDados(); // Atualiza Transações
        });

        // 4. Montando as Abas
        abas = new JTabbedPane();
        abas.addTab("Cadastro de Clientes", pCadastro);
        abas.addTab("Cadastrar Conta", criarPainelContas()); // Este ainda é local
        abas.addTab("Transações", pTransacoes);
        abas.addTab("Visão Geral", pVisao);
        abas.addTab("Histórico", pHistorico);

        add(abas);

        // 5. Listener para Auto-Refresh
        abas.addChangeListener(e -> {
            Component c = abas.getSelectedComponent();
            // CORREÇÃO: O nome da interface é PainelAtualizavel
            if (c instanceof Painel) {
                ((Painel) c).recarregarDados();
            }
        });

        // Carga inicial
        atualizarListaClientesCombo();
    }

    // --- MÉTODOS RESTANTES (Abertura de Conta) ---
    // Mantivemos este localmente por enquanto

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

        // Área Resultado (Local apenas para feedback visual imediato)
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
                    if (nova != null) { // Assumindo que abrirConta retorna a conta ou null
                        // Define a senha na conta (se o método abrirConta já não definir)
                        // nova.setSenha(senha);

                        areaResultado.setText("SUCESSO!\nConta: " + nova.getNumero() + "\nSaldo: " + valor);
                        txtSenha.setText("");
                        txtDeposito.setText("0.00");

                        // IMPORTANTE: Avisar a aba de visão geral que tem conta nova!
                        // Como pVisao não é acessível aqui dentro facilmente sem mudar o escopo,
                        // o listener "abas.addChangeListener" cuidará disso quando o usuário trocar de aba.
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