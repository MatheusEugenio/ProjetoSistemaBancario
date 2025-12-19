package entity.conta;

import entity.cliente.Cliente;
import entity.InsufficientFundsException;
import entity.InvalidValueException;

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

    public abstract void depositar(double val_deposito) throws InvalidValueException;
    public abstract void sacar(double val_saque) throws InvalidValueException;
    public abstract void transferir(Conta conta, double valor_transferencia) throws InsufficientFundsException;
    public double verSaldo(){System.out.println(saldo);return saldo;}

    public Integer getNumero() {return numero;}
    public Integer getAgencia() {return agencia;}
    public Double getSaldo() {return saldo;}
    public Cliente getTitular() {return titular;}
    public void setSaldo(Double saldo) {this.saldo = saldo;}

}
