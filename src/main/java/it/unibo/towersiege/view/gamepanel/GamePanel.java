package it.unibo.towersiege.view.gamepanel;

import javax.swing.JPanel;

import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.model.player.api.Player;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.tower.TowerType;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.view.utils.ImageLoader;

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

    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 50;
    private static final int HUD_BAR_HEIGHT = 40;
    private static final int SHOP_CARD_WIDTH = 100;
    private static final int SHOP_CARD_HEIGHT = 70;
    private static final int SHOP_GAP = 8;
    private static final int SHOP_MARGIN = 12;
    private static final int SHOP_ROUND_INNER = 8;
    private static final int SHOP_SELECTION_EXTRA = 4;
    private static final int SHOP_TEXT_Y_NAME = 18;
    private static final int SHOP_TEXT_Y_COST = 36;
    private static final int SHOP_TEXT_Y_DMG = 50;
    private static final int SHOP_TEXT_Y_RNG = 62;
    private static final int SHOP_TEXT_X_OFFSET = 5;
    private static final int FONT_SIZE_HUD = 16;
    private static final int FONT_SIZE_SHOP_NAME = 12;
    private static final int FONT_SIZE_SHOP_STAT = 10;
    private static final int FONT_SIZE_LVL = 10;
    private static final int FONT_SIZE_INFO = 11;
    private static final int HUD_TEXT_Y = 25;
    private static final int HUD_TEXT_X_HP = 20;
    private static final int HUD_TEXT_X_COINS = 140;
    private static final int HUD_TEXT_X_SCORE = 260;
    private static final int HUD_TEXT_X_WAVE = 400;
    private static final int HUD_SUBTEXT_Y = 55;
    private static final int SECONDS_DIVISOR = 60;
    private static final int SHADOW_OFFSET_X = 10;
    private static final int SHADOW_OFFSET_Y = 40;
    private static final int SHADOW_W = 30;
    private static final int SHADOW_H = 15;
    private static final int SPRITE_SIZE = 50;
    private static final int SPRITE_OFFSET = 25;
    private static final int SPRITE_TOWER_Y_OFFSET = 35;
    private static final int LVL_OFFSET_X = 10;
    private static final int LVL_OFFSET_Y = 20;
    private static final int HEALTH_BAR_WIDTH = 30;
    private static final int HEALTH_BAR_HEIGHT = 4;
    private static final int HEALTH_BAR_X_OFFSET = 10;
    private static final int HEALTH_BAR_Y_OFFSET = 5;
    private static final int PROJ_SIZE_DEFAULT = 8;
    private static final int PROJ_SIZE_SNIPER = 12;
    private static final int PROJ_SIZE_ICE = 10;
    private static final int PROJ_SIZE_RAPID = 6;
    private static final int DECORATION_PAD = 5;
    private static final int DECORATION_SIZE = 40;
    private static final int HOVER_RANGE_MULTIPLIER = 40;
    private static final int HOVER_CICRLE_OFFSET = 25;
    private static final int HOVER_COST_OFFSET_X = 10;
    private static final int HOVER_COST_OFFSET_Y = 4;
    private static final int HOVER_UP_OFFSET_Y = 16;
    private static final int HOVER_SELL_OFFEST_Y = 4;
    private static final int COST_HALF_DIVISOR = 2;
    private static final int SHOP_PANEL_PAFFING = 10;
    private static final int SHOP_PANEL_EXTRA_W = 20;
    private static final int SHOP_PANEL_EXTRA_H = 90;
    private static final int SHOP_PANEL_ROUND = 15;
    private static final int HIT_FLASH_OFFSET = 10;
    private static final int HIT_FLASH_SIZE = 30;
    private static final int TOWER_SHADOW_W = 40;
    private static final int TOWER_SHADOW_H = 20;
    private static final int TOWER_SHADOW_X_OFFSET = 20;
    private static final int TOWER_SHADOW_Y_OFFSET = 10;
    private static final int GRID_SPOT_PAD = 4;
    private static final int GRID_SPOT_RECT_SIZE = 42;
    private static final float GRID_DASH_LENGTH = 5.0f;
    private static final float GRID_METER_LIMIT = 10.0f;

    private static final Color C_UI = new Color(20, 15, 10, 210);
    private static final Color C_GOLD = new Color(255, 215, 0);
    private static final Color C_MAP_GRASS = new Color(60, 130, 50);
    private static final Color C_MAP_PATH = new Color(180, 160, 120);
    private static final Color C_SPOT_BG = new Color(0, 0, 0, 40);
    private static final Color C_SPOT_BORDER = new Color(255, 255, 255, 80);
    private static final Color C_HOVER = new Color(255, 255, 255, 60);
    private static final Color C_RANGE_FILL = new Color(255, 255, 255, 30);
    private static final Color C_RANGE_BORDER = new Color(255, 255, 255, 100);
    private static final Color C_RANGE_FILL_OCC = new Color(255, 255, 255, 20);
    private static final Color C_RANGE_BORDER_OCC = new Color(255, 255, 255, 80);
    private static final Color C_SHADOW = new Color(0, 0, 0, 80);
    private static final Color C_HIT_FLASH = new Color(255, 255, 255, 150);
    private static final Color C_HUD_LINE = new Color(60, 50, 40);
    private static final Color C_WAVE_TEXT = new Color(200, 200, 255);
    private static final Color C_ENEMY_SHADOW = new Color(0, 0, 0, 80);
    private static final Color C_SHOP_BG = new Color(0, 0, 0, 100);
    private static final Color C_SHOP_SELECTED = new Color(80, 150, 255);
    private static final Color C_SHOP_CARD_BG = new Color(40, 35, 30);
    private static final Color C_SHOP_CARD_BORDE_COLOR = new Color(80, 70, 60);
    private static final String FONT_SANSSERIF = "SansSerif";

    private GameModel model;
    private MapController mapController;
    private ShopController shopController;
    private BuildingSpot hoverSpot;

    /**
     * 
     * @param m the game model
     * @param mapC the map controller
     * @param shopC the shop controller
     */
    public GamePanel(final GameModel m, final MapController mapC, final ShopController shopC) {
        this.model = m;
        this.mapController = mapC;
        this.shopController = shopC;
        ImageLoader.loadAll();
        setupMouse();
    }
    
    /**
     * Sets the game model
     * 
     * @param m the game model
     */
    public void setModel(final GameModel m) {
        this.model = m;
    }

    /**
     * Sets the map controller
     * 
     * @param c the map controller
     */
    public void setShopController(final ShopController c){
        this.shopController = c;
    }

    public void setMapController(final MapController c) {
        this.mapController = c;
    }

    private void setupMouse() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                final int col = e.getX() / CELL_SIZE;
                final int row = e.getY() / CELL_SIZE;
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
    
                final int total = types.length * (SHOP_CARD_WIDTH + SHOP_GAP) - SHOP_GAP;
                final int shopX = (getWidth() - total) / 2;
                final int shopY = getHeight() - SHOP_CARD_HEIGHT - SHOP_MARGIN;

                for (int i = 0; i < types.length; i++) {
                    final int cx = shopX + i * (SHOP_CARD_WIDTH + SHOP_GAP);
                    if(e.getX() >= shopY && e.getX() <= cx + SHOP_CARD_WIDTH && e.getY() >= sy && e.getY() <= shopY + SHOP_CARD_HEIGHT) {
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

    /** {@inheritDoc} */
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
        g2.setColor(C_MAP_GRASS);
        g2.fillRect(0, 0, getWidth(), getHeight());

        final int[][] grid = model.getMap().getGrid();
        g2.setColor(C_MAP_PATH);
        for (int r = 0; r < grid.length; r++) {
            for ( int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    g2.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
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
            final int px = col * CELL_SIZE;
            final int py = row * CELL_SIZE;

            Image img=null;
            switch (type){
                case 0:
                    img = ImageLoader.getImgTree();
                    break;
                case 1:
                    img = ImageLoader.getImgBush();
                    break;
                case 2:
                    img = ImageLoader.getImgRock();
                    break;
                case 3:
                    img = ImageLoader.getImgRockBush();
                    break;
            }

            if(img != null){
                g2.drawImage(img, px + DECORATION_PAD, py + DECORATION_PAD, DECORATION_SIZE, DECORATION_SIZE, null);
            }

        }
    }

    private void drawGridAndSpots(final Graphics2D g2) {
        final List<BuildingSpot> spots = model.getMap().getBuildingSpots();
        for (final BuildingSpot s : spots) {
            final int px = s.getCol() * CELL_SIZE;
            final int py = s.getRow() * CELL_SIZE;

            g2.setColor(C_SPOT_BG);
            g2.fillRect(px, py, CELL_SIZE, CELL_SIZE);

            if (!s.isOccupied()) {
                g2.setColor(C_SPOT_BORDER);
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, GRID_METER_LIMIT,
                        new float[]{GRID_DASH_LENGTH}, 0.0f));
                g2.drawRect(px + GRID_SPOT_PAD, py + GRID_SPOT_PAD,
                        GRID_SPOT_RECT_SIZE, GRID_SPOT_RECT_SIZE);
                g2.setStroke(new BasicStroke(1));
            }
        }

        if (hoverSpot != null) {

            drawHoverSpot(g2);
        }
    }

    private void drawHoverSpot(final Graphics2D g2) {
        final int hx = hoverSpot.getCol() * CELL_SIZE;
        final int hy = hoverSpot.getRow() * CELL_SIZE;
        final TowerType selected = shopController.getSelectedTowerType();
        g2.setColor(C_HOVER);
        g2.fillRect(hx, hy, CELL_SIZE, CELL_SIZE);
        g2.setColor(Color.WHITE);
        g2.drawRect(hx, hy, CELL_SIZE, CELL_SIZE);

        if (!hoverSpot.isOccupied() && selected != null) {
                final int rPx = selected.getRange() * HOVER_RANGE_MULTIPLIER;
                g2.setColor(C_RANGE_FILL);
                g2.fillOval(hx +  HOVER_CICRLE_OFFSET - rPx,
                        hy + HOVER_CICRLE_OFFSET - rPx, rPx * 2, rPx *2);
                g2.setColor(C_RANGE_BORDER);
                g2.drawOval(hx + HOVER_CICRLE_OFFSET - rPx,
                        hy + HOVER_CICRLE_OFFSET - rPx, rPx * 2, rPx *2);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font(FONT_SANSSERIF, Font.BOLD, FONT_SIZE_SHOP_NAME));
                g2.drawString(selected.getCost() + "g",
                        hx + HOVER_COST_OFFSET_X, hy - HOVER_COST_OFFSET_Y);
            } else if (hoverSpot.isOccupied()) {
                drawOccupiedHover(g2, hx, hy);
            }
        }
        
    private void drawOccupiedHover(final Graphics2D g2, final int hx, final int hy) {
        final Tower t = hoverSpot.getTower();
        final int rPx = t.getRange() * HOVER_RANGE_MULTIPLIER;
        g2.setColor(C_RANGE_FILL_OCC);
        g2.fillOval((int) t.getPixelX() -rPx, (int) t.getPixelY() - rPx, rPx * 2, rPx * 2);
        g2.setColor(C_RANGE_BORDER_OCC);
        g2.drawOval((int) t.getPixelX() -rPx, (int) t.getPixelY() - rPx, rPx * 2, rPx * 2);

        final int upCost = t.getType().getCost() / COST_HALF_DIVISOR;
        final int sellCost = t.getType().getCost() / COST_HALF_DIVISOR;
        g2.setColor(Color.GREEN);
        g2.setFont(new Font(FONT_SANSSERIF, Font.BOLD, FONT_SIZE_INFO));
        g2.drawString("Up: " + upCost, hx, hy - HOVER_UP_OFFSET_Y);
        g2.setColor(Color.RED);
        g2.drawString("Sell: " + sellCost, hx, hy - HOVER_SELL_OFFEST_Y);
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
