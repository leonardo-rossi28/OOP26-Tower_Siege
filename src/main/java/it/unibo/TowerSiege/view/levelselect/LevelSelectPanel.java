package it.unibo.TowerSiege.view.levelselect;

import it.unibo.TowerSiege.controller.maincontroller.api.MainController;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.view.utils.ImageLoader;

import javax.swing.JPanel;
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
    private final GameModel model;
    private final int[][] lvlPos = { { 140, 440 }, { 400, 250 }, { 660, 400 } };
    private final long randomSeed = 99L;

    public LevelSelectPanel(final MainController controller, final GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                for (int i = 0; i < 3; i++) {
                    final int dx = e.getX() - lvlPos[i][0];
                    final int dy = e.getY() - lvlPos[i][1];
                    if (dx * dx + dy < 40 * 40 && (i + 1) <= model.getMaxUnlockedLevel()) {
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
            final Image[] imgs = { ImageLoader.imgTree, ImageLoader.imgBush, ImageLoader.imgRock, ImageLoader.imgRockBush };
            final Image di = imgs[r.nextInt(4)];
            final int ds = 25 + r.nextInt(20);
            if (di != null) {
                g2.drawImage(di, dx, dy, ds, ds, null);
            }
        }

        //Winding path
        g2.setColor
    }
}
