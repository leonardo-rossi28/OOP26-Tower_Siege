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
 * Dialog shown when the game is paused.
 */
public final class PauseMenuDialog {

    private static final int DIALOG_WIDTH = 340;
    private static final int DIALOG_HEIGHT = 530;
    private static final int TITLE_SIZE = 36;
    private static final int SPACING_TOP = 15;
    private static final int SPACING_MIDDLE = 20;
    private static final int BUTTON_WIDTH = 200;
    private static final int SPACING_SMALL = 15;
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final int LOGO_WIDTH = 300;
    private static final int BG_DARK = 20;
    private static final int BG_ALPHA = 240;
    private static final int BORDER_GRAY = 150;
    private static final int BORDER_WIDTH = 2;
    private static final int BTN_RESUME_G = 160;
    private static final int BTN_RESUME_B = 60;
    private static final int BTN_RESTART_R = 180;
    private static final int BTN_RESTART_G = 100;
    private static final int BTN_RESTART_B = 30;
    private static final int BTN_MENU_R = 180;
    private static final int BTN_MENU_G = 40;
    private static final int BTN_MENU_B = 40;
    private static final int GOLD_R = 255;
    private static final int GOLD_G = 215;

    private final JDialog dialog;

    /**
     * Creates the pause dialog.
     * 
     * @param parentFrame parent frame
     * @param mc          main controller
     * @param gc          game controller
     */
    public PauseMenuDialog(final JFrame parentFrame, final MainController mc, final GameController gc) {
        dialog = new JDialog(parentFrame, "Pausa", true);
        dialog.setUndecorated(true);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(parentFrame);

        final JPanel p = new JPanel();
        p.setBackground(new Color(BG_DARK, BG_DARK, BG_DARK, BG_ALPHA));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createLineBorder(
                new Color(BORDER_GRAY, BORDER_GRAY, BORDER_GRAY), BORDER_WIDTH));

        // Load and display the TowerSiege logo image
        final JLabel logoLabel = loadLogoLabel();
        if (logoLabel != null) {
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            p.add(Box.createRigidArea(new Dimension(0, SPACING_TOP)));
            p.add(logoLabel);
        }

        final JLabel title = new JLabel("PAUSA");
        title.setFont(new Font("Serif", Font.BOLD, TITLE_SIZE));
        title.setForeground(new Color(GOLD_G, GOLD_R, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JButton btnResume = createBtn("RIPRENDI",
                new Color(BUTTON_HEIGHT, BTN_RESUME_G, BTN_RESUME_B));
        btnResume.addActionListener(e -> gc.togglePause());

        final JButton btnRestart = createBtn("RIAVVIA LIVELLO",
                new Color(BTN_RESTART_R, BTN_RESTART_G, BTN_RESTART_B));
        btnRestart.addActionListener(e -> gc.restartGame());

        final JButton btnMenu = createBtn("TORNA AL MENU",
                new Color(BTN_MENU_R, BTN_MENU_G, BTN_MENU_B));
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

    private static JLabel loadLogoLabel() {
        try (InputStream is = PauseMenuDialog.class.getResourceAsStream("/images/towersiege_logo.jpg")) {
            if (is != null) {
                final Image original = ImageIO.read(is);
                final int origWidth = original.getWidth(null);
                final int origHeight = original.getHeight(null);
                final int scaledHeight = (int) ((double) LOGO_WIDTH / origWidth * origHeight);
                final Image scaled = original.getScaledInstance(LOGO_WIDTH, scaledHeight, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(scaled));
            }
        } catch (final IOException e) {
            java.util.logging.Logger.getLogger(PauseMenuDialog.class.getName())
                    .log(java.util.logging.Level.WARNING, "Logo non trovato", e);
        }
        return null;
    }

    private JButton createBtn(final String text, final Color bg) {
        final JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return b;
    }

    /**
     * Shhows the dialog.
     */
    public void show() {
        dialog.setVisible(true);
    }

    /**
     * Hides the dialog.
     */
    public void hide() {
        dialog.dispose();
    }
}
