package it.unibo.towersiege.view.gameover;

import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Overplay panel swhown when the game reaches a terminal state
 */
public final class GameOverPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private static final int TITLE_SIZE = 72;
    private static final int SUBTITLE_SIZE = 24;
    private static final int TITLE_Y_OFFSET = 20;
    private static final int SUBTITLE_Y_OFFSET = 30;
    private static final int OVERLAY_ALPHA = 100;
    private static final int GOLD = 255;
    private static final int GOLDISH = 215;
    private static final int RED = 200;
    private static final int RED_DARK = 50;

    private static final int BTN_WIDTH = 300;
    private static final int BTN_HEIGHT = 50;
    private static final int BTN_Y = 420;
    private static final int BTN_X = 250;
    private static final int BTN_FONT_SIZE = 18;

    private static final String FONT_SANSSERIF = "SansSerif";
    private static final String BTN_VICTORY_TEXT = "Torna alla mappa dei livelli";
    private static final String BTN_DEFEAT_TEXT = "Torna al menu";
    
    private final GameState state;

    /**
     * Constructor a new GameOverPanel.
     * 
     * @param state the terminal game state
     * @param mc the main controller
     * @param the game model
     */
    public GameOverPanel(final GameState state, final MainController mc, final GameModel model) {
        this.state = state;
        super.setOpaque(false);
        setLayout(null);

        if (state == GameState.VICTORY || state == GameState.DEFEAT) {
            final JButton btn = new JButton(state == GameState.VICTORY ? BTN_VICTORY_TEXT : BTN_DEFEAT_TEXT);
            btn.setFont(new Font(FONT_SANSSERIF, Font.BOLD, BTN_FONT_SIZE));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBounds(BTN_X, BTN_Y, BTN_WIDTH, BTN_HEIGHT);

            btn.addActionListener((final ActionEvent e) -> {
                if (state == GameState.VICTORY) {
                    mc.backToLevelSelect();
                }
                else {
                    mc.backToMenu();
                }
            });

            add(btn);
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();

        if (state == GameState.DEFEAT || state == GameState.VICTORY) {
            g2.setColor(new Color(0, 0, 0, OVERLAY_ALPHA));
            g2.fillRect(0, 0, w, h);

            g2.setFont(new Font("Serif", Font.BOLD, TITLE_SIZE));
            final String msg = state == GameState.VICTORY ? "VITTORIA!" : "SCONFITTA!";
            g2.setColor(state == GameState.VICTORY ? new Color(GOLD, GOLDISH, 0): new Color(RED, RED_DARK, RED_DARK));

            final FontMetrics fm = g2.getFontMetrics();
            final int mx = (w - fm.stringWidth(msg)) / 2;
            g2.drawString(msg, mx, h / 2 - TITLE_Y_OFFSET);

            g2.setFont(new Font("Serif", Font.PLAIN, SUBTITLE_SIZE));
            g2.setColor(Color.WHITE);
            final String sub = state == GameState.VICTORY ? "Preparazione al prossimo livello..." : "La base e' caduta.";
            final FontMetrics fms = g2.getFontMetrics();
            g2.drawString(sub, (w - fms.stringWidth(sub)) / 2, h / 2 + SUBTITLE_Y_OFFSET);
        }
    }
}
