package gameplay.builder.ghosts;

        import gameplay.EntityType;
        import gameplay.al_movement.HorizontalAl;
        import gameplay.py.Displacement;
        import gameplay.py.GhostPhy;
        import engine.core_kernel.Map;
        import engine.core_kernel.builder.EntityBuilder;
        import engine.graphic.Graphics;
        import engine.phy.BoxCollider;
        import engine.phy.Position;

/**
 * Builder corresponding to the green ghost
 */
public class HorizontalGhostBuilder extends EntityBuilder {
    private final Map map;

    public HorizontalGhostBuilder(Map map){
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
            entity.setControllerComponent(new HorizontalAl());
    }

    @Override
    public void buildPhysComp(double length, double width) {

        Position position1 = new Position(entity.getPosition().getX() , entity.getPosition().getY());
        Position position2 = new Position(entity.getPosition().getX() + length, entity.getPosition().getY() + width);

        entity.setPhysicsComponent(new GhostPhy(1, new BoxCollider(position1, position2)));
    }

    @Override
    public void buildGraphComp(double width, double height) {
        Graphics graphics = new Graphics(2);
        graphics.setImage("/Image/ghost/HorizontalGhost.png");
        graphics.setHeight(height);
        graphics.setWidth(width);
        entity.setGraphicsComponent(graphics);
    }
}
