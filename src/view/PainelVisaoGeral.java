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

    public PainelVisaoGeral(Banco banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        inicializarComponentes();

        // Já carrega os dados assim que abre o programa
        recarregarDados();
    }

    private void inicializarComponentes() {
        // Área de Texto Central
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Adiciona barra de rolagem
        add(new JScrollPane(areaLog), BorderLayout.CENTER);

        // Botão Inferior
        JButton btnAtualizar = new JButton("Atualizar Listagem");
        btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 12));

        // Ação do botão: Chama o mesmo metodo que a interface usa
        btnAtualizar.addActionListener(e -> recarregarDados());

        add(btnAtualizar, BorderLayout.SOUTH);
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

            List<Conta> contas = c.consultarContasVinculadas();
            if (contas.isEmpty()) {
                areaLog.append("   (Sem contas)\n");
            } else {
                for (Conta conta : contas) {
                    areaLog.append("   -> Conta " + conta.getTipo() +
                            " | Saldo: R$ " + String.format("%.2f", conta.getSaldo()) + "\n");
                }
            }
            areaLog.append("---------------------------\n");
        }
    }
}