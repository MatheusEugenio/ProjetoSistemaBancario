package model;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Cliente {

    protected String nome;
    protected Endereco endereco;
    protected LocalDate dataDeNascimento;
    protected Set<Conta> contas;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Cliente(String nome, Endereco endereco, String dataNascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeNascimento = LocalDate.parse(dataNascimento, formatter);
        this.contas = new HashSet<>();
    }

    public void vincularConta(Conta conta) {
        if (conta != null && conta.getTitular() != null){this.contas.add(conta);}
        else{throw new RuntimeException("Vinculação de conta inválida!");}
    }

    //esse metodo vai precisar de uma lógica de entrada e saída com interfaces
    public boolean atualizarDados(String novo_nome, Endereco novo_endereco) {
        if ((novo_nome.isBlank() || novo_nome == null) && novo_endereco == null){
            return false;
        }
        this.nome = novo_nome;
        this.endereco = novo_endereco;
        return true;
    }

    // esse metodo deve ser revisado quando a Interface Gráfica for implementada
    public ArrayList<Conta> consultarContasVinculadas() {return new ArrayList<>(contas);}
    public String getNome() {return nome;}
    public String getEnderecoARQ() {return endereco.toStringARQ();}

    @Override
    public String toString() {
        return this.nome;
    }

    public String toStringARQ() {
        return nome + ";" + getEnderecoARQ() + ";" + dataDeNascimento.format(formatter);
    }
}
