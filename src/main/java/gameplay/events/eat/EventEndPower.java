package gameplay.events.eat;

import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.sound.SoundManager;

/**
 * Event repassant pacnoel in the pacman state
 */
public class EventEndPower extends Event {
    private PacmanModel pacmanModel;

    public EventEndPower(PacmanModel pacmanModel, Entity entity, int time) {
        super(entity, time);
        this.pacmanModel = pacmanModel;
    }

    @Override
    public void handle() {
        SoundManager.getInstance().stopAllSound();
        pacmanModel.setRed(false);
    }
}
