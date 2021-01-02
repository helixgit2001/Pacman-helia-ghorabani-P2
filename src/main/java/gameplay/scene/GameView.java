package gameplay.scene;
import engine.ui.View;

/**
 * View corresponding to the game
 */
public class GameView extends View {

    public GameView() {
        init();
    }

    @Override
    public void init() {
        setStyle("-fx-background-color: #000000;");
    }
}
