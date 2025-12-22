package model;

import exception.InsufficientFundsException;
import exception.InvalidValueException;

public class ContaPoupanca extends Conta implements Tributavel {

    private Double taxaRendimento;

    public ContaPoupanca(Integer numero, Integer agencia, Cliente titular, double taxa) {
        super(numero, agencia, titular);
        this.taxaRendimento = taxa;
    }

    public ContaPoupanca(Integer numero, Integer agencia, Cliente titular) {
        super(numero, agencia, titular);
        this.taxaRendimento = 1.5;
    }

    public void calcularRendimento(){
        if (this.taxaRendimento != null && this.saldo > 0) {
            double valorDoRendimento = this.saldo * this.taxaRendimento;
            this.saldo += valorDoRendimento;
        }
    }

    @Override
    public double calcularImposto() { // DA INTERFACE
        return 0;
    }

    @Override
    public void depositar(double val_deposito) throws InvalidValueException {//deve implementar transação
        if (val_deposito > 0) {
            saldo += val_deposito;
        } else {
            throw new InvalidValueException("Valor de depósito inválido!");//revisar
        }
    }

    @Override
    public void sacar(double val_saque) throws InvalidValueException {//deve implementar transação
        if (val_saque > 0 && val_saque <= saldo) {
            saldo -= val_saque;
        }else {
            throw new InvalidValueException("Saldo insuficiente para saque!");//revisar
        }
    }

    @Override
    public void transferir(Conta conta, double valor_transferencia) throws InsufficientFundsException { //deve implementar transação
        if (valor_transferencia > 0 && this.getSaldo() >= valor_transferencia) {
            this.setSaldo(this.getSaldo() - valor_transferencia);
            conta.setSaldo(conta.getSaldo() + valor_transferencia);
        }else {
            throw new InsufficientFundsException("FALHA NA TRANSFERÊNCIA, SALDO INSUFICENTE!");
        }
    }

    @Override
    public String toString() {
        return "Conta Poupanca: " +
                "Titular = " + titular.getNome() +
                " | Saldo = " + saldo +
                " | Agência = " + agencia +
                " | Número da conta = " + numero;
    }
}
