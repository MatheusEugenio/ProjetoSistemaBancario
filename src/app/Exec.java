package app;
import service.Banco;
import view.TelaLogin;
import javax.swing.*;

public class Exec {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível carregar o LookAndFeel do sistema.");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Banco meuBanco = new Banco("Banco Tech");
                TelaLogin login = new TelaLogin();
                login.setVisible(true);
            }
        });
    }
}
