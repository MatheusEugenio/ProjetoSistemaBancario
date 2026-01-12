package view;

import model.Cliente;
import model.Conta;
import service.Banco;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelVisaoGeral extends JPanel implements Painel {

    private Banco banco;
    private JTextArea areaLog;
    private JTextField txtNumeroContaExcluir;

    public PainelVisaoGeral(Banco banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        inicializarComponentes();
        recarregarDados();
    }

    private void inicializarComponentes() {
        // Área de Texto Central
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaLog), BorderLayout.CENTER);

        // --- ÁREA SUL (Controles) ---
        JPanel painelControles = new JPanel();
        painelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelControles.setBorder(BorderFactory.createTitledBorder("Gerenciamento"));

        // Campo para informar qual conta excluir
        painelControles.add(new JLabel("Número da Conta para Excluir:"));
        txtNumeroContaExcluir = new JTextField(6); // Tamanho visual do campo
        painelControles.add(txtNumeroContaExcluir);

        // Botão Excluir
        JButton btnExcluir = new JButton("Excluir Conta");
        btnExcluir.setBackground(new Color(255, 100, 100)); // Vermelho claro
        btnExcluir.setForeground(Color.BLACK);
        btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 12));

        btnExcluir.addActionListener(e -> acaoExcluirConta());
        painelControles.add(btnExcluir);

        // Botão Atualizar (Mantido)
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAtualizar.addActionListener(e -> recarregarDados());
        painelControles.add(btnAtualizar);

        add(painelControles, BorderLayout.SOUTH);
    }

    private void acaoExcluirConta() {
        try {
            String textoNumero = txtNumeroContaExcluir.getText().trim();

            if (textoNumero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o número da conta!");
                return;
            }

            int numeroConta = Integer.parseInt(textoNumero);

            // Confirmação de Segurança
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir a conta Nº " + numeroConta + "?\nEssa ação não pode ser desfeita.",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirmacao == JOptionPane.YES_OPTION) {
                // Chama o metodo no service (Banco) que remove e salva no arquivo
                boolean sucesso = banco.removerConta(numeroConta);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Conta removida com sucesso!");
                    txtNumeroContaExcluir.setText("");
                    recarregarDados(); // Atualiza a tela automaticamente
                } else {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada.");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O número da conta deve ser um valor numérico válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    @Override
    public void recarregarDados() {
        areaLog.setText(""); // Limpa
        areaLog.append("--- CLIENTES E CONTAS ---\n\n");

        List<Cliente> clientes = banco.getClientesDoBanco();

        if (clientes.isEmpty()) {
            areaLog.append("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente c : clientes) {
            areaLog.append("Cliente: " + c.getNome() + "\n");

            List<Conta> contasVinculadas = c.consultarContasVinculadas();
            if (contasVinculadas.isEmpty()) {
                areaLog.append("   (Sem contas)\n");
            } else {
                for (Conta conta : contasVinculadas) {
                    areaLog.append("   -> Conta " + conta.getTipo() + " | Número da Conta: "+ conta.getNumero()+
                            " | Saldo: R$ " + String.format("%.2f", conta.getSaldo()) + "\n");
                }
            }
            areaLog.append("---------------------------\n");
        }
    }
}