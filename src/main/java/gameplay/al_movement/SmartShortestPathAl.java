package gameplay.al_movement;

import gameplay.model.GameModel;
import gameplay.physics.GhostPhy;
import engine.physics.BoxCollider;
import engine.physics.PhyComp;
import engine.physics.Position;
import engine.al.AL;
import engine.al.BFS;
import engine.core_kernel.Entity;
import gameplay.physics.Displacement;

import java.util.List;
import java.util.TimerTask;

/**
 * Class defining the shortest path ai
 */
public class SmartShortestPathAl extends TimerTask implements AL {
    private BFS pathFinder;
    private Entity origin;
    private Entity anticipTarget;
    private Entity target;
    private Displacement lastDisplacement;
    private final int nbAnticipate;
    private List<Entity> lastEntities;

    public SmartShortestPathAl(){
        lastDisplacement = Displacement.NOTHING;
        nbAnticipate = 5;
    }

    @Override
    public void run() {

    }

    public void setOrigin(Entity origin) {
        this.origin = origin;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public void setPathFinder(BFS pathFinder) {
        this.pathFinder = pathFinder;
    }

    /**
     * Function used to define the next position of the entity passed as a parameter
     * @param entity
     */
    @Override
    public void update(Entity entity){

        if(pathFinder == null) return;
        if(! canChangeDirection()) return;

        Position position_origin = pathFinder.getMap().getPositionEntity(origin);
        List<Position> listPositions = getAnticipPosition();
        if(listPositions.size() == 0){
            entity.setPositioning(lastDisplacement.orientation);
            return;
        }

        Position nextPosition = (listPositions.size() == 1) ?
                listPositions.get(0) : listPositions.get(listPositions.size()-2);
        Displacement result;
        if(nextPosition.getX() != position_origin.getX()){
            if(nextPosition.getX() > position_origin.getX())
                result = Displacement.RIGHT;
            else if(nextPosition.getX() < position_origin.getX())
                result = Displacement.LEFT;
            else
                result = Displacement.NOTHING;
        } else {
            if(nextPosition.getY() > position_origin.getY())
                result = Displacement.DOWN;
            else if(nextPosition.getY() < position_origin.getY())
                result = Displacement.UP;
            else
                result = Displacement.NOTHING;
        }

        lastDisplacement = result;
        if(result != Displacement.NOTHING)
            entity.setPositioning(result.orientation);
    }

    /**
     * function to detect if the change of direction is possible
     * @return
     */
    private boolean canChangeDirection(){
        return (origin.getPosition().getX()%pathFinder.getMap().getMap().getDimCellWdt() == 0)
                && (origin.getPosition().getY()%pathFinder.getMap().getMap().getDimCellHgt() == 0);
    }

    private List<Position> getAnticipPosition(){
        anticipTarget = new Entity();
        anticipTarget.setPositioning(target.getPositioning());
        anticipTarget.setPosition(new Position(target.getPosition().getX(), target.getPosition().getY()));
        anticipTarget.setControllerComponent(null);
        anticipTarget.setGraphicsComponent(null);

        Position position1 = new Position(target.getPosition().getX(), target.getPosition().getY());
        Position position2 = new Position(target.getPosition().getX() + GameModel.getInstance().getMap().getDimCellWdt(), target.getPosition().getY() + GameModel.getInstance().getMap().getDimCellHgt());
        PhyComp phyComp = new GhostPhy(1, new BoxCollider(position1, position2));
        anticipTarget.setPhysicsComponent(phyComp);
        for(int i = nbAnticipate; i > 0; i--)
            anticipTarget.getPhysicsComponent().update(anticipTarget);

        int x = (int) ((anticipTarget.getPosition().getX() + GameModel.getInstance().getMap().getDimCellWdt()/2) / GameModel.getInstance().getMap().getDimCellWdt());
        int y = (int) ((anticipTarget.getPosition().getY() + GameModel.getInstance().getMap().getDimCellHgt()/2) / GameModel.getInstance().getMap().getDimCellHgt());
        GameModel.getInstance().getMap().addEntity(x, y, anticipTarget);
        List<Position> listPositions = pathFinder.pathFinding(origin, anticipTarget);
        GameModel.getInstance().getMap().deleteEntity(new Position(x, y), anticipTarget);

        return listPositions;
    }
}
