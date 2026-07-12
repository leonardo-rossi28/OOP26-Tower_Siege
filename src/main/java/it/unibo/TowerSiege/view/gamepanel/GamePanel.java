package it.unibo.TowerSiege.view.gamepanel;

import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.view.utils.ImageLoader;
import it.unibo.TowerSiege.controller.mapcontroller.api.MapController;
import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.tower.api.Tower;

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
                        map.controller.sellTowerAtSpot(spot);
                    }
                    return;
                }

                final TowerType[] types = TowerType.values();
                final int cw = 100;
                final int gap = 8;
                final int total = types.length * (cw + gap) - gap;
                final int sx = (getWidth() - total) / 2;
                final int sy = getHeigth() - 70 - 12;

                for (int i = 0; i < types.length; i++) {
                    final int cx = sx + i * (cw + gap);
                    if(e.getX() >= cx && e.getX() <= cx + cw && e.getY() >= sy && e.getY() <= sy + 70) {
                        shopController.setSelectedTowerType(types[i]);
                        repaint();
                        return;
                    }
                }

                final BuildingSpot spot = model.getMap().getSpotAt(col, row,);
                if (spot != null) {
                    mapController.interactiveWithSpot(spot);
                }
            }
        });
    }
}
