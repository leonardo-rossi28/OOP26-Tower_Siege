package it.unibo.towersieges.view.levelselect;

import javax.swing.JPanel;

import it.unibo.towersieges.controller.maincontroller.api.MainController;
import it.unibo.towersieges.model.gamemodel.api.GameModel;
import it.unibo.towersieges.view.utils.ImageLoader;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class LevelSelectPanel extends JPanel {

    private static final Color C_GOLD = new Color(255, 215, 0);
    private final int[][] lvlPos = { { 140, 440 }, { 400, 250 }, { 660, 400 } };
    private final long randomSeed = 99L;

    public LevelSelectPanel(final MainController controller, final GameModel model) {
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                for (int i = 0; i < 3; i++) {
                    final int dx = e.getX() - lvlPos[i][0];
                    final int dy = e.getY() - lvlPos[i][1];
                    if (dx * dx + dy * dy < 40 * 40) {
                        controller.startLevel(i + 1);
                        break;
                    }
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final int w = getWidth();
        final int h = getHeight();

        //Background
        g2.setPaint(new GradientPaint(0, 0, new Color(65,145,55), 0, h, new Color(40, 110, 35)));
        g2.fillRect(0, 0, w, h);

        final Random r = new Random(randomSeed);
        for (int i = 0; i < 40; i++) {
            g2.setColor(new Color(50 + r.nextInt(30), 130 + r.nextInt(40), 40 + r.nextInt(20), 50));
            g2.fillOval(r.nextInt(w), r.nextInt(h), 30 + r.nextInt(50), 30 + r.nextInt(50));
        }

        ImageLoader.loadAll(); //Ensure assets are loaded
        for (int i = 0; i < 25; i++) {
            final int dx = r.nextInt(w - 40) + 20;
            final int dy = r.nextInt(h - 60) + 20;
            final Image[] imgs = { ImageLoader.getImgTree(), ImageLoader.getImgBush(), ImageLoader.getImgRock(), ImageLoader.getImgRockBush() };
            final Image di = imgs[r.nextInt(4)];
            final int ds = 25 + r.nextInt(20);
            if (di != null) {
                g2.drawImage(di, dx, dy, ds, ds, null);
            }
        }

        //Winding path
        g2.setColor(new Color(180, 150, 100));
        g2.setStroke(new BasicStroke(18, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawPathLines(g2);

        g2.setColor(new Color(210, 195, 155));
        g2.setStroke(new BasicStroke(19, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawPathLines(g2);
        g2.setStroke(new BasicStroke(1));

        //Title
        g2.setFont(new Font("Serif", Font.BOLD, 34));
        g2.setColor(new Color(0, 0, 0, 140));
        g2.drawString("MAPPA DEI LIVELLI", 222, 52);
        g2.setColor(C_GOLD);
        g2.drawString("MAPPA DEI LIVELLI", 220, 50);

        //Level nodes
        final String[] names = {"Foresta", "Pianura", "Montagna"};
        final String[] diff = {"Facile", "Medio", "Difficile"};
        final Color[] cols = {new Color(40, 180, 80), new Color(200, 160, 40), new Color(200, 60, 60) };
        for (int i = 0; i < 3; i++) {
            final int cx = lvlPos[i][0];
            final int cy = lvlPos[i][1];

            g2.setColor(new Color(cols[i].getRed(), cols[i].getGreen(), cols[i].getBlue(), 40));
            g.fillOval(cx - 50, cy - 50, 100, 100);

            g2.setColor(new Color(0, 0, 0, 60));
            g2.fillOval( cx - 37, cy - 33, 74, 70);
            g2.setColor(new Color(60, 50, 35));
            g2.fillOval( cx -35, cy - 35, 70, 70);
            g2.setColor(cols[i]);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(cx - 35, cy - 35, 70, 70);
            g2.setStroke(new BasicStroke(1));

            g2.setFont(new Font("Serif", Font.BOLD, 36));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString("" + (i + 1), cx - fm.stringWidth("" + (i + 1)) / 2, cy + 12);

            g2.setFont(new Font("Serif", Font.BOLD, 14));
            g2.setColor(C_GOLD);
            fm = g2.getFontMetrics();
            g2.drawString(names[i], cx - fm.stringWidth(names[i]) / 2, cy + 52);

            g2.setFont(new Font("Serif", Font.BOLD, 11));
            g2.setColor(cols[i]);
            fm = g2.getFontMetrics();
            g2.drawString(diff[i], cx - fm.stringWidth(diff[i]) / 2, cy + 66);
        }

        g2.setFont(new Font("Serif", Font.PLAIN, 12));
        g2.setColor(new Color(200, 200, 200, 150));
        g2.drawString("Clicca su un livello per giocare", w / 2 - 100, h - 20);
    }

    private void drawPathLines(final Graphics2D g2) {
        g2.drawLine(lvlPos[0][0], lvlPos[0][1], 220, 380);
        g2.drawLine(220, 380, 280, 300);
        g2.drawLine(280, 300, 340, 270);
        g2.drawLine(340, 270, lvlPos[1][0], lvlPos[1][1]);
        g2.drawLine(lvlPos[1][0], lvlPos[1][1], 480, 270);
        g2.drawLine(480, 270, 540, 320);
        g2.drawLine(540, 320, 600, 370);
        g2.drawLine(600, 370, lvlPos[2][0], lvlPos[2][1]);
    }
}
