package gameplay.py;

import gameplay.EntityType;
import engine.core_kernel.Entity;
import engine.physics.Collider;
import engine.physics.PhyComp;
import engine.physics.Position;

/**
 * class of physical components of phantoms
 */
public class GhostPhy extends PhyComp {
    public GhostPhy(double speed, Collider collider) {
        super(speed);
        this.collider = collider;
    }

    /**
     * Collision detection in the ghost collider box
     * @param entity_owned
     * @param entity
     */
    @Override
    public void onCollision(Entity entity_owned, Entity entity){
        if (entity_owned.getPositioning() == null)
            return;

        if(entity.getName().equals(EntityType.WALL.name)){
            updatePositionEntityPosition(entity_owned, entity);
        }
    }

    /**
     * Box collider of ghosts released
     * @param entity_owned
     */
    @Override
    public void onExit(Entity entity_owned){
        moveBack(entity_owned);
    }

    /**
     * update of the positions of the ghosts according to the displacement chosen by the AI
     * @param ownedPositioning
     * @param entity
     */
    private void updatePositionEntityPosition(Entity ownedPositioning, Entity entity){
        if(ownedPositioning.getPositioning() != null){
            double x = ownedPositioning.getPosition().getX(), y = ownedPositioning.getPosition().getY();
            double new_x = x, new_y = y;
            if(ownedPositioning.getPositioning().equals(Displacement.RIGHT.orientation)){
                new_x = x - (x+ownedPositioning.getGraphicsComponent().getWidth() - entity.getPosition().getX());
            } else if (ownedPositioning.getPositioning().equals(Displacement.LEFT.orientation)) {
                new_x = x + (entity.getPosition().getX() - x+ownedPositioning.getGraphicsComponent().getWidth());
            } else if (ownedPositioning.getPositioning().equals(Displacement.UP.orientation)) {
                new_y = y + (entity.getPosition().getY() - y+ownedPositioning.getGraphicsComponent().getHeight());
            } else if (ownedPositioning.getPositioning().equals(Displacement.DOWN.orientation)) {
                new_y = y - (y+ownedPositioning.getGraphicsComponent().getHeight() - entity.getPosition().getY());
            }
            ownedPositioning.setPosition(new Position(new_x, new_y));
            ownedPositioning.setPositioning(Displacement.NOTHING.orientation);
        }

    }

    /**
     * Half turn for ghosts
     * @param positioning
     */
    private void moveBack(Entity positioning){
        if(positioning.getPositioning() == null) return;
        positioning.setPositioning((positioning.getPositioning() + 180.0) % 360);
        positioning.getPhysicsComponent().update(positioning);
        positioning.setPositioning((positioning.getPositioning() - 180.0) % 360);
    }

    /**
     * movement of the ghosts ahead of them
     * @param entityOwned
     */
    private void moveForward(Entity entityOwned){
        if(entityOwned.getPositioning() == null) return;
        entityOwned.getPhysicsComponent().update(entityOwned);
    }
}
