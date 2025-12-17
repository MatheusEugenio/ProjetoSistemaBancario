package Entitite.Cliente;

import Entitite.Conta.Conta;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public abstract class Cliente {

    private String nome;
    private String endereco;
    private LocalDate dataDeNascimento;
    private Set<Conta> contas = new HashSet<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Cliente(String nome, String endereco, String dataNascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeNascimento = LocalDate.parse(dataNascimento, formatter);
    }

    public void vincularConta(Conta conta) {
        if (conta != null && conta.getTitular() != null){this.contas.add(conta);}
    }

    public void atualizarDados(String novo_nome, String novo_endereco) {// falta a integração da interface gráfica
        this.nome = novo_nome;
        this.endereco = novo_endereco;
    }

    // ESSE METODO RETORNA TODAS AS CONTAS LIGADAS A ESSE CLIENTE
    public String consultarContas() {
        return "";//é preciso iterar sobre uma lista de clientes
    }

    public String getNome() {return nome;}
    public String getEndereco() {return endereco;}

    @Override
    public String toString() {
        return "Nome do cliente -" + nome +
                ", endereco -" + endereco +
                ", dataDeNascimento -" + dataDeNascimento +
                ", contas -" + contas;
    }
}
