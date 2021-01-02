package engine.ui;


import javafx.scene.Parent;
import engine.core_kernel.Map;

public interface SceneController {
    /**
     * Initialise scene
     */
    void init();

    /**
     * Function called at each loop of the game. Updates the scene
     * @param map current map to update
     */
    void update(Map map);
    Parent getView();
}
