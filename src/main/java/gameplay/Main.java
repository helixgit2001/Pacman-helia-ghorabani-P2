package gameplay;

import engine.core_kernel.GameHelper;
import gameplay.scene.MenuController;

/**
 * Class used to launch the Helix game
 */
public class Main {

    public static void main(String[] args) {
        GameHelper.setTitle("PACMAN");
        MenuController menuController = new MenuController();
        GameHelper.setSceneController(menuController);
        GameHelper.setPathIcon("/Image/pacman/pacmanRight.png");
        GameHelper.startGame();
    }
}
