package gameplay.controller;

import gameplay.EntityType;
import gameplay.model.GameModel;
import gameplay.py.Displacement;
import engine.controller.KeyboardController;
import engine.core_kernel.Entity;
import engine.core_kernel.Map;
import engine.physics.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining the inputs for the second player
 */
public class GhostController extends KeyboardController {
    private Displacement nextMove;
    private Displacement move;
    private final Map map;
    private int lastPos;

    public GhostController(Map map){
        this.map = map;
        lastPos = GameModel.getInstance().getPacmanModel().getPV();
        nextMove = Displacement.NOTHING;
        move = Displacement.NOTHING;
        createHandler();
    }

    /**
     * function collecting player input to define the next move
     */
    private void createHandler(){
        createHandler(KeyEvent -> {
            switch (KeyEvent) {
                case UP :
                    nextMove = Displacement.UP;
                    break;
                case LEFT :
                    nextMove = Displacement.LEFT;
                    break;
                case DOWN :
                    nextMove = Displacement.DOWN;
                    break;
                case RIGHT :
                    nextMove = Displacement.RIGHT;
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Function updating orientation
     * @param entity
     */
    @Override
    public void update(Entity entity){
        updateMove(entity);

        entity.setPositioning(move.orientation);
    }

    /**
     * Function checking if the direction entered is valid for the movement of the entity
     * @param entity
     */
    private void updateMove(Entity entity){
        if(lastPos != GameModel.getInstance().getPacmanModel().getPV() || GameModel.getInstance().getPacmanModel().isDead()){
            move = Displacement.NOTHING;
            nextMove = Displacement.NOTHING;
            //entity.getGraphicsComponent().getAnimationManager().setCurrentAnimation(move.orientation.toString());

            lastPos = GameModel.getInstance().getPacmanModel().getPV();
        }

        if(nextMove != Displacement.NOTHING){
            Position position = map.getPositionEntity(entity);
            int x = (int)position.getX(), y = (int)position.getY();
            List<Entity> entities = new ArrayList<>();

            if((entity.getPositioning() == null)
                    || ((entity.getPosition().getX()%map.getDimCellWdt() <= entity.getPhysicsComponent().getSpeed()))
                    && (entity.getPosition().getY()%map.getDimCellHgt() <= entity.getPhysicsComponent().getSpeed())) {
                switch (nextMove) {
                    case UP -> entities = map.getEntity(x, y - 1);
                    case DOWN -> entities = map.getEntity(x, y + 1);
                    case RIGHT -> entities = map.getEntity(x + 1, y);
                    case LEFT -> entities = map.getEntity(x - 1, y);
                }

                if(canCross(entities)) {
                    double new_x = entity.getPosition().getX() - (entity.getPosition().getX()%map.getDimCellWdt());
                    double new_y = entity.getPosition().getY() - (entity.getPosition().getY()%map.getDimCellHgt());
                    Position new_position = new Position(new_x, new_y);
                    entity.setPosition(new_position);
                    entity.getPhysicsComponent().getCollider().update(new_position);
                    move = nextMove;
                    nextMove = Displacement.NOTHING;
                }
            }
        }
    }

    /**
     *function allowing to know if in the list of entity passed in parameter
     * all entities are traversable
     * @param entities
     * @return
     */
    private boolean canCross(List<Entity> entities){
        for(Entity entity : entities){
            if(EntityType.WALL.name.equals(entity.getName()))
                return false;
        }
        return true;
    }
}
