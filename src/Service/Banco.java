package Service;

public class Banco {
    private String nome_do_Banco;
    private String codigo;

    public Banco(String nomeBanco, String codigo){
        this.nome_do_Banco = nomeBanco;
        this.codigo = codigo;
    }

    public void adicionarCliente(){

    }

    public void abrirConta(){

    }

    public String getNomeDoBanco() {
        return nome_do_Banco;
    }

    public String getCodigo() {
        return codigo;
    }
}
