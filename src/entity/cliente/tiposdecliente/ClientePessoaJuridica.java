package entity.cliente.tiposdecliente;

import entity.cliente.Cliente;
import entity.cliente.Endereco;

public class ClientePessoaJuridica extends Cliente {

    private String cnpj;
    private String nome_Empresa;

    public ClientePessoaJuridica(String nome, Endereco endereco, String cnpj, String nome_Empresa, String dataNascimento) {
        super(nome, endereco, dataNascimento);
        this.cnpj = cnpj;
        this.nome_Empresa = nome_Empresa;
    }

    public ClientePessoaJuridica(String nome, Endereco endereco, String cnpj, String dataNascimento) {
        super(nome, endereco, dataNascimento);
        this.cnpj = cnpj;
        this.nome_Empresa = "Não têm";
    }

    public String getCnpj() {return cnpj;}
    public String getNomeEmpresa() {return nome_Empresa;}

    @Override
    public String toString() {
        return "ClientePessoaJuridica - "+ super.toString() +", CNPJ - " + cnpj + ", Nome da Empresa atrelada ao CNPJ - " + nome_Empresa;
    }
}
