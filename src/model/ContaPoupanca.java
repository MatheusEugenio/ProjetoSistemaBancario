package model;

import repository.Transacao;
import java.io.IOException;

public class ContaPoupanca extends Conta implements Tributavel {

    private static final Double taxaRendimento = 0.005;
    private static final String tipo = "CP";

    public ContaPoupanca(Integer numero, Integer agencia, Cliente titular, Double saldo) {
        super(numero, agencia, titular, saldo);
    }

    public ContaPoupanca(Cliente titular){
        super(titular);
    }

    public void calcularRendimento() throws IOException {
        if (this.saldo > 0) {
            double valorDoRendimento = this.saldo * taxaRendimento;
            this.saldo += valorDoRendimento;

            Transacao transacao = new Transacao("Rendimento", valorDoRendimento);
            this.historicoDeTransacoes.add(transacao);
            transacao.registrar();
        }
    }

    @Override
    public String toStringARQ(){
        return tipo +";"+ super.toStringARQ();
    }

    @Override
    public double calcularImposto() { return taxaRendimento * saldo;}

    @Override
    public String toString() {
        return "Conta Poupanca: " +
                "\nTitular - " + titular.getNome() +
                "\nSaldo - " + saldo +
                "\nAgência - " + agencia +
                "\nNúmero da conta - " + numero;
    }
}
