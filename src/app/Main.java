package app;

import exception.InvalidValueException;
import service.Banco;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new  Scanner(System.in);
        Banco banco = new Banco("Banco do Brasil" , "12343");

        try {

            System.out.println("Quantos clientes deseja cadastrar?");
            int quant = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < quant; i++) {
                banco.adicionarCliente(sc);
            }

            banco.mostrarClientesDoBanco();
        }catch (InvalidValueException e){
            System.out.println("ERRO: "+e.getMessage());
        }

//        ClientePessoaFisica cPF = new ClientePessoaFisica("cleiton", 133, "Rua 123", "12/12/2003");
//        Conta c1 = new ContaPoupanca(12, 2, cPF, 12.0);
//
//        cPF.vincularConta(c1);
//        cPF.consultarContas();
    }
}
