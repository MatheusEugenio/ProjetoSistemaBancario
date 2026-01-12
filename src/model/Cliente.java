package model;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Cliente {

    protected String nome;
    protected Endereco endereco;
    protected LocalDate dataDeNascimento;
    protected ArrayList<Conta> contas;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Cliente(String nome, Endereco endereco, String dataNascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeNascimento = LocalDate.parse(dataNascimento, formatter);
        this.contas = new ArrayList<>();
    }

    public void vincularConta(Conta conta) {
        if (conta != null && conta.getTitular() != null){

            if (!this.contas.contains(conta)){
                this.contas.add(conta);
            }
        } else{
            throw new RuntimeException("Vinculação de conta inválida!");
        }
    }

    // esse metodo deve ser revisado quando a Interface Gráfica for implementada
    public ArrayList<Conta> consultarContasVinculadas() {return this.contas;}
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
