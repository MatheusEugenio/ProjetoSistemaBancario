package entity.cliente;

public class Endereco {
    private String rua;
    private Integer cep;
    private int numeroDaCasa;
    private String complemento;
    private String bairro;
    private String cidade;

    public Endereco(String rua, Integer cep, int numeroDaCasa, String complemento, String bairro, String cidade) {
        this.rua = rua;
        this.cep = cep;
        this.numeroDaCasa = numeroDaCasa;
        if (complemento.isBlank() || complemento == null) {this.complemento = "nenhum";
        }else {this.complemento = complemento;}
        this.bairro = bairro;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return " | Rua: " + rua +
                " | Bairro: " + bairro +
                " | Número Da Casa: " + numeroDaCasa +
                " | Cidade: "+ cidade;
    }

    public String getRua() {return rua;}
    public Integer getCep() {return cep;}
    public int getNumeroDaCasa() {return numeroDaCasa;}
    public String getComplemento() {return complemento;}
    public String getBairro() {return bairro;}
    public String getCidade() {return cidade;}
}
