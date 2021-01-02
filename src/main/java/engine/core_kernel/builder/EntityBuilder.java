package engine.core_kernel.builder;

import engine.core_kernel.Entity;
import engine.phy.Position;

/**
 * abstract class as a model for all builders
 */
public abstract class EntityBuilder {
    protected Entity entity;

    public Entity getEntity() {
        return entity;
    }

    /**
     * returns a new empty entity
     */
    public void createEntity() {
        entity = new Entity();
    }

    /**
     * Setting the position of the entity
     * @param position
     */
    public abstract void buildPosition(Position position);

    /**
     * Entity name setting
     */
    public abstract void buildName();

    /**
     * Setting the entity orientation
     */
    public abstract void buildPosition();

    /**
     * Entity controller setting
     */
    public abstract void buildContComp();

    /**
     * Parameterization of the physical component of the entity
     * @param length
     * @param width
     */
    public abstract void buildPhysComp(double length, double width);

    /**
     * Setting the graphical component of the entity
     * @param length
     * @param width
     */
    public abstract void buildGraphComp(double length, double width);
}

