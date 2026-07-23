package it.unibo.towersiege.view.rules;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 * Dialog that shows the game rules.
 */
public class RulesDialog {

    private static final int DIALOG_WIDTH = 520;
    private static final int DIALOG_HEIGHT = 420;
    private static final int DIALOG_FONT_SIZE = 14;
    private static final int TEXT_MARGIN = 15;
    private static final int TEXT_AREA_BG_RED = 40;
    private static final int TEXT_AREA_BG_GREEN = 35;
    private static final int TEXT_AREA_BG_BLUE = 30;
    private static final int TEXT_AREA_FG_RED = 230;
    private static final int TEXT_AREA_FG_GREEN = 220;
    private static final int TEXT_AREA_FG_BLUE = 200;

    private final JDialog dialog;

    /**
     * Creates the rules dialog.
     * 
     * @param parent parent frame
     */
    public RulesDialog(final JFrame parent) {
        dialog = new JDialog(parent, "Regolamento", true);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(parent);

        final JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setFont(new Font("Serif", Font.PLAIN, DIALOG_FONT_SIZE));
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBackground(new Color(TEXT_AREA_BG_RED, TEXT_AREA_BG_GREEN, TEXT_AREA_BG_BLUE));
        t.setForeground(new Color(TEXT_AREA_FG_RED, TEXT_AREA_FG_GREEN, TEXT_AREA_FG_BLUE));
        t.setMargin(new Insets(TEXT_MARGIN, TEXT_MARGIN, TEXT_MARGIN, TEXT_MARGIN));
        t.setText("=== REGOLE === \n\nOBIETTIVO:\n"
                + " Piazza torri sui ounti di costruzione per fermare i nemici!\n\n"
                + "TORRI:\n Cristallo (50g) - Danno medio\n"
                + " Cavaliere (120g) - Alto danno, lento\n"
                + " Mago(80g) -Rapido fuoco\n"
                + "Ghiaccio (90g) - Rallenta\n\n"
                + "NEMICI:\n Orco (standard) | Goblin (veloce, verde) | Bruto (lento, grosso)\n\n"
                + "CONTROLLI:\n Click sinistro = piazza/potenzia torre\n"
                + " Click destro = vendi torre (rimborso 50%)\n"
                + " W = ondata | F = fuoco (15s CD) | G = gelo (8s CD) | ESC = pausa");

        final JButton cb = new JButton("Chiudi");
        cb.addActionListener(e -> dialog.dispose());

        dialog.add(new JScrollPane(t), BorderLayout.CENTER);
        dialog.add(cb, BorderLayout.SOUTH);

    }

    /**
     * Shows the dialog.
     */
    public void show() {
        dialog.setVisible(true);
    }
 
}
