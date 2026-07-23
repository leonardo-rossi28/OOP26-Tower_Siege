package it.unibo.towersiege.view.gamepanel;

import javax.swing.JPanel;

import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
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
 * Main panel used to draw the game scene and handle UI interactions.
 */
public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 50;
    private static final int HUD_BAR_HEIGHT = 40;
    private static final int SHOP_CARD_WIDTH = 100;
    private static final int SHOP_CARD_HEIGHT = 70;
    private static final int SHOP_GAP = 8;
    private static final int SHOP_MARGIN = 12;
    private static final int SHOP_ROUND_OUTER = 10;
    private static final int SHOP_ROUND_INNER = 8;
    private static final int SHOP_SELECTION_PADDING = 2;
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
    private static final int SHOP_PANEL_PADDING = 10;
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
    private static final int FIRE_OVERLAY_R = 255;
    private static final int FIRE_OVERLAY_G = 100;
    private static final int FIRE_TEXT_G = 200;
    private static final int FREEZE_OVERLAY_G = 200;
    private static final int FREEZE_OVERLAY_B = 255;
    private static final int FREEZE_TEXT_R = 200;
    private static final int ANIM_MAX_TICKS = 60;
    private static final int ANIM_ALPHA_DIVISOR = 2;
    private static final int EFFECT_FONT_SIZE = 48;
    private static final int ALPHA_MAX = 255;
    private static final String FONT_SERIF = "Serif";
    private static final String FIRE_TEXT = "PIOGGIA DI FUOCO!";
    private static final String FREEZE_TEXT = "GELO GLOBALE!";

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
    private static final Color C_SHOP_CARD_BORDER = new Color(80, 70, 60);
    private static final String FONT_SANSSERIF = "SansSerif";

    private GameModel model;
    private MapController mapController;
    private ShopController shopController;
    private BuildingSpot hoverSpot;

    /**
     * Constructs a new GamePanel.
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
     * Sets the game model.
     * 
     * @param m the game model
     */
    public void setModel(final GameModel m) {
        this.model = m;
    }

    /**
     * Sets the map controller.
     * 
     * @param c the map controller
     */
    public void setShopController(final ShopController c) {
        this.shopController = c;
    }

    /**
     * Sets the controller for map management.
     * 
     * @param c the map controller to be assigned
     */
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

                if (e.getButton() == MouseEvent.BUTTON3) {
                    final BuildingSpot spot = model.getMap().getSpotAt(col, row);
                    if (spot != null) {
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
                    if (e.getX() >= cx && e.getX() <= cx + SHOP_CARD_WIDTH
                            && e.getY() >= shopY && e.getY() <= shopY + SHOP_CARD_HEIGHT) {
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

    /** 
     * {@inheritDoc} 
     */
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
    }

    private void drawMap(final Graphics2D g2) {
        g2.setColor(C_MAP_GRASS);
        g2.fillRect(0, 0, getWidth(), getHeight());

        final int[][] grid = model.getMap().getGrid();
        g2.setColor(C_MAP_PATH);
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    g2.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        drawDecorations(g2);
    }

    private void drawDecorations(final Graphics g2) {
        final List<double[]> decorations=model.getMap().getDecorations();
        for(final double[] dec : decorations) {
            final int col = (int) dec[0];
            final int row = (int) dec[1];
            final int type = (int) dec[2];
            final int px = col * CELL_SIZE;
            final int py = row * CELL_SIZE;

            Image img = null;
            switch (type) {
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

            if(img != null) {
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
                g2.fillOval(hx + HOVER_CICRLE_OFFSET - rPx,
                        hy + HOVER_CICRLE_OFFSET - rPx, rPx * 2, rPx * 2);
                g2.setColor(C_RANGE_BORDER);
                g2.drawOval(hx + HOVER_CICRLE_OFFSET - rPx,
                        hy + HOVER_CICRLE_OFFSET - rPx, rPx * 2, rPx * 2);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font(FONT_SANSSERIF, Font.BOLD, FONT_SIZE_SHOP_NAME));
                g2.drawString(selected.getCost() + "g",
                        hx + HOVER_COST_OFFSET_X, hy - HOVER_COST_OFFSET_Y);
            } 
            else if (hoverSpot.isOccupied()) {
                drawOccupiedHover(g2, hx, hy);
            }
        }
        
    private void drawOccupiedHover(final Graphics2D g2, final int hx, final int hy) {
        final Tower t = hoverSpot.getTower();
        final int rPx = t.getRange() * HOVER_RANGE_MULTIPLIER;
        g2.setColor(C_RANGE_FILL_OCC);
        g2.fillOval((int) t.getPixelX() - rPx, (int) t.getPixelY() - rPx,
                rPx * 2, rPx * 2);
        g2.setColor(C_RANGE_BORDER_OCC);
        g2.drawOval((int) t.getPixelX() - rPx, (int) t.getPixelY() - rPx,
                rPx * 2, rPx * 2);

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

            g2.setColor(C_SHADOW);
            g2.fillOval(cx - TOWER_SHADOW_X_OFFSET, cy - TOWER_SHADOW_Y_OFFSET,
                    TOWER_SHADOW_W, TOWER_SHADOW_H);

            Image img = ImageLoader.getSpTowerBasic();
            switch (t.getType()) {
                case SNIPER:
                    img = ImageLoader.getSpTowerSniper();
                    break;
                case RAPID:
                    img = ImageLoader.getSpTowerRapid();
                break;
                case ICE:
                    img = ImageLoader.getSpTowerIce();
                    break;
                default:
                 break;
            }

            if (img != null) {
                g2.drawImage(img, cx - SPRITE_OFFSET, cy- SPRITE_TOWER_Y_OFFSET,
                        SPRITE_SIZE, SPRITE_SIZE, null);
            } else {
                g2.setColor(Color.BLUE);
                g2.fillRect(cx - SHOP_PANEL_PADDING -SHOP_TEXT_X_OFFSET,
                        cy - SHOP_PANEL_PADDING - SHOP_TEXT_X_OFFSET,
                        HEALTH_BAR_WIDTH, HEALTH_BAR_WIDTH);
            }        

            g2.setColor(Color.WHITE);
            g2.setFont(new Font(FONT_SANSSERIF, Font.BOLD, FONT_SIZE_LVL));
            g2.drawString("Lv" + t.getLevel(), cx - LVL_OFFSET_X, cy + LVL_OFFSET_Y);
        }
    }

    private void drawEnemies(final Graphics2D g2) {
        final List<Enemy> enemies = model.getActiveEnemies();

        for (final Enemy e : enemies) {
            final int ex = (int) e.getPixelX() - SPRITE_OFFSET;
            final int ey = (int) e.getPixelY() - SPRITE_OFFSET;

            g2.setColor(C_ENEMY_SHADOW);
            g2.fillOval(ex + SHADOW_OFFSET_X, ey + SHADOW_OFFSET_Y, SHADOW_W, SHADOW_H);

            Image img = ImageLoader.getSpEnemyBasic();
            switch (e.getType()) {
                case FAST:
                    img = ImageLoader.getSpEnemyFast();
                    break;
                case TANK:
                img = ImageLoader.getSpEnemyTank();
                    break;
                default:
                    break;
            }

            if (img != null) {
                g2.drawImage(img, ex, ey, SPRITE_SIZE, SPRITE_SIZE, null);
            } else {
                g2.setColor(Color.RED);
                g2.fillOval(ex, ey, SPRITE_SIZE, SPRITE_SIZE);
            }

            if (e.getHitFlashTicks() > 0) {
                g2.setColor(C_HIT_FLASH);
                g2.fillOval(ex + HIT_FLASH_OFFSET, ey + HIT_FLASH_OFFSET,
                        HIT_FLASH_SIZE, HIT_FLASH_SIZE);
            }

            drawEnemyHealthBar(g2, e, ex, ey);
        }
    }
    private void drawEnemyHealthBar(final Graphics2D g2, final Enemy e,
            final int ex, final int ey) {
            final int maxH = e.getMaxHealth();
            final int curH = e.getHealth();
            final int bx = ex + HEALTH_BAR_X_OFFSET;
            final int by = ey -HEALTH_BAR_Y_OFFSET;
            g2.setColor(Color.RED);
            g2.fillRect(bx, by, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
            g2.setColor(Color.GREEN);
            g2.fillRect(bx, by,
                    (int) (HEALTH_BAR_WIDTH * ((double) curH / maxH)), HEALTH_BAR_HEIGHT);
            g2.setColor(Color.BLACK);
            g2.drawRect(bx, by, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);    
    }

    private void drawProjectiles(final Graphics2D g2) {
        for (final Projectile p : model.getProjectiles()) {
            Color pc = Color.YELLOW;
            int size = PROJ_SIZE_DEFAULT;
            if (p.getSourceTowerType() == TowerType.SNIPER) {
                pc = Color.RED;
                size = PROJ_SIZE_SNIPER;
            } else if (p.getSourceTowerType() == TowerType.ICE) {
                pc = Color.CYAN;
                size = PROJ_SIZE_ICE;
            } else if (p.getSourceTowerType() == TowerType.RAPID) {
                pc = Color.ORANGE;
                size = PROJ_SIZE_RAPID;
            }
            g2.setColor(pc);
            g2.fillOval((int) p.getX() - size / 2, (int) p.getY() - size / 2,
                    size, size);
        }
    }

    private void drawVisualEffects(final Graphics2D g2) {
        final int fAnim = model.getFireAnimTicks();
        if (fAnim > 0) {
            final int alpha = fAnim * ALPHA_MAX / ANIM_MAX_TICKS;
            g2.setColor(new Color(FIRE_OVERLAY_R, FIRE_OVERLAY_G, 0, alpha / ANIM_ALPHA_DIVISOR));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(FIRE_OVERLAY_R, FIRE_TEXT_G, 0, alpha / ANIM_ALPHA_DIVISOR));
            g2.setFont(new Font(FONT_SERIF, Font.BOLD, EFFECT_FONT_SIZE));
            final FontMetrics fm = g2.getFontMetrics();
            g2.drawString(FIRE_TEXT, (getWidth() - fm.stringWidth(FIRE_TEXT)) / 2, getHeight() / 2);
        }

        final int frAnim = model.getFreezeAnimTicks();
        if (frAnim > 0) {
            final int alpha = frAnim * ALPHA_MAX / ANIM_MAX_TICKS;
            g2.setColor(new Color(0, FREEZE_OVERLAY_G, FREEZE_OVERLAY_B, alpha / ANIM_ALPHA_DIVISOR));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(FREEZE_TEXT_R, FREEZE_OVERLAY_B, FREEZE_OVERLAY_B, alpha));
            g2.setFont(new Font(FONT_SERIF, Font.BOLD, EFFECT_FONT_SIZE));
            final FontMetrics fm = g2.getFontMetrics();
            g2.drawString(FREEZE_TEXT, (getWidth() - fm.stringWidth(FREEZE_TEXT)) / 2, getHeight() / 2);
        }
    }

    private void drawHUD(final Graphics2D g2) {
        final Player p = model.getPlayer();

        g2.setColor(C_UI);
        g2.fillRect(0, 0, getWidth(), HUD_BAR_HEIGHT);
        g2.setColor(C_HUD_LINE);
        g2.drawLine(0, HUD_BAR_HEIGHT, getWidth(), HUD_BAR_HEIGHT);

        g2.setFont(new Font(FONT_SANSSERIF, Font.BOLD, FONT_SIZE_HUD));
        g2.setColor(Color.WHITE);
        g2.drawString("Base HP: " + p.getBaseHealth(), HUD_TEXT_X_HP, HUD_TEXT_Y);
        g2.setColor(C_GOLD);
        g2.drawString("Coins: " + p.getCoins(), HUD_TEXT_X_COINS, HUD_TEXT_Y);
        g2.setColor(Color.CYAN);
        g2.drawString("Score: " + model.getScore().getTotal(),
                HUD_TEXT_X_SCORE, HUD_TEXT_Y);
        g2.setColor(C_WAVE_TEXT);

        final String wTxt = model.isWaveInProgress() ? "In corso" : "Attesa";
        g2.drawString("Ondata: " + model.getCurrentWave() + "/"
                + model.getTotalWaves() + " ("+ wTxt + ")",
                HUD_TEXT_X_WAVE, HUD_TEXT_Y);

        g2.setFont(new Font(FONT_SANSSERIF, Font.PLAIN, FONT_SIZE_SHOP_NAME));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("W=Avvia Ondata | F=Fuoco ("
                + (model.getFireCooldown() / SECONDS_DIVISOR)
                + "s) | G=Gelo ("
                + (model.getFreezeCooldown() / SECONDS_DIVISOR)
                + "s) | ESC=Pausa",
                HUD_TEXT_X_HP, HUD_SUBTEXT_Y);

        drawShopPanel(g2, p);
    }

    private void drawShopPanel(final Graphics2D g2, final Player p) {
        final TowerType[] types = TowerType.values();
        final int total = types.length * (SHOP_CARD_WIDTH + SHOP_GAP) - SHOP_GAP;
        final int sx = (getWidth() - total) / 2;
        final int sy = getHeight() - SHOP_CARD_HEIGHT - SHOP_MARGIN;

        g2.setColor(C_SHOP_BG);
        g2.fillRoundRect(sx - SHOP_PANEL_PADDING, sy - SHOP_PANEL_PADDING,
                total + SHOP_PANEL_EXTRA_W, SHOP_PANEL_EXTRA_H,
                SHOP_PANEL_ROUND, SHOP_PANEL_ROUND);

        for (int i = 0; i < types.length; i++) {
            final TowerType t = types[i];
            final int cx = sx + i * (SHOP_CARD_WIDTH + SHOP_GAP);
            
            if (shopController.getSelectedTowerType() == t) {
                g2.setColor(C_SHOP_SELECTED);
                g2.fillRoundRect(cx - SHOP_SELECTION_PADDING,
                        sy - SHOP_SELECTION_PADDING,
                        SHOP_CARD_WIDTH + SHOP_SELECTION_EXTRA,
                        SHOP_CARD_HEIGHT + SHOP_SELECTION_EXTRA,
                        SHOP_ROUND_OUTER, SHOP_ROUND_OUTER);
            }

            g2.setColor(C_SHOP_CARD_BG);
            g2.fillRoundRect(cx, sy, SHOP_CARD_WIDTH, SHOP_CARD_HEIGHT,
                    SHOP_ROUND_INNER, SHOP_ROUND_INNER);
            g2.setColor(C_SHOP_CARD_BORDER);
            g2.drawRoundRect(cx, sy, SHOP_CARD_WIDTH, SHOP_CARD_HEIGHT,
                    SHOP_ROUND_INNER, SHOP_ROUND_INNER);

            g2.setColor(p.getCoins() >= t.getCost() ? Color.WHITE : Color.GRAY);
            g2.setFont(new Font(FONT_SANSSERIF, Font.BOLD, FONT_SIZE_SHOP_NAME));
            g2.drawString(t.name(), cx + SHOP_TEXT_X_OFFSET, sy + SHOP_TEXT_Y_NAME);
            g2.setColor(C_GOLD);
            g2.drawString(t.getCost() + "g",
                    cx + SHOP_TEXT_X_OFFSET, sy + SHOP_TEXT_Y_COST);

            g2.setFont(new Font(FONT_SANSSERIF, Font.PLAIN, FONT_SIZE_SHOP_STAT));
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("Dmg: " + t.getDamage(),
                    cx + SHOP_TEXT_X_OFFSET, sy + SHOP_TEXT_Y_DMG);
            g2.drawString("Rng: " + t.getRange(),
                    cx + SHOP_TEXT_X_OFFSET, sy + SHOP_TEXT_Y_RNG);
        }
    }
}
