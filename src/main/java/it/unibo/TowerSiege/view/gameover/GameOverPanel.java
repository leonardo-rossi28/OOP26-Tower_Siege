package it.unibo.TowerSiege.view.gameover;

import it.unibo.TowerSiege.model.gamestate.GameState;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class GameOverPanel {
    
    private final GameState state;

    public GameOverPanel(final GameState state){
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();

        if (state == GameState.DEFEAT || state == GameState.VICTORY){
            g2.setColor(new Color(0, 0, 0, 180));;
            g2.fillRect(0, 0, w, h);

            g2.setFont(new Font("Serif", Font.BOLD, 72));
            final String msg = state == GameState.VICTORY ? "VITTORIA!": "SCONFITTA!";
            g2.setColor(state == GameState.VICTORY ? new Color(255, 215, 0): new Color(200, 50, 50));

            final FontMetrics fm = g2.getFontMetrics();
            final int mx = (w - fm.stringWidth(msg)) / 2;
            g2.drawString(msg, mx, h / 2 - 29);

            g2.setFont(new Font("Serif", Font.PLAIN, 24));
            g2.setColor(Color.WHITE);
            final String sub = state == GameState.VICTORY ? "Preparazione al prossimo livello...": "La base e' caduta.";
            final FontMetrics fms = g2.getFontMetrics();
            g2.drawString(sub, (w - fms.stringWidth(sub)) /2, h / 2 + 30);
        }
    }
}
