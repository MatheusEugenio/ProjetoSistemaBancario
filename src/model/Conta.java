package model;

import exception.InvalidValueException;
import repository.Transacao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta{

    private static int contador = 1;//faz com que as contas sigam um padrão no NUMERO
    protected static final int agenciaDefault = 1;

    protected String tipo;
    protected Integer numero;
    protected Integer agencia;
    protected Double saldo;
    protected Cliente titular;
    protected List<Transacao> historicoDeTransacoes;

    public Conta(int agencia, Cliente titular) {
        this.numero = contador;
        contador++;

        this.agencia = agencia;
        this.saldo = 0.0;
        this.titular = titular;
        this.historicoDeTransacoes = new ArrayList<>();
        this.titular.vincularConta(this);
    }

    public Conta(Integer numero, Integer agencia, Cliente titular, Double saldo) {
        this.numero = numero;
        this.agencia = agencia;
        this.titular = titular;
        this.saldo = saldo;
        this.historicoDeTransacoes = new ArrayList<>();

        if (numero >= contador){contador = numero + 1;}
    }

    public Conta(Cliente titular){
        this.numero = contador;// Atribui o número atual
        contador++;// incrementa para o próximo

        this.agencia = agenciaDefault;
        this.titular = titular;
        this.saldo = 0.0;
        this.historicoDeTransacoes = new ArrayList<>();
        this.titular.vincularConta(this);
    }

    public boolean depositar(double val_deposito) throws IOException {
        if (val_deposito > 0) {
            saldo += val_deposito;
            Transacao transacao = new Transacao("Deposito", val_deposito);
            this.historicoDeTransacoes.add(transacao);
            transacao.registrar(this.getTitular().nome);
            return true;
        } else {
            System.out.println("O valor para depósito é inválido!");
            return false;
        }
    }

    public boolean sacar(double val_saque) throws IOException, InvalidValueException {
        if (val_saque > 0 && val_saque <= saldo && saldo >= 0) {
            saldo -= val_saque;
            Transacao transacao = new Transacao("Saque", val_saque);
            historicoDeTransacoes.add(transacao);
            transacao.registrar(this.getTitular().nome);
            return true;
        } else {
            throw new InvalidValueException("Saldo insuficiente para efetuar o saque!");
        }
    }

    public boolean transferir(Conta conta, double valor_transferencia) throws IOException, InvalidValueException {
        if (valor_transferencia > 0 && this.getSaldo() >= valor_transferencia) {

            this.setSaldo(this.getSaldo() - valor_transferencia);

            Transacao transacaoDeSaida = new Transacao("Transferencia Enviada", valor_transferencia);
            this.historicoDeTransacoes.add(transacaoDeSaida);
            transacaoDeSaida.registrar(this.getTitular().getNome());
            conta.setSaldo(conta.getSaldo() + valor_transferencia);

            Transacao transacaoRecebida = new Transacao("Transferencia Recebida", valor_transferencia);
            conta.historicoDeTransacoes.add(transacaoRecebida);
            transacaoRecebida.registrar(conta.getTitular().getNome());

            return true;
        }else {
            throw new InvalidValueException("FALHA NA TRANSFERÊNCIA, SALDO INSUFICENTE!");
        }
    }

    /// retorna uma string com o extrato completo
    public String gerarExtrato() {
        StringBuilder extrato = new StringBuilder();

        extrato.append("--- EXTRATO BANCARIO ---\n");
        extrato.append("Titular: ").append( titular.getNome()).append("\n");

        for(Transacao transacao: historicoDeTransacoes) {
            extrato.append(transacao.toString()).append("\n");
        }

        extrato.append("------------------------\n");

        return extrato.toString();
    }

    public String toStringARQ(){
        return getNumero() +";"+ getAgencia() +";"+ getTitular().getNome() +";"+ getSaldo();
    }

    public String getTipo() {return tipo;}
    public double getSaldo(){return saldo;}
    public Integer getNumero() {return numero;}
    public Integer getAgencia() {return agencia;}
    public Cliente getTitular() {return titular;}
    public void setSaldo(Double saldo) {this.saldo = saldo;}

}
