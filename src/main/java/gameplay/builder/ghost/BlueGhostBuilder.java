package gameplay.builder.ghost;

import gameplay.EntityType;
import gameplay.al_movement.RandomShortestPathAl;
import gameplay.py.Displacement;
import gameplay.py.GhostPhy;
import engine.al.AL;
import engine.core_kernel.builder.EntityBuilder;
import engine.graphic.Graphics;
import engine.phy.BoxCollider;
import engine.phy.Position;

/**
 * Builder corresponding to the blue ghost
 */
public class BlueGhostBuilder extends EntityBuilder {
    private final AL al;

    public BlueGhostBuilder(RandomShortestPathAl randomShortestPathAl) {
        al = randomShortestPathAl;
    }

    @Override
    public void buildPosition(Position position) {
        entity.setPosition(position);
    }

    @Override
    public void buildName() {
        entity.setName(EntityType.GHOST.name);
    }

    @Override
    public void buildPosition() {
        entity.setPositioning(Displacement.NOTHING.orientation);
    }

    @Override
    public void buildContComp() {
        entity.setControllerComponent(al);
    }

    @Override
    public void buildPhysComp(double length, double width) {
        Position position1 = new Position(entity.getPosition().getX(), entity.getPosition().getY());
        Position position2 = new Position(entity.getPosition().getX() + length, entity.getPosition().getY() + width);

        entity.setPhysicsComponent(new GhostPhy(1, new BoxCollider(position1, position2)));
    }

    @Override
    public void buildGraphComp(double length, double width) {
        // Initialization of the graphics component
        Graphics graphics = new Graphics(2);
        graphics.setImage("/Image/ghost/GhostBlue.png");
        graphics.setHeight(width);
        graphics.setWidth(length);
        entity.setGraphicsComponent(graphics);
    }
}
