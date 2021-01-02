package gameplay.builder;

import gameplay.EntityType;
import gameplay.model.GameModel;
import gameplay.controller.PacmanController;
import gameplay.model.PacmanModel;
import gameplay.py.Displacement;
import gameplay.py.PacmanPhy;
import engine.core_kernel.Map;
import engine.core_kernel.builder.EntityBuilder;
import engine.graphic.AnimationManager;
import engine.graphic.Graphics;
import engine.phy.BoxCollider;
import engine.phy.Position;


/**
 * Builder corresponding to Pacman
 */
public class PacmanBuilder extends EntityBuilder {
    private final Map map;
    private final PacmanModel pacmanModel;


    public PacmanBuilder(Map map){
        this.map = map;
        this.pacmanModel = GameModel.getInstance().getPacmanModel();
    }

    @Override
    public void buildPosition(Position position) {
        entity.setPosition(position);
    }

    @Override
    public void buildName() {
        entity.setName(EntityType.PACMAN.name);
    }

    @Override
    public void buildPosition() {
        entity.setPositioning(Displacement.NOTHING.orientation);
    }

    @Override
    public void buildContComp() {
        entity.setControllerComponent(new PacmanController(map));
    }

    @Override
    public void buildPhysComp(double length, double width) {
        Position position1 = new Position(entity.getPosition().getX(), entity.getPosition().getY());
        Position position2 = new Position(entity.getPosition().getX() + length, entity.getPosition().getY() + width);

        entity.setPhysicsComponent(new PacmanPhy(2, new BoxCollider(position1, position2), pacmanModel, map));
    }

    @Override
    public void buildGraphComp(double length, double width) {
        // Initialization of the graphics component
        Graphics graphics = new Graphics(1);
        graphics.setImage("/Image/pacman/pacmanRight.png");
        graphics.setHeight(width);
        graphics.setWidth(length);

        // Addition of different animations
        AnimationManager animationManager = new AnimationManager();
        double duration = 0.10;
        animationManager.addAnimation(Displacement.UP.orientation.toString()+EntityType.RED.name, "/Animation/redPacman/redPacUp/init.txt",duration);
        animationManager.addAnimation(Displacement.DOWN.orientation.toString()+EntityType.RED.name, "/Animation/redPacman/redPacDown/init.txt",duration);
        animationManager.addAnimation(Displacement.LEFT.orientation.toString()+EntityType.RED.name, "/Animation/redPacman/redPacLeft/init.txt",duration);
        animationManager.addAnimation(Displacement.RIGHT.orientation.toString()+EntityType.RED.name, "/Animation/redPacman/redPacRight/init.txt",duration);
        animationManager.addAnimation(Displacement.UP.orientation.toString(), "/Animation/pacman/pacmanUp/init.txt",duration);
        animationManager.addAnimation(Displacement.DOWN.orientation.toString(), "/Animation/pacman/pacmanDown/init.txt",duration);
        animationManager.addAnimation(Displacement.LEFT.orientation.toString(), "/Animation/pacman/pacmanLeft/init.txt",duration);
        animationManager.addAnimation(Displacement.RIGHT.orientation.toString(), "/Animation/pacman/pacmanRight/init.txt",duration);
        animationManager.addAnimation("dead", "/Animation/dead/init.txt",0.02);
        animationManager.addAnimation("gameOver", "/Animation/dead2/init.txt",1);
        graphics.setAnimationManager(animationManager);


        entity.setGraphicsComponent(graphics);
    }
}
