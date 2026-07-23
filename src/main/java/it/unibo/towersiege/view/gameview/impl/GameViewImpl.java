package it.unibo.towersiege.view.gameview.impl;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.controller.gamecontroller.api.GameController;
import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.view.gameover.GameOverPanel;
import it.unibo.towersiege.view.gamepanel.GamePanel;
import it.unibo.towersiege.view.gameview.api.GameView;
import it.unibo.towersiege.view.levelselect.LevelSelectPanel;
import it.unibo.towersiege.view.pausemenu.PauseMenuDialog;
import it.unibo.towersiege.view.startmenu.StartMenuPanel;

/**
 * Implementation of the GameView interface.
 * Manages the Swing UI frames and panels for the game.
 */
public final class GameViewImpl implements GameView {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JFrame menuFrame;
    private JFrame levelFrame;
    private JFrame gameFrame;

    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;
    private PauseMenuDialog pauseDialog;

    /** {@inheritDoc} */
    @Override
    public void displayWelcome() {
        // Optional startup effect
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public void displayGameState(final GameModel model, final MainController mc,
            final GameController gc, final MapController mapC,
            final ShopController sc, final AbilityController ac) {
        if (gameFrame == null) {
            initGui(model, gc, mapC, sc, ac);
        } else {
            gamePanel.setModel(model);
            gamePanel.setMapController(mapC);
            gamePanel.setShopController(sc);
            gamePanel.repaint();

            if (model.getState() == GameState.DEFEAT
                    || model.getState() == GameState.VICTORY) {
                if (gameOverPanel == null) {
                    displayEndGame(model.getState(), mc, model);
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

    private void initGui(final GameModel model, final GameController gc,
            final MapController mapC, final ShopController sc,
            final AbilityController ac) {
        gameFrame = new JFrame("TowerSiege - Livello " + model.getCurrentLevel());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        gamePanel = new GamePanel(model, mapC, sc);
        gamePanel.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        gameFrame.add(layeredPane);

        final InputMap im = gameFrame.getRootPane()
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
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

    /** {@inheritDoc} */
    @Override
    public void displayEndGame(final GameState state, final MainController mc, final GameModel model) {
        if (gameFrame == null || gameOverPanel != null) {
            return;
        }
        gameOverPanel = new GameOverPanel(state, mc, model);
        gameOverPanel.setBounds(0, 0, WIDTH, HEIGHT);
        gameFrame.getLayeredPane().add(gameOverPanel, JLayeredPane.PALETTE_LAYER);
        gameFrame.getLayeredPane().repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void showPauseMenu(final MainController mc, final GameController gc) {
        if (gameFrame == null) {
            return;
        }
        if (pauseDialog == null) {
            pauseDialog = new PauseMenuDialog(gameFrame, mc, gc);
        }
        pauseDialog.show();
    }

    /** {@inheritDoc} */
    @Override
    public void hidePauseMenu() {
        if (pauseDialog != null) {
            pauseDialog.hide();
            pauseDialog = null;
        }
    }

    /** {@inheritDoc} */
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
