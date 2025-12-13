package Entitite.Conta;

import Entitite.Cliente.Cliente;

public abstract class Conta{

    protected Integer numero;
    protected Integer agencia;
    protected Double saldo;
    protected Cliente titular;

    public Conta(Integer numero, Integer agencia, Cliente titular) {
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = 0.0;
        this.titular = titular;
        this.titular.vincularConta(this);
    }

    public abstract void depositar(double val_deposito);
    public abstract void sacar(double val_saque);
    public abstract void transferir(Conta conta, double valor_transferencia);
    public abstract double verSaldo();

    public Integer getNumero() {return numero;}
    public Integer getAgencia() {return agencia;}
    public Double getSaldo() {return saldo;}
    public Cliente getTitular() {return titular;}
}
