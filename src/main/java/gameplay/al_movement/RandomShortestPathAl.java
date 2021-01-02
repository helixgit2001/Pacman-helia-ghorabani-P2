package gameplay.al_movement;

import engine.phy.Position;
import engine.al.AL;
import engine.al.BFS;
import engine.core_kernel.Entity;
import gameplay.py.Displacement;

import java.util.List;
import java.util.TimerTask;

/**
 * Class defining the shortest path ai
 */
public class RandomShortestPathAl extends TimerTask implements AL {
    private BFS bfs;
    private Entity origin;
    private Entity target;
    private Double lastPos;

    public RandomShortestPathAl(){
        lastPos = null;
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

    public void setBfs(BFS bfs) {
        this.bfs = bfs;
    }

    /**
     * Function used to define the next position of the entity passed as a parameter
     * @param entity
     */
    @Override
    public void update(Entity entity){

        if(bfs == null) return;
        if(! canChangeDirection()) return;

        Position position_origin = bfs.getMap().getPositionEntity(origin);
        List<Position> listPositions = bfs.pathFinding(origin, target);
        if(listPositions.size() == 0){
            entity.setPositioning(lastPos);
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

        int randomChoice = (int) (Math.random() * 2);
        if(result == Displacement.NOTHING) return;

        double orientation = result.orientation;
        if(randomChoice == 1)
            orientation = (orientation+180.0) % 360;

        lastPos = orientation;
        entity.setPositioning(orientation);
    }

    /**
     * function to detect if the change of direction is possible
     * @return
     */
    private boolean canChangeDirection(){
        return (origin.getPosition().getX()% bfs.getMap().getMap().getDimCellWdt() == 0)
                && (origin.getPosition().getY()% bfs.getMap().getMap().getDimCellHgt() == 0);
    }
}
