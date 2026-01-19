package view;

import model.Cliente;
import model.Conta;
import service.Banco;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelTransacoes extends JPanel implements Painel {

    private Banco banco;
    private JComboBox<Cliente> comboClientesTransacao;
    private JComboBox<Conta> comboContasOrigem;
    private JComboBox<String> comboTipoTransacao;
    private JTextField txtValorTransacao;
    private JComboBox<Conta> comboContaDestino;
    private JButton btnExecutar;
    private JButton btnAtualizarListas;

    public PainelTransacoes(Banco banco) {
        this.banco = banco;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inicializarComponentes();
        configurarEventos();
        recarregarDados();
    }

    private void inicializarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("REALIZAR TRANSAÇÕES");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1; gbc.gridy++;

        add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        comboClientesTransacao = new JComboBox<>();
        add(comboClientesTransacao, gbc);

        gbc.gridy++; gbc.gridx = 0;
        add(new JLabel("Conta de Origem:"), gbc);
        gbc.gridx = 1;
        comboContasOrigem = new JComboBox<>();
        add(comboContasOrigem, gbc);

        gbc.gridy++; gbc.gridx = 0;
        add(new JLabel("Tipo de Operação:"), gbc);
        gbc.gridx = 1;
        comboTipoTransacao = new JComboBox<>(new String[]{"Depósito", "Saque", "Transferência"});
        add(comboTipoTransacao, gbc);

        gbc.gridy++; gbc.gridx = 0;
        add(new JLabel("Valor R$:"), gbc);
        gbc.gridx = 1;
        txtValorTransacao = new JTextField();
        add(txtValorTransacao, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel lblDestino = new JLabel("Conta de destino:");
        add(lblDestino, gbc);

        gbc.gridx = 1;
        comboContaDestino = new JComboBox<>();
        comboContaDestino.setEnabled(false);
        add(comboContaDestino, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);

        btnExecutar = new JButton("Confirmar Transação");
        btnExecutar.setPreferredSize(new Dimension(180, 40));
        add(btnExecutar, gbc);
      
        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 0, 0);
        btnAtualizarListas = new JButton("Recarregar Clientes Manualmente");
        btnAtualizarListas.setFont(new Font("SansSerif", Font.PLAIN, 10));
        add(btnAtualizarListas, gbc);
    }

    private void configurarEventos() {
        comboClientesTransacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarComboContasOrigem();
            }
        });

        comboTipoTransacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipoTransacao.getSelectedItem();
                boolean isTransferencia = "Transferência".equals(tipo);
                comboContaDestino.setEnabled(isTransferencia);
            }
        });

        btnExecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarTransacao();
            }
        });

        btnAtualizarListas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recarregarDados();
            }
        });
    }

    private void atualizarComboContasOrigem() {
        comboContasOrigem.removeAllItems();
        Cliente clienteSelecionado = (Cliente) comboClientesTransacao.getSelectedItem();

        if (clienteSelecionado != null) {
            for (Conta c : clienteSelecionado.consultarContasVinculadas()) {
                comboContasOrigem.addItem(c);
            }
        }
    }

    private void atualizarComboDestinos() {
        comboContaDestino.removeAllItems();
        for (Cliente c : banco.getClientesDoBanco()) {
            for (Conta conta : c.consultarContasVinculadas()) {
                comboContaDestino.addItem(conta);
            }
        }
    }

    private void executarTransacao() {
        try {
            Conta contaOrigem = (Conta) comboContasOrigem.getSelectedItem();
            if (contaOrigem == null) throw new Exception("Selecione conta de origem.");

            String tipo = (String) comboTipoTransacao.getSelectedItem();
            double valor = Double.parseDouble(txtValorTransacao.getText().replace(",", "."));

            if ("Transferência".equals(tipo)) {
                Conta contaDestino = (Conta) comboContaDestino.getSelectedItem();
                if (contaDestino == null) throw new Exception("Selecione destino.");
                if (contaDestino.equals(contaOrigem)) throw new Exception("Contas iguais.");
                contaOrigem.transferir(contaDestino, valor);
            } else if ("Saque".equals(tipo)) {
                contaOrigem.sacar(valor);
            } else {
                contaOrigem.depositar(valor);
            }

            JOptionPane.showMessageDialog(this, "Sucesso!");
            recarregarDados();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    @Override
    public void recarregarDados() {
        comboClientesTransacao.removeAllItems();
        for (Cliente c : banco.getClientesDoBanco()) {
            comboClientesTransacao.addItem(c);
        }
        atualizarComboDestinos();
        txtValorTransacao.setText("");
    }
}