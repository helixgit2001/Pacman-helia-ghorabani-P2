package gameplay.events;

import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import gameplay.scene.GameViewController;

/**
 * Event allowing to change level at the end of a level
 */
public class EventChangeLevel extends Event {
    private final GameViewController controller;

    public EventChangeLevel(Entity entity, GameViewController controller, int time) {
        super(entity, time);
        this.controller = controller;
    }

    @Override
    public void handle() {
        controller.getGameView().getChildren().clear();
        controller.setNewLevel();
        controller.setEndLevel(false);
        controller.getGameManager().setMap(controller.getLevelGenerator().getMap());
        controller.init();
    }
}
