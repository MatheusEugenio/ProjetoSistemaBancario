package Entitite.Conta.Personas_Da_Conta;

import Entitite.Cliente.Cliente;
import Entitite.Conta.Conta;

public class ContaPoupanca extends Conta implements Tributavel {

    private Double taxaRendimento;

    public ContaPoupanca(Integer numero, Integer agencia, Double saldo, Cliente titular, double taxa) {
        super(numero, agencia, saldo, titular);
        this.taxaRendimento = taxa;
    }

    public void aplicarRendimento(){

    }

    @Override
    public double calcularImposto() { // DA INTERFACE
        return 0;
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
