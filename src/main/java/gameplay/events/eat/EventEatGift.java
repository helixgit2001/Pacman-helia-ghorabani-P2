package gameplay.events.eat;

import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.Map;
import engine.physics.Position;
import engine.sound.SoundManager;

/**
 * Event managing the action eat a gift (gift)
 */
public class EventEatGift extends Event {
    private PacmanModel pacmanModel;
    private Map map;
    private final int[] scoretoadd = new int[4];

    public EventEatGift(PacmanModel pacmanModel, Entity entity, Map map) throws InterruptedException {
        super(entity);
        this.pacmanModel = pacmanModel;
        this.map = map;
        scoretoadd[0] = 1000;
        scoretoadd[1] = 2000;
        scoretoadd[2] = 3000;
        scoretoadd[3] = 4000;


    }

    @Override
    public void handle() {
        Position position = map.getPositionEntity(entity);
        if(position == null) return;
        int temp = (int) (Math.random() * 4);
        pacmanModel.addScore(scoretoadd[temp]);
        map.deleteEntity(position, entity);
        entity.getGraphicsComponent().getCurrentImage().setImage(null);
        entity = new Entity();
        SoundManager.getInstance().addSound("pacman_eatfruit.wav", "gift", false, 0.2f, 0L);


    }
}
