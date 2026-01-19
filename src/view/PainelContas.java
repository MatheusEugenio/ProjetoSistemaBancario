package view;

import model.Cliente;
import model.Conta;
import service.Banco;

import javax.swing.*;
import java.awt.*;

public class PainelContas extends JPanel implements Painel{

    private JComboBox<String> comboClientes;
    private Banco  banco;

    public PainelContas(Banco banco) {
        this.banco = banco;
        inicializarComponentes(banco);
        recarregarDados();
    }

    @Override
    public void recarregarDados() {
        // Lógica para atualizar o combo box
        if (comboClientes != null) {
            comboClientes.removeAllItems();
            if (banco != null) {
                for (Cliente c : banco.getClientesDoBanco()) {
                    comboClientes.addItem(c.getNome());
                }
            }
        }
    }

    private void inicializarComponentes(Banco banco) {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("ABERTURA DE NOVA CONTA");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, gbc); // CORREÇÃO 2: Adicionamos ao 'this'

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        this.add(new JLabel("Selecione o Cliente:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        comboClientes = new JComboBox<>();
        comboClientes.setFont(fonteCampos);

        if(banco != null && banco.getClientesDoBanco() != null) {
            for(Cliente c : banco.getClientesDoBanco()) {
                comboClientes.addItem(c.getNome());
            }
        }

        this.add(comboClientes, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        this.add(new JLabel("Tipo de Conta:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> comboTipoConta = new JComboBox<>(new String[]{"Conta Corrente", "Conta Poupança"});
        comboTipoConta.setFont(fonteCampos);
        this.add(comboTipoConta, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        this.add(new JLabel("Depósito Inicial (R$):"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField txtDeposito = new JTextField("0.00");
        txtDeposito.setFont(fonteCampos);
        this.add(txtDeposito, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        this.add(new JLabel("Crie uma Senha (4 dígitos):"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setFont(fonteCampos);
        this.add(txtSenha, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAbrir = new JButton("Confirmar Abertura");
        btnAbrir.setPreferredSize(new Dimension(200, 40));
        this.add(btnAbrir, gbc);

        gbc.gridy++; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea areaResultado = new JTextArea(4, 20);
        areaResultado.setEditable(false);
        areaResultado.setBorder(BorderFactory.createTitledBorder("Dados da Conta Criada"));
        this.add(new JScrollPane(areaResultado), gbc);

        // Lógica do Botão
        btnAbrir.addActionListener(e -> {
            try {
                String nomeCliente = (String) comboClientes.getSelectedItem();
                if (nomeCliente == null) throw new Exception("Selecione um cliente!");

                double valor;
                try {
                    valor = Double.parseDouble(txtDeposito.getText().replace(",", "."));
                } catch (NumberFormatException nfe) {
                    throw new Exception("Valor de depósito inválido.");
                }

                String senha = new String(txtSenha.getPassword());
                if (senha.length() != 4) throw new Exception("A senha deve ter 4 dígitos.");

                String tipoSel = (String) comboTipoConta.getSelectedItem();
                String tipoFinal = "Conta Corrente".equals(tipoSel) ? "CC" : "CP";

                Cliente alvo = banco.getClientesDoBanco().stream()
                        .filter(c -> c.getNome().equalsIgnoreCase(nomeCliente))
                        .findFirst()
                        .orElse(null);

                if (alvo != null) {
                    Conta nova = banco.abrirConta(alvo, tipoFinal, valor);
                    if (nova != null) {
                        areaResultado.setText("SUCESSO!\nConta: " + nova.getNumero() + "\nSaldo: R$ " + valor);
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
    }
}