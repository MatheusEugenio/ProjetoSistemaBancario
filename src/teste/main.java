package teste;

import Entitite.Cliente.TiposDeCliente.ClientePessoaFisica;
import Entitite.Conta.Conta;
import Entitite.Conta.Personas_Da_Conta.ContaPoupanca;

public class main {
    public static void main(String[] args) {
        ClientePessoaFisica cPF = new ClientePessoaFisica("cleiton", 133, "Rua 123", "12/12/2003");
        Conta c1 = new ContaPoupanca(12, 2, cPF, 12.0);

        cPF.vincularConta(c1);
        cPF.consultarContas();
    }
}
