package it.unibo.TowerSiege.view.rules;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public class RulesDialog {
    
    private final JDialog dialog;

    public RulesDialog(final JFrame parent) {
        dialog = new JDialog(parent, "Regolamento", true);
        dialog.setSize(520, 420);
        dialog.setLocationRelativeTo(parent);

        final JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setFont(new Font("Serif", Font.PLAIN, 14));
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBackground(new Color(40, 35, 30));
        t.setForeground(new Color(230, 220, 200));
        t.setMargin(new Insets(15, 15, 15, 15));
        t.setText("=== REGOLE === \n\nOBIETTIVO:\nPiazza torri sui ounti di costruzione per fermare i nemici!\n\n" +
                "TORRI:\n Cristallo (50g) - Danno medio\n Cavaliere (120g) - Alto danno, lento\n Mago(80g) -Rapido fuoco\n Ghiaccio (90g) - Rallenta\n\n" +
                "NEMICI:\n Orco (standard) | Goblin (veloce, verde) | Bruto (lento, grosso)\n\n" +
                "CONTROLLI:\n Click sinistro = piazza/potenzia torre\n Click destro = vendi torre (rimborso 50%)\n W = ondata | F = fuoco (15s CD) | G = gelo (8s CD) | ESC = pausa");

        final JButton cb = new JButton("Chiudi");
        cb.addActionListener(e -> dialog.dispose());

        dialog.add(new JScrollPane(t), BorderLayout.CENTER);
        dialog.add(cb, BorderLayout.SOUTH);

    }

    public void show() {
        dialog.setVisible(true);
    }
    
}
