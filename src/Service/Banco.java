package Service;

import Entitite.Cliente.Cliente;
import Entitite.Cliente.Endereco;
import Entitite.Cliente.TiposDeCliente.ClientePessoaFisica;
import Entitite.Cliente.TiposDeCliente.ClientePessoaJuridica;
import Entitite.InvalidValueException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Banco {
    private String nome_do_Banco;
    private String codigo;
    private List<Cliente> clientesDoBanco;

    public Banco(String nomeBanco, String codigo){
        this.nome_do_Banco = nomeBanco;
        this.codigo = codigo;
        this.clientesDoBanco = new ArrayList<Cliente>();
    }

    public boolean adicionarCliente(Scanner sc) throws InputMismatchException{
        String cpf;
        String cnpj;
        String dataNascimento;
        String nome;
        String nomeEmpresa;
        int cep = 0;
        String cidade;
        String rua;
        String bairro;
        String numeroDaCasa;
        String complemento;
        String dataDeNascimento;
        Endereco enderecoDeCriacao;
        boolean verificacao = false;

        try {
            System.out.println("O cliente é Pessoa Física(PF) ou Pessoa Jurídica(PJ)?");
            String tipoCliente = sc.next();
            if (tipoCliente.equalsIgnoreCase("PF")){
                System.out.println("Digite o seu nome: ");
                nome = sc.next();
                System.out.println("Digite o seu CPF: ");
                cpf = sc.next();
                System.out.println("O seu endereço ");
                System.out.println("Digite o CEP da sua cidade: ");
                cep = sc.nextInt();
                System.out.println("Digite a sua cidade: ");
                cidade = sc.next();
                System.out.println("Digite a sua rua: ");
                rua = sc.next();
                System.out.println("Digite o seu bairro: ");
                bairro = sc.next();
                System.out.println("Digite o número da sua casa: ");
                numeroDaCasa = sc.next();
                System.out.println("Complemento: ");
                complemento = sc.next();
                System.out.println("Digite a sua data de nascimento: ");
                dataDeNascimento = sc.next();

                enderecoDeCriacao = new Endereco(rua, cep,numeroDaCasa,complemento,bairro,cidade);
                verificacao = this.clientesDoBanco.add(new ClientePessoaFisica(nome, cpf, enderecoDeCriacao, dataDeNascimento));

            }else if (tipoCliente.equalsIgnoreCase("PJ")){
                System.out.println("Digite o seu nome: ");
                nome = sc.next();
                System.out.println("Digite o seu CNPJ: ");
                cnpj = sc.next();
                System.out.println("Digite o nome da empresa: ");
                nomeEmpresa = sc.next();
                System.out.println("O seu endereço ");
                System.out.println("Digite o CEP da sua cidade: ");
                 cep = sc.nextInt();
                System.out.println("Digite a sua cidade: ");
                cidade = sc.next();
                System.out.println("Digite a sua rua: ");
                 rua = sc.next();
                System.out.println("Digite o seu bairro: ");
                bairro = sc.next();
                System.out.println("Digite o número da sua casa: ");
                numeroDaCasa = sc.next();
                System.out.println("Complemento: ");
                complemento = sc.next();
                System.out.println("Digite a sua data de nascimento: ");
                dataDeNascimento = sc.next();

                enderecoDeCriacao = new Endereco(rua, cep,numeroDaCasa,complemento,bairro,cidade);
                verificacao = this.clientesDoBanco.add(new ClientePessoaJuridica(nome, enderecoDeCriacao,cnpj,nomeEmpresa, dataDeNascimento));
            }

            if (verificacao) {
                System.out.println("Cliente adicionado com sucesso!");
                return true;
            }else {
                System.out.println("Cliente não foi adicionado!");
                return false;
            }

        }catch (InputMismatchException e){
            throw new InputMismatchException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void abrirConta(){

    }

    public String getNomeDoBanco() {return nome_do_Banco;}
    public String getCodigo() {return codigo;}
}
