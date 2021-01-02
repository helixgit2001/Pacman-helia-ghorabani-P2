package engine.al;

import engine.controller.ControllerComp;
import engine.core_kernel.Entity;

/**
 * Interface valid for all Al
 */
public interface AL extends ControllerComp {
    public void update(Entity entity);
}
