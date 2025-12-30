package repository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Transacao {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final String caminhoDoRegistro = "dados/RegistroDeTransacoes.txt";
    private final File arquivoRegistrador;
    private LocalDate dataTransacao;
    private String tipoDeTransacao;
    private Double valTransacao;


    public Transacao(String tipoTransacao, double val){
        this.tipoDeTransacao = tipoTransacao;
        this.valTransacao = val;
        this.arquivoRegistrador = new File(caminhoDoRegistro);

        this.dataTransacao = LocalDate.now();
        dataTransacao.format(formatter);
    }

    public Transacao(String tipoTransacao, double val, LocalDate dataEspecifica){
        this(tipoTransacao, val); // Chama o construtor de cima

        this.dataTransacao = dataEspecifica;
        dataTransacao.format(formatter);
    }

    public void registrar() throws IOException {//falta a implementação das interfaces
        registrarNoArquivo();
    }

    private void registrarNoArquivo() throws IOException {

        boolean escreverCabecalho = !arquivoRegistrador.exists() || arquivoRegistrador.length() == 0;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoDoRegistro,true))){
            if (escreverCabecalho) {
                writer.write("--- EXTRATO BANCÁRIO ---");
                writer.newLine();
            }

            // talvez tenha que refatorar essa parte por conta da formatação
            writer.write("Data da Transação: " + dataTransacao + " | Tipo de transacao: "+ tipoDeTransacao + //se foi saque ou deposito ou transferencia
                    " | Valor da transacao: "+ valTransacao);
            writer.newLine();

            writer.flush();
        }catch (IOException e) {
            throw new IOException("FALHA AO REGISTRAR AS TRANSAÇÕES NO BANCO!", e);
        }
    }

    public String toString(){return "Tipo de Transação: " + tipoDeTransacao+
                                    "\nData da Transação: " + dataTransacao+
                                    String.format("\nValor da Transação: %.2f%n \n", valTransacao);
    }
}
