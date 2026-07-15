package it.unibo.TowerSiege.application;

import it.unibo.TowerSiege.controller.maincontroller.api.MainController;
import it.unibo.TowerSiege.controller.maincontroller.impl.MainControllerImpl;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamemodel.impl.GameModelImpl; 
import it.unibo.TowerSiege.view.gameview.api.GameView;
import it.unibo.TowerSiege.view.gameview.impl.GameViewImpl;

public class TowerSiege {

   public static void main(final String[] args) {
    System.out.println("Tower Defense Game Starting...");
    final String mapPath = args.length > 0 ? args[0] : null;
    final GameModel model = new GameModelImpl(mapPath);
    final GameView view = new GameViewImpl();
    final MainController controller = new MainControllerImpl(model, view);
    controller.start();
   }
}
