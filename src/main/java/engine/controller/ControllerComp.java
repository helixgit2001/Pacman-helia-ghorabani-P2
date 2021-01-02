package engine.controller;

import engine.core_kernel.Component;
import engine.core_kernel.Entity;

/**
 * Controller interface, allows the movements of a character by keyboard input
 */
public interface ControllerComp extends Component {
    public void update(Entity entity);
}
