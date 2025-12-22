package model;

import exception.InsufficientFundsException;
import exception.InvalidValueException;
import service.Transacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta{

    protected Integer numero;
    protected Integer agencia;
    protected Double saldo;
    protected Cliente titular;
    protected List<Transacao> historico;

    public Conta(int numero, int agencia, Cliente titular) {
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = 0.0;
        this.titular = titular;
        this.titular.vincularConta(this);
        this.historico = new ArrayList<>();
    }

    public void depositar(double val_deposito) throws InvalidValueException, IOException {//
        if (val_deposito > 0) {
            saldo += val_deposito;
            Transacao t = new Transacao("Deposito", val_deposito);
            this.historico.add(t);
            t.registrar();
        } else {
            throw new InvalidValueException("Valor de depósito inválido!");//revisar
        }
    }

    public void sacar(double val_saque) throws InvalidValueException, IOException { //
        if (val_saque > 0 && val_saque <= saldo && saldo >= 0) { //Daniel
            saldo -= val_saque;
            Transacao t = new Transacao("Saque", val_saque);
            historico.add(t);
            t.registrar();
        } else {
            throw new InvalidValueException("Saldo insuficiente para saque!");//revisar
        }
    }

    public void transferir(Conta conta, double valor_transferencia) throws InsufficientFundsException, InvalidValueException, IOException {
        if (valor_transferencia > 0 && this.getSaldo() >= valor_transferencia) {
            this.setSaldo(this.getSaldo() - valor_transferencia);
            conta.depositar(valor_transferencia);
            Transacao t = new Transacao("Transferencia", valor_transferencia);
            this.historico.add(t);
            t.registrar();
        }else {
            throw new InsufficientFundsException("FALHA NA TRANSFERÊNCIA, SALDO INSUFICENTE!");
        }
    }

    public String gerarExtrato() throws IOException {
        StringBuilder extrato = new StringBuilder();

        extrato.append("--- EXTRATO BANCARIO ---\n");
        extrato.append("Titular: ").append( titular.getNome()).append("\n");

        for(Transacao transacao: historico) {
            extrato.append(transacao.toString()).append("\n");
        }

        extrato.append("------------------------\n");

        return extrato.toString();
    }

    public double verSaldo(){return saldo;}
    public Integer getNumero() {return numero;}
    public Integer getAgencia() {return agencia;}
    public Double getSaldo() {return saldo;}
    public Cliente getTitular() {return titular;}
    public void setSaldo(Double saldo) {this.saldo = saldo;}

}
