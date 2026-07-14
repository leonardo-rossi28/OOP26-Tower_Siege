package it.unibo.TowerSiege.view.gameview.impl;

import it.unibo.TowerSiege.controller.gamecontroller.api.GameController;
import it.unibo.TowerSiege.controller.maincontroller.api.MainController;
import it.unibo.TowerSiege.controller.mapcontroller.api.MapController;
import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.view.gameover.GameOverPanel;
import it.unibo.TowerSiege.view.gamepanel.GamePanel;
import it.unibo.TowerSiege.view.gameview.api.GameView;
import it.unibo.TowerSiege.view.levelselect.LevelSelectPanel;
import it.unibo.TowerSiege.view.pausemenu.PauseMenuDialog;
import it.unibo.TowerSiege.view.startmenu.StartMenuPanel;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class GameViewImpl implements GameView{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JFrame menuFrame;
    private JFrame levelFrame;
    private JFrame gameFrame;

    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;
    private PauseMenuDialog pauseDialog;

    @Override
    public void displayWelcome() {
        //Optional startup effect
    }

    @Override
    public void showStartMenu(final MainController c) {
        if (menuFrame != null) {
            menuFrame.dispose();
        }
        menuFrame = new JFrame("TowerSiege - Menu Principale");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);

        final StartMenuPanel p = new StartMenuPanel(c, menuFrame);
        menuFrame.add(p);
        menuFrame.setSize(WIDTH, HEIGHT);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

    @Override
    public void showLevelSelect(final MainController c, final GameModel model) {
        if (levelFrame != null) {
            levelFrame.dispose();
        }
        levelFrame = new JFrame("Mappa dei Livelli");
        levelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelFrame.setResizable(false);
        
        final LevelSelectPanel panel = new LevelSelectPanel(c, model);
        levelFrame.add(panel);
        levelFrame.pack();
        levelFrame.setLocationRelativeTo(null);
        levelFrame.setVisible(true);
    }

    @Override
    public void displayGameState(final GameModel model, final GameController gc, final MapController mapC, final ShopController sc, final AbilityController ac) {
        if (gameFrame == null) {
            initGui(model, gc, mapC, sc, ac);
        } else {
            gamePanel.setModel(model); 
            gamePanel.setMapController(mapC);
            gamePanel.setShopController(sc);
            gamePanel.repaint();

            if (model.getState() == GameState.DEFEAT || model.getState() == GameState.VICTORY) {
                if (gameOverPanel == null) {
                    displayEndGame(model.getState());
                }
                gameOverPanel.repaint();
            } else if (gameOverPanel != null) {
                // If restarted, remove game over panel
                gameFrame.getLayeredPane().remove(gameOverPanel);
                gameOverPanel = null;
                gameFrame.getLayeredPane().repaint();
            }
        }
    }

    private void initGui(final GameModel model, final GameController gc, final MapController mapC, final ShopController sc, final AbilityController ac) {
        gameFrame = new JFrame("TowerSiege - Livello " + model.getCurrentLevel());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        gamePanel = new GamePanel(model, mapC, sc);
        gamePanel.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        gameFrame.add(layeredPane);

        final InputMap im = gameFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        final ActionMap am = gameFrame.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "p");
        am.put("p", act(gc::togglePause));
        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, 0), "w");
        am.put("w", act(ac::forceNextWave));
        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, 0), "f");
        am.put("f", act(ac::triggerRainOfFire));
        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, 0), "g");
        am.put("g", act(ac::triggerGlobalFreeze));

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.requestFocusInWindow();
    }

    private AbstractAction act(final Runnable r) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                r.run();
            }
        };
    }

    @Override
    public void displayEndGame(final GameState state) {
        if (gameFrame == null || gameOverPanel != null) {
            return;
        }
        gameOverPanel = new GameOverPanel(state);
        gameOverPanel.setBounds(0, 0, WIDTH, HEIGHT);
        gameFrame.getLayeredPane().add(gameOverPanel, JLayeredPane.PALETTE_LAYER);
        gameFrame.getLayeredPane().repaint();
    }

    @Override
    public void showPauseMenu(final MainController mc, final GameController gc) {
        if (gameFrame == null) return;
        if (pauseDialog == null) {
            pauseDialog = new PauseMenuDialog(gameFrame, mc, gc);
        }
        pauseDialog.show();
    }

    @Override
    public void hidePauseMenu() {
        if (pauseDialog != null) {
            pauseDialog.hide();
            pauseDialog = null;
        }
    }

    @Override
    public void closeGameFrame() {
        if (gameFrame != null) {
            gameFrame.dispose();
            gameFrame = null;
            gamePanel = null;
            gameOverPanel = null;
        }
        if (levelFrame != null) {
            levelFrame.dispose();
            levelFrame = null;
        }
    }
}
