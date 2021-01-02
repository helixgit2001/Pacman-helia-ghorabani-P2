package engine.physics;

import engine.core_kernel.Component;
import engine.core_kernel.Entity;

/**
 * Class gathering all the physical properties of an entity
 */
public abstract class PhyComp implements ColliderListener, ExitListener, Component {
    public double speed;
    protected Collider collider;

    public PhyComp(double speed){
        this.speed = speed;
    }

    /**
     * Updates the position of an entity according to its speed and orientation
     * @param entity
     */
    @Override
    public void update(Entity entity){
        if (entity.getPositioning() == null) {
            return;
        }
        double direction =  entity.getPositioning() % 360;
        double radDirection = Math.toRadians((double)direction);

        double cosDir = Math.cos(radDirection);
        double sinDir = Math.sin(radDirection);

        double newPosX = speed * cosDir;
        double newPosY = speed * sinDir;

        double fx = entity.getPosition().getX() + newPosX;
        double fy = entity.getPosition().getY() + newPosY;

        entity.getPosition().setX(fx);
        entity.getPosition().setY(fy);
        collider.update(entity.getPosition());
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Collider getCollider() {
        return collider;
    }

    public double getSpeed(){
        return speed;
    }

    @Override
    public void onCollision(Entity entity_owned, Entity entity) throws InterruptedException {}

    @Override
    public void onExit(Entity entity_owned){}
}
