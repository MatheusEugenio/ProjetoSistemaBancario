package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PainelHistoricoDeTransacoes extends JPanel implements Painel {

    private JTextArea areaTransacoes;
    private static final String CAMINHO_ARQUIVO = "dados/RegistroDeTransacoes.txt";

    public PainelHistoricoDeTransacoes() {
        setLayout(new BorderLayout());

        inicializarComponentes();

        recarregarDados();
    }

    private void inicializarComponentes() {
        areaTransacoes = new JTextArea();
        areaTransacoes.setEditable(false);
        areaTransacoes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaTransacoes.setBackground(new Color(250, 250, 250)); // Fundo leve

        add(new JScrollPane(areaTransacoes), BorderLayout.CENTER);

        JButton btnAtualizar = new JButton("Atualizar Histórico");
        btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAtualizar.addActionListener(e -> recarregarDados());

        add(btnAtualizar, BorderLayout.SOUTH);
    }

    @Override
    public void recarregarDados() {
        areaTransacoes.setText("");

        Path path = Paths.get(CAMINHO_ARQUIVO);

        try {
            if (!Files.exists(path)) {
                areaTransacoes.append("Arquivo de registros não encontrado.\n");
                areaTransacoes.append("Faça uma transação para criar o arquivo.");
                return;
            }

            List<String> linhas = Files.readAllLines(path);

            if (linhas.isEmpty()) {
                areaTransacoes.append("Nenhuma transação registrada ainda.\n");
                return;
            }

            areaTransacoes.append("--- EXTRATO DE MOVIMENTAÇÕES ---\n\n");

            for (String linha : linhas) {

                if (linha.trim().isEmpty() || linha.startsWith("---")) {
                    continue;
                }

                formatarEImprimirLinha(linha);
            }

        } catch (IOException e) {
            areaTransacoes.append("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private void formatarEImprimirLinha(String linha) {
        try {
            String[] partes = linha.split("\\|");
            String data = " ", tipo = " ", valor = "0.00", destinatario = null;

            for (String parte : partes) {
                parte = parte.trim();
                if (parte.startsWith("Data")) {
                    data = extrairValor(parte);
                } else if (parte.toLowerCase().contains("tipo")) {
                    tipo = extrairValor(parte);
                } else if (parte.toLowerCase().contains("valor")) {
                    valor = extrairValor(parte);
                } else if (parte.startsWith("Destinatário")) {
                    destinatario = extrairValor(parte);
                }
            }

            areaTransacoes.append("DATA: " + data + "\n");
            if (destinatario != null) areaTransacoes.append("FAVORECIDO: " + destinatario + "\n");
            areaTransacoes.append("TIPO: " + tipo.toUpperCase() + "\n");

            try {
                double v = Double.parseDouble(valor);
                areaTransacoes.append("VALOR: R$ " + String.format("%.2f", v) + "\n");
            } catch (Exception e) {
                areaTransacoes.append("VALOR: R$ " + valor + "\n");
            }
            areaTransacoes.append("---------------------------\n");

        } catch (Exception e) {
            areaTransacoes.append("Reg. Bruto: " + linha + "\n\n");
        }
    }

    private String extrairValor(String parte) {
        return parte.substring(parte.indexOf(":") + 1).trim();
    }
}
