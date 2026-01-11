package app;

import exception.InvalidValueException;
import model.Cliente;
import model.Conta;
import model.Endereco;
import service.Banco;
import util.Validacoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

    public class BancoGUI extends JFrame {

        private Banco banco;

        private JTabbedPane abas;
        private JTextArea areaLog; // Onde vamos "imprimir" os dados
        private JComboBox<String> comboClientes; // Para escolher clientes na hora de abrir conta

        private JTextField txtNome, txtDoc, txtDataNasc, txtNomeEmpresa;
        private JTextField txtRua, txtCep, txtNum, txtBairro, txtCidade, txtComplemento;
        private JComboBox<String> comboTipoCliente;

        public BancoGUI() {
            // Inicialização do Banco (Carrega arquivos, faz a persistência funcionar)
            banco = new Banco("PACHECO's Bank");

            setTitle("Sistema Bancário - "+banco.getNomeDoBanco());
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            abas = new JTabbedPane();
            abas.addTab("Cadastro de Clientes", criarPainelCadastro());
            abas.addTab("Cadastrar Conta", criarPainelContas());
            abas.addTab("Transações", criarPainelDeTransacoes()); //falta implementação
            abas.addTab("Visão Geral", criarPainelListagem());
            abas.addTab("Visão Geral das Transações", criarPainelTransacoes()); //falta implementação

            add(abas);

            // Atualiza as listas iniciais
            atualizarListaClientesCombo();
            atualizarAreaLog();
        }

        // CADASTRO (Substitui o metodo adicaoDeCliente)
        private JPanel criarPainelCadastro() {
            JPanel painel = new JPanel(new GridBagLayout());
            painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Configurações de posicionamento (GBC)
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(0, 5, 10, 5);

            JLabel lblHeaderDados = new JLabel("DADOS PESSOAIS");
            lblHeaderDados.setFont(lblHeaderDados.getFont().deriveFont(Font.BOLD, 14f));
            lblHeaderDados.setHorizontalAlignment(SwingConstants.CENTER); // Centralizado
            painel.add(lblHeaderDados, gbc);

            // RESET DAS CONFIGURAÇÕES PARA OS CAMPOS NORMAIS
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridy++;

            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Tipo de Cliente:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            comboTipoCliente = new JComboBox<>(new String[]{"Pessoa Física", "Pessoa Jurídica"});
            painel.add(comboTipoCliente, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Nome do Cliente:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtNome = new JTextField();
            painel.add(txtNome, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            JLabel lblDoc = new JLabel("CPF:");
            painel.add(lblDoc, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtDoc = new JTextField();
            painel.add(txtDoc, gbc);

            // Nome da Empresa (Opcional)
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            JLabel lblEmpresa = new JLabel("Nome da Empresa:");
            painel.add(lblEmpresa, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtNomeEmpresa = new JTextField();
            painel.add(txtNomeEmpresa, gbc);


            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Data de Nascimento (dd/MM/yyyy):"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtDataNasc = new JTextField();
            painel.add(txtDataNasc, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(20, 5, 5, 5);
            JLabel lblHeaderEndereco = new JLabel("ENDEREÇO");
            lblHeaderEndereco.setFont(lblHeaderEndereco.getFont().deriveFont(Font.BOLD, 14f));
            lblHeaderEndereco.setHorizontalAlignment(SwingConstants.CENTER);
            painel.add(lblHeaderEndereco, gbc);

            // Reseta configurações
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Rua:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtRua = new JTextField();
            painel.add(txtRua, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("CEP:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtCep = new JTextField();
            painel.add(txtCep, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Número:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtNum = new JTextField();
            painel.add(txtNum, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Bairro:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtBairro = new JTextField();
            painel.add(txtBairro, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Cidade:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtCidade = new JTextField();
            painel.add(txtCidade, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Complemento:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            txtComplemento = new JTextField();
            painel.add(txtComplemento, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 5, 5, 5);

            JButton btnSalvar = new JButton("Salvar Cliente");
            btnSalvar.setPreferredSize(new Dimension(150, 30));
            painel.add(btnSalvar, gbc);

            lblEmpresa.setVisible(false);
            txtNomeEmpresa.setVisible(false);

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
                painel.revalidate();
                painel.repaint();
            });

            btnSalvar.addActionListener(e -> {
                try {
                    String nome = txtNome.getText();
                    String doc = txtDoc.getText();
                    String data = txtDataNasc.getText();
                    String complemento = txtComplemento.getText();
                    String selecionado = (String) comboTipoCliente.getSelectedItem();
                    boolean isPF = "Pessoa Física".equals(selecionado);
                    String nomeDaEmpresa = txtNomeEmpresa.getText();

                    Validacoes.validacaoDasStrings(nome);
                    Validacoes.validacaoDasDatas(data);

                    if (isPF) {
                        Validacoes.validacaoDasStrings(doc, 11);
                    } else {
                        Validacoes.validacaoDasStrings(doc, 14);
                        if (nomeDaEmpresa == null || nomeDaEmpresa.trim().isEmpty()) {
                            throw new InvalidValueException("Nome da empresa obrigatório.");
                        }
                    }

                    if (complemento == null || complemento.trim().isEmpty()) complemento = "Nenhum";

                    Endereco novoEndereco = new Endereco(
                            txtRua.getText(), txtCep.getText(),
                            Integer.parseInt(txtNum.getText()), complemento,
                            txtBairro.getText(), txtCidade.getText()
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
                        atualizarListaClientesCombo();
                        atualizarAreaLog();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
                    }

                } catch (InvalidValueException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro: Campo 'Número' deve ser numérico.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            });

            return painel;
        }

        // ABRIR CONTA
        private JPanel criarPainelContas() {
            JPanel painel = new JPanel(new GridBagLayout());
            painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);

            // TÍTULO
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 20, 0);

            JLabel lblTitulo = new JLabel("ABERTURA DE NOVA CONTA");
            lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            painel.add(lblTitulo, gbc);

            // Reset para os campos
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // 1. Selecionar Cliente
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            painel.add(new JLabel("Selecione o Cliente:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.ipady = 8;
            // Combo deve ser atributo da classe para ser atualizado depois
            comboClientes = new JComboBox<>();
            comboClientes.setFont(fonteCampos);
            painel.add(comboClientes, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            gbc.ipady = 0;
            painel.add(new JLabel("Tipo de Conta:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.ipady = 8;
            String[] tiposConta = {"Conta Corrente", "Conta Poupança"};
            JComboBox<String> comboTipoConta = new JComboBox<>(tiposConta);
            comboTipoConta.setFont(fonteCampos);
            painel.add(comboTipoConta, gbc);

            // Depósito Inicial
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            gbc.ipady = 0;
            painel.add(new JLabel("Depósito Inicial (R$):"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.ipady = 8;
            JTextField txtDeposito = new JTextField("0.00");
            txtDeposito.setFont(fonteCampos);
            painel.add(txtDeposito, gbc);

            // Senha da Conta
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            gbc.ipady = 0;
            painel.add(new JLabel("Crie uma Senha (4 dígitos):"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.ipady = 8;
            JPasswordField txtSenha = new JPasswordField(); // Campo de senha para esconder os caracteres
            txtSenha.setFont(fonteCampos);
            painel.add(txtSenha, gbc);

            // Botão
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 0, 20, 0);

            JButton btnAbrir = new JButton("Confirmar Abertura");
            btnAbrir.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnAbrir.setPreferredSize(new Dimension(200, 40));
            painel.add(btnAbrir, gbc);

            // Área de Resultado
            gbc.gridy++;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JTextArea areaResultado = new JTextArea(4, 20);
            areaResultado.setEditable(false);
            areaResultado.setBackground(new Color(240, 240, 240));
            areaResultado.setBorder(BorderFactory.createTitledBorder("Dados da Conta Criada"));
            areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
            painel.add(new JScrollPane(areaResultado), gbc);


            // LÓGICA DO BOTÃO
            btnAbrir.addActionListener(e -> {
                try {
                    //Validações básicas de UI
                    String nomeCliente = (String) comboClientes.getSelectedItem();
                    if (nomeCliente == null) {
                        JOptionPane.showMessageDialog(this, "Selecione um cliente!");
                        return;
                    }

                    double valorDeposito = Double.parseDouble(txtDeposito.getText().replace(",", "."));
                    String senha = new String(txtSenha.getPassword());

                    if (senha.length() != 4) {
                        JOptionPane.showMessageDialog(this, "A senha deve ter exatamente 4 dígitos.");
                        return;
                    }

                    String tipoSelecionado = (String) comboTipoConta.getSelectedItem();
                    String finalTipoConta = tipoSelecionado.equalsIgnoreCase("Conta Corrente")? "CC" : "CP";

                    boolean achouCliente = false;
                    Conta novaConta = null;

                    for(Cliente clienteAlvo : banco.getClientesDoBanco()){
                        if(clienteAlvo.getNome().equalsIgnoreCase(nomeCliente)){ //se achou o nome do alvo ele entra no if
                                achouCliente = true;
                                novaConta = banco.abrirConta(clienteAlvo, finalTipoConta, valorDeposito);
                                break;
                        }
                    }

                    if (!achouCliente) {
                        JOptionPane.showMessageDialog(this, "Cliente não encontrado no banco de dados.");
                    }
                    else if (novaConta != null) {
                        // mostra o resultado na caixa de texto
                        areaResultado.setText("");
                        areaResultado.append("SUCESSO!\n");
                        areaResultado.append("Cliente: " + nomeCliente + "\n");
                        areaResultado.append("Tipo: " + tipoSelecionado + "\n");
                        areaResultado.append("Número da Conta: " + novaConta.getNumero() + "\n");
                        areaResultado.append("Saldo Inicial: R$ " + String.format("%.2f", valorDeposito));

                        // limpa os campos
                        txtSenha.setText("");
                        txtDeposito.setText("0.00");
                        atualizarAreaLog(); // Atualiza log geral se existir
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao criar conta.");
                    }

                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valor de depósito inválido.");
                }catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            });

            return painel;
        }

        //LISTAGEM (Substitui mostrarClientesComContasVinculadas)
        private JPanel criarPainelListagem() {
            JPanel painel = new JPanel(new BorderLayout());
            areaLog = new JTextArea();
            areaLog.setEditable(false);
            areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));

            painel.add(new JScrollPane(areaLog), BorderLayout.CENTER);

            JButton btnAtualizar = new JButton("Atualizar Listagem");
            btnAtualizar.addActionListener(e -> atualizarAreaLog());
            painel.add(btnAtualizar, BorderLayout.SOUTH);

            return painel;
        }

        private JPanel criarPainelTransacoes(){
            JPanel painel = new JPanel(new GridLayout(12, 2, 10, 10));
            painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


            return painel;
        }

        private JPanel criarPainelDeTransacoes(){
            JPanel painel = new JPanel(new GridLayout(12, 2, 10, 10));
            painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            return painel;
        }

        // --- Métodos Auxiliares ---

        // Atualiza o ComboBox da aba de Contas para aparecer novos clientes
        private void atualizarListaClientesCombo() {
            comboClientes.removeAllItems();
            List<Cliente> clientes = banco.getClientesDoBanco();
            for (Cliente c : clientes) {
                comboClientes.addItem(c.getNome());
            }
        }

        private void atualizarAreaLog() {
            areaLog.setText(""); // Limpa
            areaLog.append("--- CLIENTES E CONTAS ---\n\n");

            List<Cliente> clientes = banco.getClientesDoBanco();
            for (Cliente c : clientes) {
                areaLog.append("Cliente: " + c.getNome() + "\n");

                List<Conta> contas = c.consultarContasVinculadas();
                if (contas.isEmpty()) {
                    areaLog.append("   (Sem contas)\n");
                } else {
                    for (Conta conta : contas) {
                        areaLog.append("   -> Conta " + conta.getTipo() + " | Saldo: R$ " + String.format("%.2f",conta.getSaldo()) + "\n");
                    }
                }
                areaLog.append("---------------------------\n");
            }
        }

        private void limparCamposCadastro() {
            txtNome.setText("");
            txtDoc.setText("");
            txtDataNasc.setText("");
            txtRua.setText("");
        }

        // Metodo Main para rodar a tela
        public static void main(String[] args) {

            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            SwingUtilities.invokeLater(() -> new BancoGUI().setVisible(true));
        }
}