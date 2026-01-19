package view;

import service.Banco;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalGUI extends JFrame {

    private Banco banco;
    private JTabbedPane abas;

    public MenuPrincipalGUI() {
        // 1. Configurações Iniciais
        banco = new Banco("PACHECO's Bank");
        setTitle("Sistema Bancário - " + banco.getNomeDoBanco());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Instanciando os Painéis
        PainelTransacoes pTransacoes = new PainelTransacoes(banco);
        PainelVisaoGeral pVisao = new PainelVisaoGeral(banco);
        PainelHistoricoDeTransacoes pHistorico = new PainelHistoricoDeTransacoes();
        PainelContas pContas = new PainelContas(banco);

        PainelCadastro pCadastro = new PainelCadastro(banco, () -> {
            pVisao.recarregarDados();
            pTransacoes.recarregarDados();
            pContas.recarregarDados();
        });

        //Abas
        abas = new JTabbedPane();
        abas.addTab("Cadastro de Clientes", pCadastro);
        abas.addTab("Cadastrar Conta", pContas);
        abas.addTab("Transações", pTransacoes);
        abas.addTab("Visão Geral", pVisao);
        abas.addTab("Histórico", pHistorico);

        add(abas);

        abas.addChangeListener(e -> {
            Component c = abas.getSelectedComponent();
            if (c instanceof Painel) {
                ((Painel) c).recarregarDados();
            }
        });
    }
}