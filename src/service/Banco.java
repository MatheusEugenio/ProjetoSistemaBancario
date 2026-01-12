package service;

import model.*;
import repository.Persistencia;
import util.Validacoes;

import java.io.IOException;
import java.util.ArrayList;
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

        this.contasDoBanco.add(novaConta);
        this.bancoDeDados.salvarConta(contasDoBanco);

        return novaConta;
    }

    public boolean removerConta(int numeroConta) {
        Conta contaParaRemover = null;
        Cliente donoDaConta = null;

        for (Cliente cliente : clientesDoBanco) {
            for (Conta conta : cliente.consultarContasVinculadas()) {
                if (conta.getNumero() == numeroConta) {
                    contaParaRemover = conta;
                    donoDaConta = cliente;
                    break;
                }
            }
            if (contaParaRemover != null) break;
        }

        if (contaParaRemover != null && donoDaConta != null) {
            donoDaConta.consultarContasVinculadas().remove(contaParaRemover);

            if (this.contasDoBanco != null) {
                this.contasDoBanco.remove(contaParaRemover);
            }

            try {
                salvarTodasAsContas();
                return true;
            } catch (Exception e) {
                System.out.println("Erro ao persistir exclusão: " + e.getMessage());
                return false;
            }
        }

        return false; // Conta não encontrada
    }

    private void salvarTodasAsContas() {
        // Cria uma lista temporária apenas com as contas que sobraram nos clientes
        List<Conta> listaAtualizada = new ArrayList<>();

        for (Cliente c : clientesDoBanco) {
            listaAtualizada.addAll(c.consultarContasVinculadas());
        }

        bancoDeDados.salvarConta(listaAtualizada);
    }

    public List<Cliente> getClientesDoBanco() {
            return clientesDoBanco;
    }
    public String getNomeDoBanco(){return nomeDoBanco;}
}
