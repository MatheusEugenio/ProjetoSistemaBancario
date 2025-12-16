package Service;

import java.time.LocalDate;

public class Transacao {

    private LocalDate data;
    private String tipo_de_transacao;
    private Double valTransacao;

    public Transacao(LocalDate dt, String tipoTransacao, double val){
        this.data = dt;
        this.tipo_de_transacao = tipoTransacao;
        this.valTransacao = val;
    }

    public void registrar(){
        System.out.println("Data da Transacao: " + data);
        System.out.println("Tipo de transacao: " + tipo_de_transacao);
        System.out.printf("Valor da transacao: " + valTransacao);
    }

    public LocalDate getData() {return data;}
    public String getTipo_de_transacao() {return tipo_de_transacao;}
    public Double getValTransacao() {return valTransacao;}
}
