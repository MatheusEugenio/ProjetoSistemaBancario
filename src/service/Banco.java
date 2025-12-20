package service;

import entity.InvalidValueException;
import entity.Tratamento;
import entity.cliente.Cliente;
import entity.cliente.Endereco;
import entity.cliente.tiposdecliente.ClientePessoaFisica;
import entity.cliente.tiposdecliente.ClientePessoaJuridica;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Banco {
    private String nomeDoBanco;
    private String codigo;
    private List<Cliente> clientesDoBanco;

    public Banco(String nomeBanco, String codigo){
        this.nomeDoBanco = nomeBanco;
        this.codigo = codigo;
        this.clientesDoBanco = new ArrayList<>();
    }

    public void mostrarClientesDoBanco(){
        clientesDoBanco.forEach(System.out::println);
    }

    public boolean adicionarCliente(Scanner sc) throws InvalidValueException { // ajustar esse metodo para quando lançar uma exceção, pedir para escrever dnv
        String cnpj, dataDeNascimento, nome, nomeEmpresa;
        long cpf;
        Endereco enderecoDeCriacao;

        try {
            System.out.println("O cliente é Pessoa Física(PF) ou Pessoa Jurídica(PJ)?");
            String tipoCliente = Tratamento.validacaoDasStringsPorPadrao(sc, 2, "pf", "pj");

            System.out.println("Nome do cliente: ");
            nome = sc.nextLine();

            System.out.println("Digite a data de nascimento do cliente: ");
            dataDeNascimento = sc.nextLine(); //fazer verificação da data

            if (tipoCliente.equalsIgnoreCase("PF")){

                System.out.println("CPF do cliente: ");
                cpf = Tratamento.validacaoDosInteiros(sc,11);

                enderecoDeCriacao = leitorDeEndereco(sc);
                return this.clientesDoBanco.add(new ClientePessoaFisica(nome, cpf, enderecoDeCriacao, dataDeNascimento));

            }else if (tipoCliente.equalsIgnoreCase("PJ")){

                System.out.println("Digite o CNPJ do cliente: ");
                cnpj = sc.nextLine();

                System.out.println("Digite o nome da empresa do cliente: ");
                nomeEmpresa = sc.nextLine();

                enderecoDeCriacao = leitorDeEndereco(sc);
                return this.clientesDoBanco.add(new ClientePessoaJuridica(nome, enderecoDeCriacao,cnpj,nomeEmpresa, dataDeNascimento));

            }else {
                return false;
            }

        }catch (InputMismatchException e){
            System.out.println("ERRO, VOCÊ DIGITOU ALGO ERRADO NO CAMPO ERRADO (ex: digitou números em um campo de caracteres)! "+ e.getMessage());
            sc.nextLine();
            return false;
        }
    }

    public void abrirConta(){
        //printar os clientes que existem no banco
        // perguntar qual dos cliente deseja abrir a conta
        // resposta de S ou N
        // ver qual o tipo da conta a ser criado
        //ler os dados necessários para cria uma conta via scanner
        //(eu acho) instanciar uma conta no cliente alvo cadastrado ao banco
    }

    private static Endereco leitorDeEndereco(Scanner sc) {
        System.out.println("--- Endereço ---");

        System.out.println("Digite o CEP da cidade do cliente: ");
        long cep = Tratamento.validacaoDosInteiros(sc, 8);

        System.out.println("Digite o nome da cidade do cliente: ");
        String cidade = sc.nextLine();

        System.out.println("Digite a rua do cliente: ");
        String rua = sc.nextLine();

        System.out.println("Digite o bairro do cliente: ");
        String bairro = sc.nextLine();

        System.out.println("Digite o número da casa do cliente: ");
        int numeroDaCasa = sc.nextInt();
        sc.nextLine();

        System.out.println("Complemento: ");
        String complemento = sc.nextLine();

        return new Endereco(rua, cep,numeroDaCasa,complemento,bairro,cidade);
    }

    public String getNomeDoBanco() {return nomeDoBanco;}
    public String getCodigo() {return codigo;}
}
