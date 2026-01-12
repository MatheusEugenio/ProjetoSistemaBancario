package view;

import exception.InvalidValueException;
import model.Endereco;
import service.Banco;
import util.Validacoes;

import javax.swing.*;
        import java.awt.*;

public class PainelCadastro extends JPanel {

    // Dependências
    private Banco banco;
    private Runnable acaoAposSalvar; // Um "gatilho" para avisar a tela principal

    // Componentes Visuais (Atributos da classe para serem lidos no botão)
    private JComboBox<String> comboTipoCliente;
    private JTextField txtNome, txtDoc, txtDataNasc, txtNomeEmpresa;
    private JTextField txtRua, txtCep, txtNum, txtBairro, txtCidade, txtComplemento;
    private JLabel lblDoc, lblEmpresa;

    // CONSTRUTOR
    public PainelCadastro(Banco banco, Runnable acaoAposSalvar) {
        this.banco = banco;
        this.acaoAposSalvar = acaoAposSalvar;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Título Dados Pessoais ---
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 10, 5);
        JLabel lblHeaderDados = new JLabel("DADOS PESSOAIS");
        lblHeaderDados.setFont(lblHeaderDados.getFont().deriveFont(Font.BOLD, 14f));
        lblHeaderDados.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblHeaderDados, gbc);

        // Reset config
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy++;

        // Tipo Cliente
        gbc.gridx = 0; gbc.weightx = 0.0;
        add(new JLabel("Tipo de Cliente:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        comboTipoCliente = new JComboBox<>(new String[]{"Pessoa Física", "Pessoa Jurídica"});
        add(comboTipoCliente, gbc);

        // Nome
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        add(new JLabel("Nome do Cliente:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNome = new JTextField();
        add(txtNome, gbc);

        // Documento (CPF/CNPJ)
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        lblDoc = new JLabel("CPF:");
        add(lblDoc, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDoc = new JTextField();
        add(txtDoc, gbc);

        // Nome Empresa (Opcional)
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        lblEmpresa = new JLabel("Nome da Empresa:");
        add(lblEmpresa, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNomeEmpresa = new JTextField();
        add(txtNomeEmpresa, gbc);

        // Inicialmente invisíveis
        lblEmpresa.setVisible(false);
        txtNomeEmpresa.setVisible(false);

        // Data Nascimento
        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.0;
        add(new JLabel("Data de Nascimento (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDataNasc = new JTextField();
        add(txtDataNasc, gbc);

        // --- Título Endereço ---
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        JLabel lblHeaderEndereco = new JLabel("ENDEREÇO");
        lblHeaderEndereco.setFont(lblHeaderEndereco.getFont().deriveFont(Font.BOLD, 14f));
        lblHeaderEndereco.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblHeaderEndereco, gbc);

        // Reset config
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos de Endereço
        adicionarCampo(gbc, "Rua:", txtRua = new JTextField());
        adicionarCampo(gbc, "CEP:", txtCep = new JTextField());
        adicionarCampo(gbc, "Número:", txtNum = new JTextField());
        adicionarCampo(gbc, "Bairro:", txtBairro = new JTextField());
        adicionarCampo(gbc, "Cidade:", txtCidade = new JTextField());
        adicionarCampo(gbc, "Complemento:", txtComplemento = new JTextField());

        // Botão Salvar
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);

        JButton btnSalvar = new JButton("Salvar Cliente");
        btnSalvar.setPreferredSize(new Dimension(150, 30));

        // Lógica do Botão
        btnSalvar.addActionListener(e -> tentarSalvarCliente());

        add(btnSalvar, gbc);
    }

    // Metodo auxiliar para não repetir código de GridBag
    private void adicionarCampo(GridBagConstraints gbc, String rotulo, JTextField campo) {
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        add(new JLabel(rotulo), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(campo, gbc);
    }

    private void configurarEventos() {
        // Lógica visual: Troca labels CPF/CNPJ e esconde/mostra campo Empresa
        comboTipoCliente.addActionListener(e -> {
            String selecionado = (String) comboTipoCliente.getSelectedItem();
            if ("Pessoa Física".equals(selecionado)) {
                lblDoc.setText("CPF:");
                lblEmpresa.setVisible(false);
                txtNomeEmpresa.setVisible(false);
            } else {
                lblDoc.setText("CNPJ:");
                lblEmpresa.setVisible(true);
                txtNomeEmpresa.setVisible(true);
            }
            // Revalida o layout para ajustar os espaços
            revalidate();
            repaint();
        });
    }

    private void tentarSalvarCliente() {
        try {
            String nome = txtNome.getText();
            String doc = txtDoc.getText();
            String data = txtDataNasc.getText();
            String complemento = txtComplemento.getText();
            String cep = txtCep.getText();
            String selecionado = (String) comboTipoCliente.getSelectedItem();
            boolean isPF = "Pessoa Física".equals(selecionado);
            String nomeDaEmpresa = txtNomeEmpresa.getText();

            // Validações
            Validacoes.validacaoDasStrings(nome);
            Validacoes.validacaoDasDatas(data);

            if (isPF) {
                Validacoes.validacaoDasStrings(doc, 11, "CPF");
            } else {
                Validacoes.validacaoDasStrings(doc, 14, "CNPJ");

                if (nomeDaEmpresa == null || nomeDaEmpresa.trim().isEmpty()) {
                    throw new InvalidValueException("ERRO: Nome da empresa obrigatório.");
                }
            }

            if (txtRua.getText().trim().isEmpty() ||
                    cep.trim().isEmpty() ||
                    txtBairro.getText().trim().isEmpty() ||
                    txtCidade.getText().trim().isEmpty()) {

                throw new InvalidValueException("ERRO: Todos os campos de endereço (exceto complemento) são obrigatórios.");
            }

            Validacoes.validacaoDasStrings(cep, 8, "CEP");
            if (complemento == null || complemento.trim().isEmpty()) complemento = "Nenhum";

            // 4. Cria o objeto Endereço (agora seguro)
            Endereco novoEndereco = new Endereco(
                    txtRua.getText(),
                    cep,
                    Integer.parseInt(txtNum.getText()),
                    complemento,
                    txtBairro.getText(),
                    txtCidade.getText()
            );

            boolean sucesso;
            if (isPF) {
                sucesso = banco.adicionarCliente(nome, doc, novoEndereco, data);
            } else {
                sucesso = banco.adicionarCliente(nome, doc, novoEndereco, data, nomeDaEmpresa);
            }

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente Cadastrado!");
                limparCamposCadastro();
                comboTipoCliente.setSelectedIndex(0);

                if (acaoAposSalvar != null) {
                    acaoAposSalvar.run();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Verifique se o cliente já existe.");
            }

        } catch (InvalidValueException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: O Campo 'Número' deve ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void limparCamposCadastro() {
        txtNome.setText("");
        txtDoc.setText("");
        txtDataNasc.setText("");
        txtNomeEmpresa.setText("");
        txtRua.setText("");
        txtCep.setText("");
        txtNum.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtComplemento.setText("");
    }
}
