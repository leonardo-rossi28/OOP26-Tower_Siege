package it.unibo.TowerSiege.view.gameover;

import it.unibo.TowerSiege.model.gamestate.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Overplay panel swhown when the game reaches a terminal state
 */

public class GameOverPanel extends JPanel {
    
    private static final int TITLE_SIZE=72;
    private static final int SUBTITLE_SIZE=24;
    private static final int TITLE_Y_OFFSET=20;
    private static final int SUBTITLE_Y_OFFSET=30;
    private static final int OVERLAY_ALPHA=100;
    private static final int GOLD=255;
    private static final int GOLDISH=215;
    private static final int RED=200;
    private static final int RED_DARK=50;
    private static final int WHITE=50;


    private final GameState state;

    public GameOverPanel(final GameState state){
        this.state = state;
        setOpaque(false); // So it draws over the GamePanel properly
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();

        if (state == GameState.DEFEAT || state == GameState.VICTORY){
            g2.setColor(new Color(0, 0, 0, OVERLAY_ALPHA));;
            g2.fillRect(0, 0, w, h);

            g2.setFont(new Font("Serif", Font.BOLD, TITLE_SIZE));
            final String msg = state == GameState.VICTORY ? "VITTORIA!": "SCONFITTA!";
            g2.setColor(state == GameState.VICTORY ? new Color(GOLD, GOLDISH, 0): new Color(RED, RED_DARK, RED_DARK));

            final FontMetrics fm = g2.getFontMetrics();
            final int mx = (w - fm.stringWidth(msg)) / 2;
            g2.drawString(msg, mx, h / 2 - TITLE_Y_OFFSET);

            g2.setFont(new Font("Serif", Font.PLAIN, SUBTITLE_SIZE));
            g2.setColor(Color.WHITE);
            final String sub = state == GameState.VICTORY ? "Preparazione al prossimo livello...": "La base e' caduta.";
            final FontMetrics fms = g2.getFontMetrics();
            g2.drawString(sub, (w - fms.stringWidth(sub)) /2, h / 2 + SUBTITLE_Y_OFFSET);
        }
    }
}
