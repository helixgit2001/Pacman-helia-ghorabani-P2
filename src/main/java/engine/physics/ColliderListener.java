package engine.physics;

import engine.core_kernel.Entity;

/**
 * Interface defining the classes used to deal with collisions
 */
public interface ColliderListener {
    /**
     * methode triggered when two entity collisions
     * @param entity_owned
     * @param entity
     */
    public void onCollision(Entity entity_owned, Entity entity) throws InterruptedException;
}
