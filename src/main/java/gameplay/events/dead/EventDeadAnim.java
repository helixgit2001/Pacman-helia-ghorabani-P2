package gameplay.events.dead;

import gameplay.model.GameModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.GameHelper;
import engine.sound.SoundManager;
import gameplay.scene.MenuController;
import engine.ui.SceneManager;

/**
 * Event rebooting the game after death
 */
public class EventDeadAnim extends Event {
    private final Entity entityOwned;
    protected EventDeadAnim(Entity entityOwned, Entity entity, int time) {
        super(entity, time);
        this.entityOwned = entityOwned;
    }

    @Override
    public void handle() {
        GameModel.getInstance().resetPacMan();
        System.out.println("Game Over!");
        SceneManager.getInstance().setSceneView(new MenuController());
        SoundManager.getInstance().stopAllSound();
        GameHelper.setGameManager(null);
    }
}
