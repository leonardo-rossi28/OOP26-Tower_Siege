package it.unibo.TowerSiege.view.pausemenu;

import it.unibo.TowerSiege.controller.gamecontroller.api.GameController;
import it.unibo.TowerSiege.controller.maincontroller.api.MainController;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
/**
 * Dialog shown when the game is paused
 */

public class PauseMenuDialog {
    
    private final static int DIALOG_WIDTH=300;
    private final static int DIALOG_HEIGHT=350;
    private final static int TITLE_SIZE=36;
    private final static int SPACING_TOP=30;
    private final static int SPACING_MIDDLE=40;
    private final static int BUTTON_WIDTH=200;
    private final static int SPACING_SMALL=15;
    private final static int BUTTON_HEIGHT=40;
    private final static int BUTTON_FONT_SIZE=14;

    private final JDialog dialog;
    /**
     * Creates the pause dialog.
     * 
     * @param parentFrame parent frame
     * @param mc main controller
     * @param gc game controller
     */

    public PauseMenuDialog(JFrame parentFrame, final MainController mc, final GameController gc){
        dialog = new JDialog(parentFrame, "Pausa", true);
        dialog.setUndecorated(true);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(parentFrame);


        final JPanel p = new JPanel();
        p.setBackground(new Color(20, 20, 20, 240));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        
        final JLabel title = new JLabel("PAUSA");
        title.setFont(new Font("Serif", Font.BOLD, TITLE_SIZE));
        title.setForeground(new Color(255, 255, 0));
        title.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        final JButton btnResume = createBtn("RIPRENDI", new Color(40, 160, 60));
        btnResume.addActionListener(e -> gc.togglePause());

        final JButton btnRestart = createBtn("RIAVVIA LIVELLO", new Color(180, 100, 30));
        btnRestart.addActionListener(e -> gc.restartGame());

        final JButton btnMenu = createBtn("TORNA AL MENU", new Color(180, 40, 40));
        btnMenu.addActionListener(e -> mc.backToMenu());

        p.add(Box.createRigidArea(new Dimension(0, SPACING_TOP)));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, SPACING_MIDDLE)));
        p.add(btnResume);
        p.add(Box.createRigidArea(new Dimension(0, SPACING_SMALL)));
        p.add(btnRestart);
        p.add(Box.createRigidArea(new Dimension(0, SPACING_SMALL)));
        p.add(btnMenu);

        dialog.add(p);
    }

    private JButton createBtn(final String text, final Color bg) {
        final JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return b;
    }

    /**
     * Shhows the dialog.
     */
    public void show(){
        dialog.setVisible(true);
    }

    /**
     * Hides the dialog.
     */
    public void hide(){
        dialog.dispose();
    }
}
