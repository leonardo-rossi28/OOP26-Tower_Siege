package it.unibo.TowerSiege.view.gamepanel;

import it.unibo.TowerSiege.controller.mapcontroller.api.MapController;
import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.model.player.api.Player;
import it.unibo.TowerSiege.model.projectile.api.Projectile;
import it.unibo.TowerSiege.view.utils.ImageLoader;
import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.tower.TowerType;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Main panel used to draw the game scene and handle UI interactions
 */

public class GamePanel extends JPanel {

    private static final Color C_UI = new Color(20, 15, 10, 210);
    private static final Color C_GOLD = new Color(255, 215, 0);

    private GameModel model;
    private MapController mapController;
    private ShopController shopController;
    private BuildingSpot hoverSpot;

    public GamePanel(final GameModel m, final MapController mapC, final ShopController shopC) {
        this.model = m;
        this.mapController = mapC;
        this.shopController = shopC;
        ImageLoader.loadAll();
        setupMouse();
    }
    
    public void setModel(final GameModel m) {
        this.model = m;
    }

    public void setMapController(final MapController c) {
        this.mapController = c;
    }

    public void setShopController(final ShopController c) {
        this.shopController = c;
    }

    private void setupMouse() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                final int col = e.getX() / 50;
                final int row = e.getY() / 50;
                hoverSpot = model.getMap().getSpotAt(col, row);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int col = e.getX() / 50;
                final int row = e.getY() / 50;

                if(e.getButton() == MouseEvent.BUTTON3) {
                    final BuildingSpot spot = model.getMap().getSpotAt(col, row);
                    if(spot != null) {
                        mapController.sellTowerAtSpot(spot);
                    }
                    return;
                }

                final TowerType[] types = TowerType.values();
                final int cw = 100;
                final int gap = 8;
                final int total = types.length * (cw + gap) - gap;
                final int sx = (getWidth() - total) / 2;
                final int sy = getHeight() - 70 - 12;

                for (int i = 0; i < types.length; i++) {
                    final int cx = sx + i * (cw + gap);
                    if(e.getX() >= cx && e.getX() <= cx + cw && e.getY() >= sy && e.getY() <= sy + 70) {
                        shopController.setSelectedTowerType(types[i]);
                        repaint();
                        return;
                    }
                }

                final BuildingSpot spot = model.getMap().getSpotAt(col, row);
                if (spot != null) {
                    mapController.interactWithSpot(spot);
                }
            }
        });
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawMap(g2);
        drawGridAndSpots(g2);
        drawTowers(g2);
        drawEnemies(g2);
        drawProjectiles(g2);
        drawVisualEffects(g2);
        drawHUD(g2);

        if (model.getState() == GameState.DEFEAT || model.getState() == GameState.VICTORY) {
        }
    }

    private void drawMap(final Graphics2D g2) {
        g2.setColor(new Color(60, 130, 50));
        g2.fillRect(0, 0, getWidth(), getHeight());

        final int[][] grid = model.getMap().getGrid();
        g2.setColor(new Color(180, 160, 120));
        for (int r = 0; r < grid.length; r++) {
            for ( int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    g2.fillRect(c * 50, r * 50, 50, 50);
                }
            }
        }
    }

    private void drawDecoration(final Graphics g2){
        final List<double[]> decorations=model.getMap().getDecorations();
        for(final double[] dec : decorations){
            final int col = (int) dec[0];
            final int row = (int) dec[1];
            final int type = (int) dec[2];
            final int px = col * 50;
            final int py = row *50;

            Image img=null;
            switch (type){
                case 0: img = ImageLoader.getImgTree(); break;
                case 1: img= ImageLoader.getImgBush(); break;
                case 2: img=ImageLoader.getImgRock(); break;
                case 3: img=ImageLoader.getImgRockBush(); break;
            }

            if(img != null){
                g2.drawImage(img, px + 5, py + 5, 40, 40, null);
            }

        }
    }

    private void drawGridAndSpots(final Graphics2D g2) {
        final List<BuildingSpot> spots = model.getMap().getBuildingSpots();
        for (final BuildingSpot s : spots) {
            final int px = s.getCol() * 50;
            final int py = s.getRow() * 50;

            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRect(px, py, 50, 50);

            if (!s.isOccupied()) {
                g2.setColor(new Color(255, 255, 255, 80));
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f}, 0.0f));
                g2.drawRect(px + 4, py + 4, 42, 42);
                g2.setStroke(new BasicStroke(1));
            }
        }

        if (hoverSpot != null) {
            final int hx = hoverSpot.getCol() * 50;
            final int hy = hoverSpot.getRow() * 50;
            final TowerType selected = shopController.getSelectedTowerType();

            g2.setColor(new Color(255, 255, 255, 60));
            g2.fillRect(hx, hy, 50, 50);
            g2.setColor(Color.WHITE);
            g2.drawRect(hx, hy, 50, 50);

            if (!hoverSpot.isOccupied() && selected != null) {
                final int rPx = selected.getRange() * 40;
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillOval((hx + 25) -rPx, (hy+25) - rPx, rPx * 2, rPx * 2);
                g2.setColor(new Color(255, 255, 255, 100));
                g2.drawOval((hx + 25) -rPx, (hy+25) - rPx, rPx * 2, rPx * 2);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                g2.drawString(selected.getCost() + "g", hx + 10, hy - 5);
            } else if (hoverSpot.isOccupied()) {
                final Tower t = hoverSpot.getTower();
                final int rPx = t.getRange() * 40;
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval((int) t.getPixelX() -rPx, (int) t.getPixelY() - rPx, rPx * 2, rPx * 2);
                g2.setColor(new Color(255, 255, 255, 80));
                g2.drawOval((int) t.getPixelX() -rPx, (int) t.getPixelY() - rPx, rPx * 2, rPx * 2);

                final int upCost = t.getType().getCost() / 2;
                final int sellCost = t.getType().getCost() / 2;
                g2.setColor(Color.GREEN);
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
                g2.drawString("Up: " + upCost, hx, hy - 16);
                g2.setColor(Color.RED);
                g2.drawString("Sell: " + sellCost, hx, hy - 4);
            }
        }
    }

    private void drawTowers(final Graphics2D g2) {
        for (final Tower t : model.getMap().getTowers()) {
            final int cx = (int) t.getPixelX();
            final int cy = (int) t.getPixelY();

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillOval(cx -20, cy -10, 40, 20);

            Image img = ImageLoader.getSpTowerBasic();
            switch (t.getType()) {
                case SNIPER: img = ImageLoader.getSpTowerSniper(); break;
                case RAPID: img = ImageLoader.getSpTowerRapid(); break;
                case ICE: img = ImageLoader.getSpTowerIce(); break;
                default: break;
            }

            if (img != null) {
                g2.drawImage(img, cx - 25, cy- 35, 50, 50, null);
            } else {
                g2.setColor(Color.BLUE);
                g2.fillRect(cx - 15, cy - 15, 30, 30);
            }        

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            g2.drawString("Lv" + t.getLevel(), cx - 10, cy + 20);
        }
    }

    private void drawEnemies(final Graphics2D g2) {
        final List<Enemy> enemies = model.getActiveEnemies();

        for (final Enemy e : enemies) {
            final int ex = (int) e.getPixelX() - 25;
            final int ey = (int) e.getPixelY() -25;

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillOval(ex + 10, ey + 40, 30, 15);

            Image img = ImageLoader.getSpEnemyBasic();
            switch (e.getType()) {
                case FAST: img = ImageLoader.getSpEnemyFast(); break;
                case TANK: img = ImageLoader.getSpEnemyTank(); break;
                default: break;
            }

            if (img != null) {
                g2.drawImage(img, ex, ey, 50, 50, null);
            } else {
                g2.setColor(Color.RED);
                g2.fillOval(ex, ey, 50, 50);
            }

            if (e.getHitFlashTicks() > 0) {
                g2.setColor(new Color(255, 255, 255, 150));
                g2.fillOval(ex + 10, ey + 10, 30, 30);
            }

            final int maxH = e.getMaxHealth();
            final int curH = e.getHealth();
            final int bw = 30;
            final int bh = 4;
            final int bx = ex + 10;
            final int by = ey -5;
            g2.setColor(Color.RED);
            g2.fillRect(bx, by, bw, bh);
            g2.setColor(Color.GREEN);
            g2.fillRect(bx, by, (int) (bw * ((double) curH /maxH)), bh);
            g2.setColor(Color.BLACK);
            g2.drawRect(bx, by, bw, bh);    
        }
    }

    private void drawProjectiles(final Graphics2D g2) {
        for (final Projectile p : model.getProjectiles()) {
            Color pc = Color.YELLOW;
            int size = 8;
            if (p.getSourceTowerType() == TowerType.SNIPER) {
                pc = Color.RED; size = 12;
            } else if (p.getSourceTowerType() == TowerType.ICE) {
                pc = Color.CYAN; size = 10;
            } else if (p.getSourceTowerType() == TowerType.RAPID) {
                pc = Color.ORANGE; size = 6;
            }
            g2.setColor(pc);
            g2.fillOval((int) p.getX() - size / 2, (int) p.getY() - size / 2, size, size);
        }
    }

    private void drawVisualEffects(final Graphics2D g2) {
        final int fAnim = model.getFireAnimTicks();

        if (fAnim > 0) {
            g2.setColor(new Color(255, 100, 0, (fAnim * 255 / 60) / 2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(255, 200, 0, fAnim * 255 / 60));
            g2.setFont(new Font("Serif", Font.BOLD, 48));
            final String m = "PIOGGIA DI FUOCO!";
            final FontMetrics fm = g2.getFontMetrics();
            g2.drawString(m, (getWidth() - fm.stringWidth(m)) / 2, getHeight() / 2);
        }

        final int frAnim = model.getFreezeAnimTicks();
        if (frAnim > 0) {
            g2.setColor(new Color(0, 200, 255, (frAnim * 255 / 60) / 2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(200, 255, 255, frAnim * 255 / 60));
            g2.setFont(new Font("Serif", Font.BOLD, 48));
            final String m = "GELO GLOBALE!";
            final FontMetrics fm = g2.getFontMetrics();
            g2.drawString(m, (getWidth() - fm.stringWidth(m)) / 2, getHeight() / 2);
        }
    }

    private void drawHUD(final Graphics2D g2) {
        final Player p = model.getPlayer();

        g2.setColor(C_UI);
        g2.fillRect(0, 0, getWidth(), 40);
        g2.setColor(new Color(60, 50, 40));
        g2.drawLine(0, 40, getWidth(), 40);

        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.setColor(Color.WHITE);
        g2.drawString("Base HP: " + p.getBaseHealth(), 20, 25);
        g2.setColor(C_GOLD);
        g2.drawString("Coins: " + p.getCoins(), 140, 25);
        g2.setColor(Color.CYAN);
        g2.drawString("Score: " + model.getScore().getTotal(), 260, 25);
        g2.setColor(new Color(200, 200, 255));

        final String wTxt = model.isWaveInProgress() ? "In corso" : "Attesa";
        g2.drawString("Ondata: " + model.getCurrentWave() + "/" + model.getTotalWaves() + " ("+ wTxt + ")", 400, 25);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("W=Avvia Ondata | F=Fuoco (" + (model.getFireCooldown() / 60) + "s) | G=Gelo (" + (model.getFreezeCooldown() / 60) + "s) | ESC=Pausa", 20, 55);
        
        // Shop
        final TowerType[] types = TowerType.values();
        final int cw = 100;
        final int gap = 8;
        final int total = types.length * (cw + gap) - gap;
        final int sx = (getWidth() - total) / 2;
        final int sy = getHeight() -70 -12;

        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(sx - 10, sy - 10, total + 20, 90, 15, 15);

        for (int i = 0; i < types.length; i++) {
            final TowerType t = types[i];
            final int cx = sx + i * (cw + gap);
            
            if (shopController.getSelectedTowerType() == t) {
                g2.setColor(new Color(80, 150, 255));
                g2.fillRoundRect(cx - 2, sy - 2, cw + 4, 74, 10, 10);
            }

            g2.setColor(new Color(40, 35, 30));
            g2.fillRoundRect(cx, sy, cw, 70, 8, 8);
            g2.setColor(new Color(80, 70, 60));
            g2.drawRoundRect(cx, sy, cw, 70, 8, 8);

            g2.setColor(p.getCoins() >= t.getCost() ? Color.WHITE : Color.GRAY);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(t.name(), cx + 5, sy + 18);
            g2.setColor(C_GOLD);
            g2.drawString(t.getCost() + "g", cx + 5, sy + 36);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("Dmg: " + t.getDamage(), cx + 5, sy + 50);
            g2.drawString("Rng: " + t.getRange(), cx + 5, sy + 62);
        }
    }
}
