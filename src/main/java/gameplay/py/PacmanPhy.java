package gameplay.py;

import gameplay.EntityType;
import gameplay.events.eat.EventEatGift;
import gameplay.events.eat.EventEatGhost;
import gameplay.events.eat.EventEatCoin;
import gameplay.events.eat.EventEatSpecialCoin;
import gameplay.model.GameModel;
import gameplay.events.dead.EventPacmanDie;
import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.EventManager;
import engine.core_kernel.Map;
import engine.physics.Collider;
import engine.physics.PhyComp;
import engine.physics.Position;
import engine.sound.SoundManager;
import java.util.HashMap;

/**
 * Pacman physical component class
 */
public class PacmanPhy extends PhyComp {
    private final PacmanModel pacmanModel;
    private final Map map;
    private final HashMap<Entity, Boolean> ghostsEat;
    private boolean changeGhostEat;

    public PacmanPhy(double speed, Collider collider, PacmanModel pacmanModel, Map map) {
        super(speed);
        this.collider = collider;
        this.pacmanModel = pacmanModel;
        this.map = map;
        ghostsEat = new HashMap<>();
        changeGhostEat = false;
    }

    /**
     * collide with the pacman's Collider box
     * @param entity_owned
     * @param entity
     */
    @Override
    public void onCollision(Entity entity_owned, Entity entity) throws InterruptedException {
        if(entity.getName().equals(EntityType.GHOST.name)){
            updateGhostCollision(entity_owned, entity);
        } else if(entity.getName().equals(EntityType.WALL.name)){
            updatePositionEntityPosition(entity_owned, entity);
        } else if(entity.getName().equals(EntityType.Blue.name)){
            EventManager.getEventManager().addEvent(new EventEatCoin(pacmanModel, entity, map));
        } else if(entity.getName().equals(EntityType.GIFT.name)){
            EventManager.getEventManager().addEvent(new EventEatGift(pacmanModel, entity, map));
        } else if(entity.getName().equals(EntityType.RED.name)){
            EventManager.getEventManager().addEvent(new EventEatSpecialCoin(pacmanModel, entity, entity_owned, map));
        }
    }

    @Override
    public void onExit(Entity entity_owned){
        moveBack(entity_owned);
    }

    /**
     * updates the pacman positions according to the controller
     * @param entity_owned
     * @param entity
     */
    private void updatePositionEntityPosition(Entity entity_owned, Entity entity){
        if(entity_owned.getPositioning() != null){
            double x = entity_owned.getPosition().getX(), y = entity_owned.getPosition().getY();
            double new_x = x, new_y = y;
            if(entity_owned.getPositioning().equals(Displacement.RIGHT.orientation)){
                new_x = x - (x+entity_owned.getGraphicsComponent().getWidth() - entity.getPosition().getX());
            } else if (entity_owned.getPositioning().equals(Displacement.LEFT.orientation)) {
                new_x = x + (entity.getPosition().getX() - x+entity_owned.getGraphicsComponent().getWidth());
            } else if (entity_owned.getPositioning().equals(Displacement.UP.orientation)) {
                new_y = y + (entity.getPosition().getY() - y+entity_owned.getGraphicsComponent().getHeight());
            } else if (entity_owned.getPositioning().equals(Displacement.DOWN.orientation)) {
                new_y = y - (y+entity_owned.getGraphicsComponent().getHeight() - entity.getPosition().getY());
            }
            entity_owned.setPosition(new Position(new_x, new_y));
            entity_owned.setPositioning(Displacement.NOTHING.orientation);
        }
    }

    /**
     * Check in case of collision with any ghost
     * @param entity_owned
     * @param entity
     */
    private void updateGhostCollision(Entity entity_owned, Entity entity) throws InterruptedException {
        if (pacmanModel.isRed() && canEat(entity)){
            EventManager.getEventManager().addEvent(new EventEatGhost(pacmanModel, entity));
            GameModel.getInstance().resetEntity(entity);
        } else {
            resetGhostEat();
            pacmanModel.decrementPV();
            if (pacmanModel.checkNull()){
                if (!pacmanModel.isDead()){
                    EventManager.getEventManager().addEvent(new EventPacmanDie(pacmanModel, entity, entity_owned, map));
                }
                pacmanModel.setDead(true);
                entity_owned.setPositioning(Displacement.NOTHING.orientation);
            } else {
                GameModel.getInstance().resetGame();
                SoundManager.getInstance().addSound("touch.wav", "touch", false, 0.2f, 0L);
            }
        }
    }

    private void resetGhostEat(){
        if(pacmanModel.isRed()) return;
        if(! changeGhostEat) return;

        ghostsEat.replaceAll((e, v) -> true);
        changeGhostEat = false;
    }

    private boolean canEat(Entity entity) {
        changeGhostEat = true;
        if(ghostsEat.containsKey(entity)){
            boolean res = ghostsEat.get(entity);
            ghostsEat.put(entity, false);
            return res;
        } else {
            ghostsEat.put(entity, false);
            return true;
        }
    }

    /**
     * pacman half-turn movement
     * @param positioning
     */
    private void moveBack(Entity positioning){
        if(positioning.getPositioning() == null) return;
        positioning.setPositioning((positioning.getPositioning()+180.0)%360);
        positioning.getPhysicsComponent().update(positioning);
        positioning.setPositioning((positioning.getPositioning()-180.0)%360);
    }

    /**
     * movement straight from the pacman
     * @param entity_owned
     */
    private void moveForward(Entity entity_owned){
        if(entity_owned.getPositioning() == null) return;
        entity_owned.getPhysicsComponent().update(entity_owned);
    }
}
