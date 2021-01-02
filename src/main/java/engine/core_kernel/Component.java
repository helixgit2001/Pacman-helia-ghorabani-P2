package engine.core_kernel;

/**
 * Interface representing a component of the entity
 */
public interface Component {
    /**
     * Function updating the attributes of all components
     * @param entity
     */
    public void update(Entity entity);
}
