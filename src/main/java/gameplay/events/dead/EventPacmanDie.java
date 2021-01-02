package gameplay.events.dead;

import gameplay.Score;
import gameplay.model.PacmanModel;
import gameplay.scene.GameViewController;
import engine.core_kernel.*;
import engine.sound.SoundManager;

/**
 * Event that triggers the death animation and sets a new high score if the record is broken
 */
public class EventPacmanDie extends Event {
    private final PacmanModel pacmanModel;
    private final Map map;
    private final Entity entityOwned;
    private final Score score = GameViewController.getScore();

    public EventPacmanDie(PacmanModel pacmanModel, Entity entity, Entity entityOwned, Map map) {
        super(entity);
        this.pacmanModel = pacmanModel;
        this.map = map;
        this.entityOwned = entityOwned;
    }

    @Override
    public void handle() {
        if(Integer.parseInt(score.getScoreFile() ) < pacmanModel.getScore()){
            GameViewController.setSessionBestScore(pacmanModel.getScore());
            score.setScoreFile(pacmanModel.getScore()+"");
        }
        entityOwned.getGraphicsComponent().getAnimationManager().setCurrentAnimation("dead");
        SoundManager.getInstance().stopAllSound();
        SoundManager.getInstance().addSound("dead.wav", "dead", false, 0.2f, 400L);
        EventManager.getEventManager().addEvent(new EventPowerfulPacDie(entityOwned, entity, 200));
    }
}
