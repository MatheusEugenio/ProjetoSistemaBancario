package Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transacao {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String caminhoDoRegistro = "dados/RegistroDeTransacoes.txt";
    private LocalDate dataTransacao;
    private String tipo_de_transacao;
    private Double valTransacao;
    private final File arquivoRegistrador;


    public Transacao(String tipoTransacao, double val){
        this.tipo_de_transacao = tipoTransacao;
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

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoDoRegistro,true))){
            if (!arquivoRegistrador.exists() || arquivoRegistrador.length() == 0) {
                writer.write("--- REGISTRO DE TRANSAÇÕES BANCÁRIAS ---");
                writer.newLine();
            }

            writer.write("Data da Transação: " + dataTransacao +
                        "| Tipo de transacao: "+ tipo_de_transacao +
                        "| Valor da transacao: "+ valTransacao);
            writer.newLine();

            writer.flush();
        }catch (IOException e) {
            throw new IOException("FALHA AO REGISTRAR AS TRANSAÇÕES NO BANCO!");
        }

        System.out.println("--- REGISTRO DE TRANSAÇÕES BANCÁRIAS ---");
        System.out.println("Data da Transação: " + dataTransacao);
        System.out.println("Tipo de Transação: " + tipo_de_transacao);
        System.out.printf("Valor da Transação: %.2f%n", valTransacao);
        System.out.println("---------------------------------------------");
    }

    public LocalDate getDataTransacao() {return dataTransacao;}
    public String getTipo_de_transacao() {return tipo_de_transacao;}
    public Double getValTransacao() {return valTransacao;}
}
