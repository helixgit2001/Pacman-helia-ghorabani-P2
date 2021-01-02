package gameplay.al_movement;

import gameplay.model.GameModel;
import gameplay.physics.Displacement;
import engine.al.AL;
import engine.core_kernel.Entity;

import java.util.TimerTask;


/**
 * Class defining the random AI
 */
public class HorizontalAl extends TimerTask implements AL {

    private final Displacement[] possibleDisplacement;

    public HorizontalAl(){
        possibleDisplacement = new Displacement[]{Displacement.RIGHT, Displacement.LEFT,Displacement.NOTHING};
    }

    @Override
    public void run() {
    }






    /**
     * method defining the next movement of the entity passed as a parameter
     * @param entity
     */
    public void update(Entity entity){
        if(! canChangeDirection(entity)) return;

        int choose = (int) (Math.random()*possibleDisplacement.length);
        if(possibleDisplacement[choose] != Displacement.NOTHING)
            entity.setPositioning(possibleDisplacement[choose].orientation);
    }

    /**
     * function to detect if the change of direction is possible
     * @return
     */
    private boolean canChangeDirection(Entity entity){
        return (entity.getPosition().getX() % GameModel.getInstance().getMap().getDimCellWdt() == 0)
                && (entity.getPosition().getY() % GameModel.getInstance().getMap().getDimCellHgt() == 0);
    }
}
