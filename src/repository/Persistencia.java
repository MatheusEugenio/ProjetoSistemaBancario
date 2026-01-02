package repository;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    private static final String arq_clientes = "dados/arqClientes.txt";
    private static final String arq_contas = "dados/arqContas.txt";

    public boolean salvarCliente(List<Cliente> cliente){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arq_clientes, false))){

            for (Cliente c : cliente){
                writer.write(c.toStringARQ());
                writer.newLine();
            }

            return true;

        }catch (IOException e){
            System.out.println("Erro ao salvar os clientes: "+e.getMessage());
            return false;
        }
    }

    public boolean salvarConta(List<Conta> contas){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arq_contas, false))){

            for (Conta c : contas){
                writer.write(c.toStringARQ());
                writer.newLine();
            }

            return true;

        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Cliente> carregarClientes(){
        List<Cliente> novosClientes = new ArrayList<>();
        File file = new File(arq_clientes);

        if (!file.exists()){return novosClientes;}

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isBlank()) {
                    continue;
                }

                Cliente novoCliente = parseCliente(linha);
                if (novoCliente != null){novosClientes.add(novoCliente);}
            }
        }catch (IOException e){
            System.out.println("Erro ao carregar os clientes: "+e.getMessage());
        }
        return novosClientes;
    }

    public List<Conta> carregarContas(List<Cliente> listaDeClientes){
        List<Conta> novaListaDeContas = new ArrayList<>();
        File file = new File(arq_contas);

        if (!file.exists()){return novaListaDeContas;}

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isBlank()) {
                    continue;
                }

                Conta conta = parseConta(linha, listaDeClientes);
                if (conta != null){novaListaDeContas.add(conta);}
            }
        }catch (IOException e){
            System.out.println("Erro ao carregar os clientes: "+e.getMessage());
        }
        return novaListaDeContas;

    }

    private Cliente parseCliente(String linha){
        String[] partes = linha.split(";");
        String tipo = partes[0];

        if (tipo.equalsIgnoreCase("PF")) {
            String nome = partes[1];
            String cep = partes[2];
            String rua = partes[3];
            String bairro = partes[4];
            int numeroDaCasa = Integer.parseInt(partes[5]);
            String cidade = partes[6];
            String complemento = partes[7];
            String dataDeNascimento = partes[8];
            String cpf = partes[9];

            Endereco endereco = new Endereco(rua, cep, numeroDaCasa, complemento, bairro, cidade);

            return new ClientePessoaFisica(nome, cpf, endereco, dataDeNascimento);
        } else if (tipo.equalsIgnoreCase("PJ")) {
            String nome = partes[1];
            String cep = partes[2];
            String rua = partes[3];
            String bairro = partes[4];
            int numeroDaCasa = Integer.parseInt(partes[5]);
            String cidade = partes[6];
            String complemento = partes[7];
            String dataDeNascimento = partes[8];
            String cnpj = partes[9];
            String nomeEmpresa = partes[10];

            Endereco endereco = new Endereco(rua, cep, numeroDaCasa, complemento, bairro, cidade);

            return new ClientePessoaJuridica(nome, endereco, cnpj, nomeEmpresa, dataDeNascimento);
        }
        return null;
    }

    private Conta parseConta(String linha, List<Cliente> clientes){
        String[] partes = linha.split(";");
        String tipo = partes[0];

        String nome = partes[3];
        Cliente clienteTitular = null;
        for (Cliente cliente : clientes) {
            if (nome.equalsIgnoreCase(cliente.getNome())) {
                clienteTitular = cliente;
                break;
            }
        }

        if (clienteTitular == null){return null;}

            if (tipo.equalsIgnoreCase("CC")) {
                return new ContaCorrente(Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), clienteTitular,  Double.parseDouble(partes[4]));
            } else {
                return new ContaPoupanca(Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), clienteTitular,  Double.parseDouble(partes[4]));
            }
        }
}
