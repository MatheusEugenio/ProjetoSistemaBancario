package Entitite.Conta.Personas_Da_Conta;

import Entitite.Cliente.Cliente;
import Entitite.Conta.Conta;
import Entitite.InsufficientFundsException;
import Entitite.InvalidValueException;

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
    public void depositar(double val_deposito) throws InvalidValueException{
        if (val_deposito > 0) {
            saldo += val_deposito;
        } else {
            throw new InvalidValueException("Valor de depósito inválido!");//revisar
        }
    }

    @Override
    public void sacar(double val_saque) throws InvalidValueException{
        if (val_saque > 0 && val_saque <= (saldo + limiteChequeEspecial)) { //Daniel
            saldo -= val_saque;
        } else {
            throw new InvalidValueException("Saldo insuficiente para saque!");//revisar
        }
    }

    @Override
    public void transferir(Conta conta, double valor_transferencia) throws InsufficientFundsException{
        if (valor_transferencia > 0 && this.getSaldo() >= valor_transferencia) {
            this.setSaldo(this.getSaldo() - valor_transferencia);
            conta.setSaldo(conta.getSaldo() + valor_transferencia);
        }else {
            throw new InsufficientFundsException("FALHA NA TRANSFERÊNCIA, SALDO INSUFICENTE!");
        }
    }

}
