package gameplay.events.eat;

import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.EventManager;
import engine.core_kernel.Map;
import engine.phy.Position;
import engine.sound.SoundManager;

/**
 * Event managing the action to eat a Christmas tree
 */
public class EventEatSpecialCoin extends Event {
    private PacmanModel pacmanModel;
    private Entity entityowned;
    private Map map;

    public EventEatSpecialCoin(PacmanModel pacmanModel, Entity entity, Entity entityowned, Map map) throws InterruptedException {
        super(entity);
        this.pacmanModel = pacmanModel;
        this.map = map;
        this.entityowned = entityowned;
    }

    @Override
    public void handle() {
        Position position = map.getPositionEntity(entity);
        if(position == null) return;

        map.deleteEntity(position, entity);
        entity.getGraphicsComponent().getCurrentImage().setImage(null);
        pacmanModel.addScore(100);
        if(!pacmanModel.isRed()){
            pacmanModel.setRed(true);
            SoundManager.getInstance().stopAllSound();
            SoundManager.getInstance().addSound("isNoel.wav", "isNoel", false, 0.8f, 0L);
            EventManager.getEventManager().addEvent(new EventEndPower(pacmanModel, entityowned, 660));



        }
    }
}
