package gameplay.builder.object;

import gameplay.EntityType;
import gameplay.physics.Displacement;
import gameplay.physics.ObjectPhy;
import engine.core_kernel.builder.EntityBuilder;
import engine.graphic.Graphics;
import engine.physics.BoxCollider;
import engine.physics.Position;

/**
 * Builder corresponding to super-gums (fir trees)
 */
public class SpecialCoinBuilder extends EntityBuilder {

    @Override
    public void buildPosition(Position position) {
        entity.setPosition(position);
    }

    @Override
    public void buildName() {
        entity.setName(EntityType.RED.name);
    }

    @Override
    public void buildPosition() {
        entity.setPositioning(Displacement.NOTHING.orientation);
    }

    @Override
    public void buildPhysComp(double length, double width) {
        Position position1 = new Position(entity.getPosition().getX(), entity.getPosition().getY());
        Position position2 = new Position(entity.getPosition().getX() + length, entity.getPosition().getY() + width);

        entity.setPhysicsComponent(new ObjectPhy(new BoxCollider(position1, position2)));
    }

    @Override
    public void buildGraphComp(double width, double height) {
        // Initialization of the graphics component
        Graphics graphics = new Graphics(0);
        graphics.setImage("/Image/object/sapin.png");
        graphics.setHeight(height);
        graphics.setWidth(width);
        entity.setGraphicsComponent(graphics);
    }

    @Override
    public void buildContComp() {

    }
}
