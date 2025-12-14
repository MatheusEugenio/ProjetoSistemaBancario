package Entitite.Cliente.TiposDeCliente;

import Entitite.Cliente.Cliente;

public class ClientePessoaJuridica extends Cliente {

    private String cnpj;
    private String razaoSocial;

    public ClientePessoaJuridica(String nome, String endereco, String cnpj, String razaoSocial) {
        super(nome, endereco);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {return cnpj;}
    public String getRazaoSocial() {return razaoSocial;}

    @Override
    public String toString() {
        return "ClientePessoaJuridica - "+super.toString() +", cnpj =" + cnpj + ", razaoSocial =" + razaoSocial;
    }
}
