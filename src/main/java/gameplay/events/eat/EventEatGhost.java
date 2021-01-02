package gameplay.events.eat;

import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.sound.SoundManager;

/**
 * Event handling the eating a ghost action
 */
public class EventEatGhost extends Event {
    private PacmanModel pacmanModel;

    public EventEatGhost(PacmanModel pacmanModel, Entity entity) throws InterruptedException {
        super(entity);
        this.pacmanModel = pacmanModel;
    }

    @Override
    public void handle() {
        pacmanModel.addScore(150);
        SoundManager.getInstance().addSound("pacman_eatghost.wav", "ghost", false, 0.2f, 0L);
    }
}
