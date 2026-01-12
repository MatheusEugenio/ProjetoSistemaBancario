package model;

import repository.Transacao;

import java.io.IOException;

public class ContaCorrente extends Conta {

    private Double limiteChequeEspecial;

    public ContaCorrente(Integer numero, Integer agencia, Cliente titular, double saldo) {
        super(numero, agencia, titular, saldo);
        this.limiteChequeEspecial = 0.0;
        this.tipo = "CC";
    }

    public ContaCorrente(Cliente titular) {
        super(titular);
        this.tipo = "CC";
        this.limiteChequeEspecial = 0.0;
    }

    @Override
    public String toStringARQ(){
        return tipo +";"+ super.toStringARQ();
    }

    @Override
    public boolean sacar(double val_saque) throws IOException {
        if (val_saque > 0 && (saldo + limiteChequeEspecial) >= val_saque) {
            saldo -= val_saque;
            Transacao transacao= new Transacao("Saque", val_saque);
            historicoDeTransacoes.add(transacao);
            transacao.registrar(this.getTitular().getNome());
            return true;
        } else {
            System.out.println("Saldo insuficiente para saque!");//revisar
            return false;
        }
    }

    @Override
    public String toString() {
        return "Conta Corrente: " +
                " | Titular: " + titular.getNome() +
                " | Saldo: " + this.getSaldo() +
                " | Agência: " + agencia +
                " | Número da conta: " + numero;
    }

    public void setLimiteChequeEspecial(Double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }
}
