package gameplay.events;

import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import gameplay.scene.GameViewController;
import engine.sound.SoundManager;

/**
 * Event allowing to change level at the end of a level
 */
public class EventWinAllLevel extends Event {
    private final GameViewController controller;

    public EventWinAllLevel(Entity entity, GameViewController controller, int time) {
        super(entity, time);
        this.controller = controller;
    }

    @Override
    public void handle() {
        SoundManager.getInstance().addSound("endLevel.wav", "end", false, 0.2f, 0L);
        controller.getGameView().getChildren().clear();
        controller.setEndLevel(false);
        controller.getBackMenuWin();
    }
}
