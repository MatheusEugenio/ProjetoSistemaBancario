package model;

public class ClientePessoaJuridica extends Cliente {

    private String cnpj;
    private String nome_Empresa;
    private final String tipo = "PJ";

    public ClientePessoaJuridica(String nome, Endereco endereco, String cnpj, String nome_Empresa, String dataNascimento) {
        super(nome, endereco, dataNascimento);
        this.cnpj = cnpj;
        if (nome_Empresa.isBlank() || nome_Empresa == null) this.nome_Empresa = "Não tem";
        else this.nome_Empresa = nome_Empresa;
    }

    public String getCnpj() {return cnpj;}

    @Override
    public String toStringARQ() {
        return tipo + ";" + super.toStringARQ() + ";" + getCnpj() + ";" + nome_Empresa;
    }

    @Override
    public String toString() {
        return "Pessoa Juridica: \n"+ super.toString() +"\n | CNPJ: " + cnpj + " | Nome da Empresa atrelada ao CNPJ: " + nome_Empresa;
    }
}
