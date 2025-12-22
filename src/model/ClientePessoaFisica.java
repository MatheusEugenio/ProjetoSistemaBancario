package model;

public class ClientePessoaFisica extends Cliente {

    private String cpf;

    public ClientePessoaFisica(String nome, String cpf, Endereco endereco, String data_de_nascimento) {
        super(nome, endereco, data_de_nascimento);
        this.cpf = cpf;
    }

    public String getCpf() {return cpf;}

    @Override
    public String toString() {
        return "ClientePessoaFisica: \n" + super.toString() + " | CPF - " + cpf;
    }
}
