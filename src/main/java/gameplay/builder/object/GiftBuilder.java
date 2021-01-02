package gameplay.builder.object;

import gameplay.EntityType;
import gameplay.py.ObjectPhy;
import gameplay.py.Displacement;
import engine.core_kernel.builder.EntityBuilder;
import engine.graphic.Graphics;
import engine.phy.BoxCollider;
import engine.phy.Position;

/**
 * Builder corresponding to gifts
 */
public class GiftBuilder extends EntityBuilder {
    @Override
    public void buildPosition(Position position) {
        entity.setPosition(position);
    }

    @Override
    public void buildName() {
        entity.setName(EntityType.GIFT.name);
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
    public void buildGraphComp(double length, double width) {
        Graphics graphics = new Graphics(0);
        graphics.setImage("/Image/object/giftCoin.png");
        graphics.setHeight(width);
        graphics.setWidth(length);
        entity.setGraphicsComponent(graphics);
    }

    @Override
    public void buildContComp() {

    }
}
