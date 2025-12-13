package Entitite.Conta.Personas_Da_Conta;

import Entitite.Cliente.Cliente;
import Entitite.Conta.Conta;

public class ContaCorrente extends Conta {

    private Double limiteChequeEspecial;

    public ContaCorrente(Integer numero, Integer agencia, Cliente titular, double limite) {
        super(numero, agencia, titular);
        this.limiteChequeEspecial = limite;
    }

    public Double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    @Override
    public void depositar(double val_deposito) {

    }

    @Override
    public void sacar(double val_saque) {

    }

    @Override
    public double verSaldo() {
        return super.saldo;
    }

    @Override
    public void transferir(Conta conta, double valor_transferencia) {

    }

}
