package app;

import model.Cliente;
import model.Conta;
import model.Endereco;
import service.Banco;
import util.Validacoes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        /// O QUE FALTA?
        ///FAZER A MAIN (QUE SERIA ONDE A APLICAÇÃO VAI RODAR),
        /// TESTAR TODAS AS FUNCIONALIDADES - MÉTODOS, PERSISTENCIAS... ,
        /// IMPLEMENTAR A INTERFACE GRÁFICA

        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco("PACHECO's Bank");

        try { //cadastro dos clientes
            while (true) {
                try {
                    System.out.println("Quantos clientes deseja cadastrar?");
                    int quant = sc.nextInt();
                    sc.nextLine();

                    if (quant < 0) {
                        throw new InputMismatchException("Número não pode ser negativo!");
                    }

                    boolean verificacao;
                    for (int i = 0; i < quant; i++) {
                        verificacao = adicaoDeCliente(sc, banco);
                        if (verificacao) {
                            System.out.println("Cadastro de clientes realizado com sucesso!");
                        }
                    }

                    break;

                } catch (InputMismatchException e) {
                    System.out.println("ERRO, DIGITOU CARACTERES NO LOCAL ERRADO! " + e.getMessage());
                    System.out.println("Por favor, tente novamente.");
                    sc.nextLine();
                }
            }

        }catch (Exception e){
            System.out.println("ERRO GERAL, "+e.getMessage());
        }

        try{ // abrir contas

            //banco.mostrarClientesDoBanco();
            banco.mostrarClientesComContasVinculadas();
            System.out.println("Qual dos clientes deseja abrir uma conta?");

            boolean verificacao = abrirConta(sc, banco);

            if (verificacao){System.out.println("Conta aberta com sucesso!");}

        }catch (Exception e){
            System.out.println("ERRO INESPERADO: "+e.getMessage());
        }
    }

    public static boolean abrirConta(Scanner sc, Banco banco) {
        try {
            int posicaoDoCLiente = sc.nextInt() - 1;
            sc.nextLine();

            if (posicaoDoCLiente < 0 || posicaoDoCLiente >= banco.getClientesDoBanco().size()) {
                throw new IndexOutOfBoundsException("CLIENTE NÃO ENCONTRADO, ESCOLHA UM CLIENTE VÁLIDO!");
            }

            Cliente clienteReference = banco.getClientesDoBanco().get(posicaoDoCLiente);

            while (true) {
                try {
                    System.out.println("Qual o tipo de conta que "+clienteReference.getNome()+" deseja abrir?\n-Conta Poupança (CP)\n-Conta Corrente (CC)");
                    String tipoConta = sc.nextLine();

                    if (tipoConta.equalsIgnoreCase("CP") || tipoConta.equalsIgnoreCase("CC")) {

                        for (Conta contaExistente : clienteReference.consultarContasVinculadas()) {
                            if (contaExistente.getTipo().equalsIgnoreCase(tipoConta)) {
                                throw new IllegalArgumentException("ERRO, O CLIENTE JÁ POSSUI UMA CONTA DO TIPO \"" + tipoConta.toUpperCase() + "\" VINCULADA!");
                            }
                        }

                        return banco.abrirConta(clienteReference, tipoConta);
                    } else {
                        throw new IllegalArgumentException("TIPO DE CONTA \"" + tipoConta + "\" NÃO VÁLIDO!");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("ERRO, "+e.getMessage());
                    System.out.println("Por favor, tente novamente.");
                }
            }

        }catch (InputMismatchException e) {
            System.out.println("ERRO, VOCÊ DIGITOU CARACTERES INVÁLIDOS, DIGITE APENAS NÚMEROS!");
            sc.nextLine();
            return false;
        }catch (IndexOutOfBoundsException e) {
            System.out.println("ERRO, " + e.getMessage());
            return false;
        }
    }



    public static boolean adicaoDeCliente(Scanner sc, Banco banco) {
        String cnpj, dataDeNascimento, nome, nomeEmpresa, cpf;
        Endereco enderecoDeCriacao;

        try {
            System.out.println("O cliente é Pessoa Física(PF) ou Pessoa Jurídica(PJ)?");
            String tipoCliente = Validacoes.validacaoDasStringsPorPadrao(sc, 2, "pf", "pj");

            System.out.println("Nome do cliente: ");
            nome = sc.nextLine();

            System.out.println("Digite a data de nascimento do cliente: ");
            dataDeNascimento = Validacoes.validacaoDasDatas(sc);

            if (tipoCliente.equalsIgnoreCase("PF")){

                System.out.println("CPF do cliente: ");
                cpf = Validacoes.validacaoDasStrings(sc,11);

                enderecoDeCriacao = leitorDeEndereco(sc);
                banco.adicionarCliente(nome, cpf, enderecoDeCriacao, dataDeNascimento);

                return true;

            }else if (tipoCliente.equalsIgnoreCase("PJ")){

                System.out.println("Digite o CNPJ do cliente: ");
                cnpj = sc.nextLine();

                System.out.println("Digite o nome da empresa do cliente: ");
                nomeEmpresa = sc.nextLine();

                enderecoDeCriacao = leitorDeEndereco(sc);
                banco.adicionarCliente(nome, cnpj, enderecoDeCriacao, dataDeNascimento,  nomeEmpresa);

                return true;

            }else {return false;}

        }catch (InputMismatchException e){
            System.out.println("ERRO, VOCÊ DIGITOU ALGO ERRADO NO CAMPO ERRADO (ex: digitou números em um campo de caracteres)! "+ e.getMessage());
            sc.nextLine();
            return false;
        }
    }

    public static Endereco leitorDeEndereco(Scanner sc) {
        System.out.println("--- Endereço ---");

        System.out.println("Digite o CEP da cidade do cliente: ");
        String cep = Validacoes.validacaoDasStrings(sc, 8);

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
}
