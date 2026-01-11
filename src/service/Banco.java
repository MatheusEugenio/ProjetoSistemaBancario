package service;

import model.*;
import repository.Persistencia;
import util.Validacoes;

import java.io.IOException;
import java.util.List;

public class Banco {

    private String nomeDoBanco;
    private List<Cliente> clientesDoBanco;
    private List<Conta> contasDoBanco;
    private Persistencia bancoDeDados;

    public Banco(String nomeBanco){
        this.bancoDeDados = new Persistencia();
        this.nomeDoBanco = nomeBanco;
        this.clientesDoBanco = bancoDeDados.carregarClientes();
        this.contasDoBanco = bancoDeDados.carregarContas(clientesDoBanco);
        vincularClientesComConta();
    }

    private void vincularClientesComConta(){
        for (Conta conta : contasDoBanco) {

            String nomeTitular = conta.getTitular().getNome();

            Cliente dono = clientesDoBanco.stream()
                    .filter(cliente -> cliente.getNome().equalsIgnoreCase(nomeTitular))
                    .findFirst()
                    .orElse(null);

            if (dono != null){
                dono.vincularConta(conta);
            }
        }
    }

    public void mostrarClientesComContasVinculadas(){
        System.out.println("\n--- Clientes cadastrados ---");
        for (int i = 0; i < clientesDoBanco.size(); i++) {

            System.out.print((i+1) + ". " + clientesDoBanco.get(i).getNome());

            int quantContas = clientesDoBanco.get(i).consultarContasVinculadas().size();

            if (quantContas > 0) { // printa o cliente que já possui conta, pelo menos uma conta vinculada
                System.out.print(" - ");

                for (int j = 0; j < quantContas; j++) {
                    System.out.print(clientesDoBanco.get(i).consultarContasVinculadas().get(j).getTipo());

                    if (j < quantContas - 1) {
                        System.out.print(" & ");
                    }
                }
            }

            System.out.print("\n");
        }
    }

    //pessoa física
    public boolean adicionarCliente(String nome, String cpf, Endereco endereco, String dataNascimento) {
        if (nome == null || cpf == null || endereco == null || dataNascimento == null){return false;}

        Cliente novoCliente = new ClientePessoaFisica(nome, cpf, endereco, dataNascimento);
        this.clientesDoBanco.add(novoCliente);
        this.bancoDeDados.salvarCliente(clientesDoBanco);
        return true;
    }

    //pessoa jurídica
    public boolean adicionarCliente(String nome, String cnpj, Endereco endereco, String dataNascimento, String nomeEmpresa){
        if (nome == null || cnpj == null || endereco == null || dataNascimento == null || nomeEmpresa == null){return false;}

        Cliente novoCliente = new ClientePessoaJuridica(nome, endereco,cnpj,nomeEmpresa, dataNascimento);
        this.clientesDoBanco.add(novoCliente);
        this.bancoDeDados.salvarCliente(clientesDoBanco);
        return true;
    }

    public boolean abrirConta(Cliente cliente, String tipoConta) throws IllegalArgumentException{
        if (cliente == null){throw new IllegalArgumentException("Cliente não pode ser nulo.");}

        Conta novaConta;

        if (tipoConta.equalsIgnoreCase("CC")){ novaConta = new ContaCorrente(cliente);
        } else if (tipoConta.equalsIgnoreCase("CP")) { novaConta = new ContaPoupanca(cliente);
        }else {return false;}

        cliente.vincularConta(novaConta);
        this.contasDoBanco.add(novaConta);
        this.bancoDeDados.salvarConta(contasDoBanco);
        return true;
    }

    public Conta abrirConta(Cliente cliente, String tipoConta, double depositoInicial) throws IllegalArgumentException, IOException {
        if (cliente == null){throw new IllegalArgumentException("Cliente não pode ser nulo.");}

        Conta novaConta;

        if (Validacoes.validaTipoDaContaExistente(cliente, tipoConta)){
            throw new IllegalArgumentException("ERRO: "+ cliente.getNome() +" já possui uma conta do tipo \"" + tipoConta + "\"");
        }

        if (tipoConta.equalsIgnoreCase("CC")){
            novaConta = new ContaCorrente(cliente);
        } else if (tipoConta.equalsIgnoreCase("CP")) {
            novaConta = new ContaPoupanca(cliente);
        }else {
            throw new IllegalArgumentException("Tipo de conta inválido: " + tipoConta);
        }
        novaConta.depositar(depositoInicial);

        cliente.vincularConta(novaConta);
        this.contasDoBanco.add(novaConta);
        this.bancoDeDados.salvarConta(contasDoBanco);

        return novaConta;
    }

    public List<Cliente> getClientesDoBanco() {
            return clientesDoBanco;
    }
    public String getNomeDoBanco(){return nomeDoBanco;}
}
