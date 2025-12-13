package Entitite.Cliente.TiposDeCliente;

import Entitite.Cliente.Cliente;

public class ClientePessoaFisica extends Cliente {

    private String data_de_nascimento;

    public ClientePessoaFisica(String nome, long cpf, String endereco, String data_de_nascimento) {
        super(nome, cpf, endereco);
        this.data_de_nascimento = data_de_nascimento;
    }

    public String getData_de_nascimento() {return data_de_nascimento;}

    @Override
    public String toString() {
        return "ClientePessoaFisica - " + super.toString() + ", data_de_nascimento= " + data_de_nascimento;
    }
}
