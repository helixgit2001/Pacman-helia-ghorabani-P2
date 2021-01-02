package gameplay.events.dead;

import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.EventManager;

/**
 * Event allowing to display the image of the dead panoel
 */
public class EventPowerfulPacDie extends Event {
    private Entity entityOwned;

    protected EventPowerfulPacDie(Entity entityOwned, Entity entity, int time) {
        super(entity, time);
        this.entityOwned = entityOwned;
    }

    @Override
    public void handle() {
        entityOwned.getGraphicsComponent().getAnimationManager().setCurrentAnimation("mortpacnoel");
        EventManager.getEventManager().addEvent(new EventDeadAnim(entityOwned, entity, 100));
    }
}
