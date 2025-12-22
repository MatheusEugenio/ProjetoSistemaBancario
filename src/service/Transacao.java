package service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transacao {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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

        System.out.println("--- EXTRATO BANCARIO ---");
        System.out.println("Data da Transação: " + dataTransacao);
        System.out.println("Tipo de Transação: " + tipoDeTransacao);
        System.out.printf("Valor da Transação: %.2f%n", valTransacao);
        System.out.println("---------------------------------------------");
    }

    public void exibicaoDoExtrato() throws IOException { ///CLASSE NÃO TERMINADA
        try(BufferedReader reader = new BufferedReader(new FileReader(caminhoDoRegistro))){
            String line;
            while((line = reader.readLine()) != null){
                if (line.isBlank() || line.startsWith("---")) {
                    continue;
                }

                //lógica de exibição com interface gráfica
            }

        }catch (IOException e){
            throw new IOException("FALHA AO EXIBIR O REGISTRO GERAL" , e);
        }
    }

    private void registrarNoArquivo() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoDoRegistro,true))){
            if (!arquivoRegistrador.exists() || arquivoRegistrador.length() == 0) {
                writer.write("--- EXTRATO BANCÁRIO ---");
                writer.newLine();
            }

            writer.write("Data da Transação: " + dataTransacao + "| Tipo de transacao: "+ tipoDeTransacao + //se foi saque ou deposito ou transferencia
                    "| Valor da transacao: "+ valTransacao);
            writer.newLine();

            writer.flush();
        }catch (IOException e) {
            throw new IOException("FALHA AO REGISTRAR AS TRANSAÇÕES NO BANCO!", e);
        }
    }

    public LocalDate getDataTransacao() {return dataTransacao;}
    public String getTipoDeTransacao() {return tipoDeTransacao;}
    public Double getValTransacao() {return valTransacao;}
}
