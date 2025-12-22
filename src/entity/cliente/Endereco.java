package entity.cliente;

public class Endereco {

    private String rua, cep, complemento, bairro, cidade;
    private int numeroDaCasa;

    public Endereco(String rua, String cep, int numeroDaCasa, String complemento, String bairro, String cidade) {
        this.rua = rua;
        this.cep = cep;
        this.numeroDaCasa = numeroDaCasa;
        if (complemento.isBlank() || complemento == null) {this.complemento = "Nenhum";
        }else {this.complemento = complemento;}
        this.bairro = bairro;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Rua: " + rua +
                " | Bairro: " + bairro +
                " | Número Da Casa: " + numeroDaCasa +
                " | Cidade: "+ cidade;
    }

    public String getRua() {return rua;}
    public String getCep() {return cep;}
    public int getNumeroDaCasa() {return numeroDaCasa;}
    public String getComplemento() {return complemento;}
    public String getBairro() {return bairro;}
    public String getCidade() {return cidade;}
}
