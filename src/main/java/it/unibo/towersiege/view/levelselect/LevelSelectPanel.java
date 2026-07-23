package it.unibo.towersiege.view.levelselect;

import javax.swing.JPanel;

import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.view.utils.ImageLoader;

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

/**
 * Panel that displays the level selection map with interactive level nodes.
 */
public final class LevelSelectPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int PANEL_W = 800;
    private static final int PANEL_H = 600;
    private static final int LEVEL_COUNT = 3;
    private static final int LVL1_X = 140;
    private static final int LVL1_Y = 440;
    private static final int LVL2_X = 400;
    private static final int LVL2_Y = 250;
    private static final int LVL3_X = 660;
    private static final int LVL3_Y = 400;
    private static final int CLICK_RADIUS = 40;
    private static final int DECO_COUNT = 25;
    private static final int BG_CIRCLE_COUNT = 40;
    private static final int BG_R1 = 65;
    private static final int BG_G1 = 145;
    private static final int BG_B1 = 55;
    private static final int BG_R2 = 40;
    private static final int BG_G2 = 110;
    private static final int BG_B2 = 35;
    private static final int BG_CIRCLE_R_BASE = 50;
    private static final int BG_CIRCLE_G_BASE = 130;
    private static final int BG_CIRCLE_B_BASE = 40;
    private static final int BG_CIRCLE_R_RANGE = 30;
    private static final int BG_CIRCLE_G_RANGE = 40;
    private static final int BG_CIRCLE_B_RANGE = 20;
    private static final int BG_CIRCLE_ALPHA = 50;
    private static final int BG_CIRCLE_SIZE_MIN = 30;
    private static final int BG_CIRCLE_SIZE_RANGE = 50;
    private static final int DECO_MARGIN = 40;
    private static final int DECO_MARGIN_Y = 60;
    private static final int DECO_OFFSET = 20;
    private static final int DECO_SIZE_MIN = 25;
    private static final int DECO_SIZE_RANGE = 20;
    private static final int DECO_IMG_COUNT = 4;
    private static final int PATH_OUTER_WIDTH = 18;
    private static final int PATH_INNER_WIDTH = 10;
    private static final int PATH_R1 = 180;
    private static final int PATH_G1 = 150;
    private static final int PATH_B1 = 100;
    private static final int PATH_R2 = 210;
    private static final int PATH_G2 = 195;
    private static final int PATH_B2 = 155;
    private static final int PATH_P1_X = 220;
    private static final int PATH_P1_Y = 380;
    private static final int PATH_P2_X = 280;
    private static final int PATH_P2_Y = 300;
    private static final int PATH_P3_X = 340;
    private static final int PATH_P3_Y = 270;
    private static final int PATH_P4_X = 480;
    private static final int PATH_P4_Y = 270;
    private static final int PATH_P5_X = 540;
    private static final int PATH_P5_Y = 320;
    private static final int PATH_P6_X = 600;
    private static final int PATH_P6_Y = 370;
    private static final int TILE_FONT_SIZE = 34;
    private static final int TILE_SHADOW_ALPHA = 140;
    private static final int TITLE_X = 220;
    private static final int TITLE_Y = 50;
    private static final int TILE_SHADOW_DX = 2;
    private static final int NODE_GLOW_R = 50;
    private static final int NODE_OUTER_R = 100;
    private static final int NODE_SHADOW_R = 37;
    private static final int NODE_SHADOW_DY = 2;
    private static final int NODE_SHADOW_W = 74;
    private static final int NODE_SHADOW_H = 70;
    private static final int NODE_SHADOW_ALPHA = 60;
    private static final int NODE_INNER_R = 35;
    private static final int NODE_INNER_D = 70;
    private static final int NODE_BG_R = 60;
    private static final int NODE_BG_G = 50;
    private static final int NODE_BG_B = 35;
    private static final int NODE_STROKE = 3;
    private static final int NODE_GLOW_ALPHA = 40;
    private static final int NODE_NUM_FONT_SIZE = 36;
    private static final int NODE_NUM_Y_OFFSET = 12;
    private static final int NODE_NAME_FONT_SIZE = 14;
    private static final int NODE_NAME_Y_OFFSET = 52;
    private static final int NODE_DIFF_FONT_SIZE = 11;
    private static final int NODE_DIFF_Y_OFFSET = 66;
    private static final int HINT_FONT_SIZE = 12;
    private static final int HINT_ALPHA = 150;
    private static final int HINT_COLOR = 200;
    private static final int HINT_X_OFFSET = 100;
    private static final int HINT_Y_OFFSET = 20;
    private static final int GREEN_NODE_R = 40;
    private static final int GREEN_NODE_G = 180;
    private static final int GREEN_NODE_B = 80;
    private static final int YELLOW_NODE_R = 200;
    private static final int YELLOW_NODE_G = 160;
    private static final int YELLOW_NODE_B = 40;
    private static final int RED_NODE_R = 200;
    private static final int RED_NODE_G = 60;
    private static final int RED_NODE_B = 60;
    private static final long RANDOM_SEED = 99L;

    private static final Color C_GOLD = new Color(255, 215, 0);
    private static final String FONT_SERIF = "Serif";
    private final Random random = new Random(RANDOM_SEED);

    private final int[][] lvlPos = {
            { LVL1_X, LVL1_Y },
            { LVL2_X, LVL2_Y },
            { LVL3_X, LVL3_Y }
    };

    /**
     * Creates the level selection panel.
     * 
     * @param controller main controller
     * @param model      the game model
     */

    public LevelSelectPanel(final MainController controller, final GameModel model) {
        setPreferredSize(new Dimension(PANEL_W, PANEL_H));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                for (int i = 0; i < LEVEL_COUNT; i++) {
                    final int dx = e.getX() - lvlPos[i][0];
                    final int dy = e.getY() - lvlPos[i][1];
                    if (dx * dx + dy * dy < CLICK_RADIUS * CLICK_RADIUS) {
                        controller.startLevel(i + 1);
                        break;
                    }
                }
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final int w = getWidth();
        final int h = getHeight();

        drawBackGround(g2, w, h);
        drawDecorations(g2, w, h);
        drawPaths(g2);
        drawTitle(g2);
        drawLevelNodes(g2);
        drawHint(g2, w, h);
    }

    private void drawBackGround(final Graphics2D g2, final int w, final int h) {
        g2.setPaint(new GradientPaint(0, 0, new Color(BG_R1, BG_G1, BG_B1),
                0, h, new Color(BG_R2, BG_G2, BG_B2)));
        g2.fillRect(0, 0, w, h);

        for (int i = 0; i < BG_CIRCLE_COUNT; i++) {
            g2.setColor(new Color(
                    BG_CIRCLE_R_BASE + random.nextInt(BG_CIRCLE_R_RANGE),
                    BG_CIRCLE_G_BASE + random.nextInt(BG_CIRCLE_G_RANGE),
                    BG_CIRCLE_B_BASE + random.nextInt(BG_CIRCLE_B_RANGE),
                    BG_CIRCLE_ALPHA));
            final int size = BG_CIRCLE_SIZE_MIN + random.nextInt(BG_CIRCLE_SIZE_RANGE);
            g2.fillOval(random.nextInt(w), random.nextInt(h), size, size);
        }
    }

    private void drawDecorations(final Graphics2D g2, final int w, final int h) {
        ImageLoader.loadAll();
        // skip the random state used by background
        for (int i = 0; i < BG_CIRCLE_COUNT; i++) {
            random.nextInt();
            random.nextInt();
            random.nextInt();
            random.nextInt();
            random.nextInt();
            random.nextInt();
        }
        final Image[] imgs = {
                ImageLoader.getImgTree(), ImageLoader.getImgBush(),
                ImageLoader.getImgRock(), ImageLoader.getImgRockBush(),
        };
        for (int i = 0; i < DECO_COUNT; i++) {
            final int dx = random.nextInt(w - DECO_MARGIN) + DECO_OFFSET;
            final int dy = random.nextInt(h - DECO_MARGIN_Y) + DECO_OFFSET;
            final Image di = imgs[random.nextInt(DECO_IMG_COUNT)];
            final int ds = DECO_SIZE_MIN + random.nextInt(DECO_SIZE_RANGE);
            if (di != null) {
                g2.drawImage(di, dx, dy, ds, ds, null);
            }
        }
    }

    private void drawPaths(final Graphics2D g2) {
        g2.setColor(new Color(PATH_R1, PATH_G1, PATH_B1));
        g2.setStroke(new BasicStroke(PATH_OUTER_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawPathLines(g2);

        g2.setColor(new Color(PATH_R2, PATH_G2, PATH_B2));
        g2.setStroke(new BasicStroke(PATH_INNER_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawPathLines(g2);
        g2.setStroke(new BasicStroke(1));
    }

    private void drawTitle(final Graphics2D g2) {
        g2.setFont(new Font(FONT_SERIF, Font.BOLD, TILE_FONT_SIZE));
        g2.setColor(new Color(0, 0, 0, TILE_SHADOW_ALPHA));
        g2.drawString("MAPPA DEI LIVELLI",
                TITLE_X + TILE_SHADOW_DX, TITLE_Y + TILE_SHADOW_DX);
        g2.setColor(C_GOLD);
        g2.drawString("MAPPA DEI LIVELLI", TITLE_X, TITLE_Y);
    }

    private void drawLevelNodes(final Graphics2D g2) {
        final String[] names = { "Foresta", "Pianura", "Montagna" };
        final String[] diff = { "Facile", "Medio", "Difficile" };
        final Color[] cols = {
                new Color(GREEN_NODE_R, GREEN_NODE_G, GREEN_NODE_B),
                new Color(YELLOW_NODE_R, YELLOW_NODE_G, YELLOW_NODE_B),
                new Color(RED_NODE_R, RED_NODE_G, RED_NODE_B) };
        for (int i = 0; i < LEVEL_COUNT; i++) {
            final int cx = lvlPos[i][0];
            final int cy = lvlPos[i][1];

            g2.setColor(new Color(cols[i].getRed(), cols[i].getGreen(),
                    cols[i].getBlue(), NODE_GLOW_ALPHA));
            g2.fillOval(cx - NODE_GLOW_R, cy - NODE_GLOW_R,
                    NODE_OUTER_R, NODE_OUTER_R);

            g2.setColor(new Color(0, 0, 0, NODE_SHADOW_ALPHA));
            g2.fillOval(cx - NODE_SHADOW_R, cy - NODE_SHADOW_R + NODE_SHADOW_DY,
                    NODE_SHADOW_W, NODE_SHADOW_H);
            g2.setColor(new Color(NODE_BG_R, NODE_BG_G, NODE_BG_B));
            g2.fillOval(cx - NODE_INNER_R, cy - NODE_INNER_R,
                    NODE_INNER_D, NODE_INNER_D);
            g2.setColor(cols[i]);
            g2.setStroke(new BasicStroke(NODE_STROKE));
            g2.drawOval(cx - NODE_INNER_R, cy - NODE_INNER_R,
                    NODE_INNER_D, NODE_INNER_D);
            g2.setStroke(new BasicStroke(1));

            g2.setFont(new Font(FONT_SERIF, Font.BOLD, NODE_NUM_FONT_SIZE));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            final String num = String.valueOf(i + 1);
            g2.drawString(num, cx - fm.stringWidth(num) / 2,
                    cy + NODE_NUM_Y_OFFSET);

            g2.setFont(new Font(FONT_SERIF, Font.BOLD, NODE_NAME_FONT_SIZE));
            g2.setColor(C_GOLD);
            fm = g2.getFontMetrics();
            g2.drawString(names[i], cx - fm.stringWidth(names[i]) / 2,
                    cy + NODE_NAME_Y_OFFSET);

            g2.setFont(new Font(FONT_SERIF, Font.BOLD, NODE_DIFF_FONT_SIZE));
            g2.setColor(cols[i]);
            fm = g2.getFontMetrics();
            g2.drawString(diff[i], cx - fm.stringWidth(diff[i]) / 2,
                    cy + NODE_DIFF_Y_OFFSET);
        }
    }

    private void drawHint(final Graphics2D g2, final int w, final int h) {
        g2.setFont(new Font(FONT_SERIF, Font.PLAIN, HINT_FONT_SIZE));
        g2.setColor(new Color(HINT_COLOR, HINT_COLOR, HINT_COLOR, HINT_ALPHA));
        g2.drawString("Clicca su un livello per giocare",
                w / 2 - HINT_X_OFFSET, h - HINT_Y_OFFSET);
    }

    private void drawPathLines(final Graphics2D g2) {
        g2.drawLine(lvlPos[0][0], lvlPos[0][1], PATH_P1_X, PATH_P1_Y);
        g2.drawLine(PATH_P1_X, PATH_P1_Y, PATH_P2_X, PATH_P2_Y);
        g2.drawLine(PATH_P2_X, PATH_P2_Y, PATH_P3_X, PATH_P3_Y);
        g2.drawLine(PATH_P3_X, PATH_P3_Y, lvlPos[1][0], lvlPos[1][1]);
        g2.drawLine(lvlPos[1][0], lvlPos[1][1], PATH_P4_X, PATH_P4_Y);
        g2.drawLine(PATH_P4_X, PATH_P4_Y, PATH_P5_X, PATH_P5_Y);
        g2.drawLine(PATH_P5_X, PATH_P5_Y, PATH_P6_X, PATH_P6_Y);
        g2.drawLine(PATH_P6_X, PATH_P6_Y, lvlPos[2][0], lvlPos[2][1]);
    }
}
