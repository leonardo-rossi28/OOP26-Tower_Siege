package it.unibo.towersiege.application;

import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.controller.maincontroller.impl.MainControllerImpl;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersiege.view.gameview.api.GameView;
import it.unibo.towersiege.view.gameview.impl.GameViewImpl;

/**
 * Main entry point for the Tower Defense game.
 */
public final class TowerSiege {

   private TowerSiege() {
   }

   /**
    * Starts the game.
    * 
    * @param args optional: path to a custom map JSON
    */
   public static void main(final String[] args) {
      final String mapPath = args.length > 0 ? args[0] : null;
      final GameModel model = new GameModelImpl(mapPath);
      final GameView view = new GameViewImpl();
      final MainController controller = new MainControllerImpl(model, view);
      controller.start();
   }
}
