package service;

import model.*;
import repository.Persistencia;
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
    }

    public void mostrarClientesDoBanco(){
        System.out.println("--- Clientes cadastrados ---");
        for (int i = 0; i < clientesDoBanco.size(); i++) {
            System.out.println((i+1) + ". " + clientesDoBanco.get(i).getNome());
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
        this.bancoDeDados.salvarCliente(clientesDoBanco);
        return true;
    }

    public boolean abrirConta(Cliente cliente, String tipoConta){
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
}
