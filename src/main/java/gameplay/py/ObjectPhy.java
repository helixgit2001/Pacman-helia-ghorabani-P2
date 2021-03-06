package gameplay.py;

import engine.phy.Collider;
import engine.phy.PhyComp;

/**
 * class of objects physically present in the game
 */
public class ObjectPhy extends PhyComp {
    public ObjectPhy(Collider collider) {
        super(0.0);
        this.collider = collider;
    }
}
