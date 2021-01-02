package engine.phy;

import engine.core_kernel.Entity;

/**
 * Interface defining the classes detecting when an entity
 */
public interface ExitListener {
    /**
     * method triggered when an entity leaves a defined area
     * @param entity_owned
     */
    public void onExit(Entity entity_owned);
}
