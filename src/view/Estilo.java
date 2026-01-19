package view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Estilo {
    public static final Color COR_MENU = new Color(30, 41, 59);
    public static final Color COR_MENU_SELECAO = new Color(51, 65, 85);

    public static final Color COR_PRINCIPAL = new Color(37, 99, 235);
    public static final Color COR_HOVER = new Color(29, 78, 216);

    public static final Color COR_FUNDO_CLARO = new Color(241, 245, 249);
    public static final Color COR_TEXTO_MENU = new Color(255, 255, 255);
    public static final Color COR_TEXTO_ESCURO = new Color(15, 23, 42);

    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONTE_GERAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);
    public static JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(COR_MENU_SELECAO);
                } else {
                    g2.setColor(COR_MENU);
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(COR_TEXTO_MENU);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setBorder(new EmptyBorder(12, 25, 12, 0));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }
    public static JButton criarBotao(String texto) {
        JButton btn = new JButton(texto.toUpperCase()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(COR_HOVER);
                } else {
                    g2.setColor(COR_PRINCIPAL);
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(FONTE_BOTAO);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    public static JTextField criarInput() {
        JTextField txt = new JTextField();
        txt.setFont(FONTE_GERAL);
        txt.setForeground(COR_TEXTO_ESCURO);
        txt.setBorder(new CompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return txt;
    }
}