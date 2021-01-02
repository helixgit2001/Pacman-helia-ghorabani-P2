package gameplay.builder.ghost;

        import gameplay.EntityType;
        import gameplay.al_movement.VerticalAl;
        import gameplay.physics.Displacement;
        import gameplay.physics.GhostPhy;
        import engine.core_kernel.Map;
        import engine.core_kernel.builder.EntityBuilder;
        import engine.graphic.Graphics;
        import engine.physics.BoxCollider;
        import engine.physics.Position;

/**
 * Builder corresponding to the green ghost
 */
public class VerticalGhostBuilder extends EntityBuilder {
    private final Map map;

    public VerticalGhostBuilder(Map map){
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
            entity.setControllerComponent(new VerticalAl());
    }

    @Override
    public void buildPhysComp(double length, double width) {

        Position position1 = new Position(entity.getPosition().getX() , entity.getPosition().getY());
        Position position2 = new Position(entity.getPosition().getX() + length, entity.getPosition().getY() + width);

        entity.setPhysicsComponent(new GhostPhy(1, new BoxCollider(position1, position2)));
    }

    @Override
    public void buildGraphComp(double width, double heigth) {
        Graphics graphics = new Graphics(2);
        graphics.setImage("/Image/ghost/VerticalGhost.png");
        graphics.setHeight(heigth);
        graphics.setWidth(width);
        entity.setGraphicsComponent(graphics);
    }
}


