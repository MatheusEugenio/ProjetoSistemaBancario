package view;

import model.Cliente;
import model.Conta;
import service.Banco;
import javax.swing.*;
import java.awt.*;

public class PainelTransacoes extends JPanel implements Painel{

    private Banco banco;

    private JComboBox<Cliente> comboClientesTransacao;
    private JComboBox<Conta> comboContasOrigem;
    private JComboBox<String> comboTipoTransacao;
    private JTextField txtValorTransacao;
    private JComboBox<Conta> comboContaDestino;
    private JButton btnExecutar;
    private JButton btnAtualizarListas;
    private JPasswordField txtSenha;

    // CONSTRUTOR: Recebe o banco
    public PainelTransacoes(Banco banco) {
        this.banco = banco;

        // Configuração do Layout (igual ao seu metodo antigo)
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Chama os métodos para montar a tela
        inicializarComponentes();
        configurarEventos();

        // Carrega os dados iniciais
        recarregarDados();
    }

    private void inicializarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Título
        JLabel lblTitulo = new JLabel("REALIZAR TRANSAÇÕES");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        add(lblTitulo, gbc); // Note: "this.add" ou apenas "add"

        // Reset grid
        gbc.gridwidth = 1;
        gbc.gridy++;

        // 1. Cliente
        add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        comboClientesTransacao = new JComboBox<>();
        add(comboClientesTransacao, gbc);

        // 2. Conta Origem
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Conta de Origem:"), gbc);
        gbc.gridx = 1;
        comboContasOrigem = new JComboBox<>();
        add(comboContasOrigem, gbc);

        // 3. Tipo Transação
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Tipo de Operação:"), gbc);
        gbc.gridx = 1;
        comboTipoTransacao = new JComboBox<>(new String[]{"Depósito", "Saque", "Transferência"});
        add(comboTipoTransacao, gbc);

        // 4. Valor
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Valor R$:"), gbc);
        gbc.gridx = 1;
        txtValorTransacao = new JTextField();
        add(txtValorTransacao, gbc);

        // 5. Senha (Recomendado adicionar)
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField();
        add(txtSenha, gbc);

        // 6. Conta Destino
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblDestino = new JLabel("Conta de destino:");
        add(lblDestino, gbc);

        gbc.gridx = 1;
        comboContaDestino = new JComboBox<>();
        comboContaDestino.setEnabled(false);
        add(comboContaDestino, gbc);

        // 7. Botão Confirmar
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);

        btnExecutar = new JButton("Confirmar Transação");
        btnExecutar.setPreferredSize(new Dimension(180, 40));
        add(btnExecutar, gbc);

        // 8. Botão Recarregar (Opcional, pois a aba atualiza sozinha agora)
        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 0, 0);
        btnAtualizarListas = new JButton("Recarregar Clientes Manualmente");
        btnAtualizarListas.setFont(new Font("SansSerif", Font.PLAIN, 10));
        add(btnAtualizarListas, gbc);
    }

    private void configurarEventos() {

        // Ao trocar cliente -> Atualiza contas dele
        comboClientesTransacao.addActionListener(e -> atualizarComboContasOrigem());

        // Ao trocar tipo -> Habilita/Desabilita destino
        comboTipoTransacao.addActionListener(e -> {
            String tipo = (String) comboTipoTransacao.getSelectedItem();
            boolean isTransferencia = "Transferência".equals(tipo);
            comboContaDestino.setEnabled(isTransferencia);
        });

        // Botão Confirmar
        btnExecutar.addActionListener(e -> executarTransacao());

        // Botão Recarregar (Chama o metodo que limpa e refaz tudo)
        btnAtualizarListas.addActionListener(ev -> recarregarDados());
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
            String senha = new String(txtSenha.getPassword());

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
            recarregarDados(); // Atualiza a tela após sucesso

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    @Override
    public void recarregarDados() {
        // Preenche Clientes
        comboClientesTransacao.removeAllItems();
        for (Cliente c : banco.getClientesDoBanco()) {
            comboClientesTransacao.addItem(c);
        }

        // Preenche Destinos (Todas as contas do banco)
        atualizarComboDestinos();

        // Limpa campos
        txtValorTransacao.setText("");
        txtSenha.setText("");
    }
}
