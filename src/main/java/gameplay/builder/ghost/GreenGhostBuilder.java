package gameplay.builder.ghost;

import gameplay.EntityType;
import gameplay.al_movement.RandomAL;
import gameplay.physics.Displacement;
import gameplay.physics.GhostPhy;
import engine.core_kernel.Map;
import engine.core_kernel.builder.EntityBuilder;
import engine.graphique.Graphics;
import engine.physics.BoxCollider;
import engine.physics.Position;

/**
 * Builder corresponding to the green ghost
 */
public class GreenGhostBuilder extends EntityBuilder {
    private final Map map;

    public GreenGhostBuilder(Map map){
        this.map = map;
    }

    @Override
    public void buildPosition(Position position) {
        // AL setting
        position.setX(position.getX());
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
        entity.setControllerComponent(new RandomAL());
    }

    @Override
    public void buildPhysComp(double length, double width) {

        Position position1 = new Position(entity.getPosition().getX() , entity.getPosition().getY());
        Position position2 = new Position(entity.getPosition().getX() + length, entity.getPosition().getY() + width);

        entity.setPhysicsComponent(new GhostPhy(1, new BoxCollider(position1, position2)));
    }

    @Override
    public void buildGraphComp(double length, double width) {
        Graphics graphics = new Graphics(2);
        graphics.setImage("/Image/ghost/GhostGreen.png");
        graphics.setHeight(width);
        graphics.setWidth(length);
        entity.setGraphicsComponent(graphics);
    }
}
