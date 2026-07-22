package it.unibo.towersiege.view.pausemenu;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towersiege.controller.gamecontroller.api.GameController;
import it.unibo.towersiege.controller.maincontroller.api.MainController;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * Dialog shown when the game is paused
 */
public class PauseMenuDialog {
    
    private final static int DIALOG_WIDTH = 340;
    private final static int DIALOG_HEIGHT = 530;
    private final static int TITLE_SIZE = 36;
    private final static int SPACING_TOP = 15;
    private final static int SPACING_MIDDLE = 20;
    private final static int BUTTON_WIDTH = 200;
    private final static int SPACING_SMALL = 15;
    private final static int BUTTON_HEIGHT = 40;
    private final static int BUTTON_FONT_SIZE = 14;
    private final static int LOGO_WIDTH = 300;

    private final JDialog dialog;

    /**
     * Creates the pause dialog.
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
        
        //Load and display the TowerSiege logo image
        final JLabel logoLabel = loadLogoLabel();
        if (logoLabel != null) {
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            p.add(Box.createRigidArea(new Dimension(0, SPACING_TOP)));
            p.add(logoLabel);
        }

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

    private JLabel loadLogoLabel() {
        try (InputStream is = getClass().getResourceAsStream("/images/towersiege_logo.jpg")) {
            if(is != null) {
                final Image original = ImageIO.read(is);
                final int origWidth = original.getWidth(null);
                final int origHeight = original.getHeight(null);
                final int scaledHeight = (int) ((double) LOGO_WIDTH / origWidth * origHeight);
                final Image scaled = original.getScaledInstance(LOGO_WIDTH, scaledHeight, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(scaled));
            }
        } catch (final IOException e) {
            //Image not found, skip logo display
        }
        return null;
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
