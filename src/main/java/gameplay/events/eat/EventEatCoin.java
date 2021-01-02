package gameplay.events.eat;

import gameplay.LevelGenerator;
import gameplay.al_movement.*;
import gameplay.model.GameModel;
import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.Map;
import engine.phy.Position;
import engine.sound.SoundManager;

import static gameplay.LevelGenerator.CopyGhosts;

/**
 * Event managing the action of eating a gum (barley sugar)
 */
public class EventEatCoin extends Event {
    private PacmanModel pacmanModel;
    private Map map;
    private int c = 200;

    public EventEatCoin(PacmanModel pacmanModel, Entity entity, Map map) throws InterruptedException {
        super(entity);
        this.pacmanModel = pacmanModel;
        this.map = map;
    }
    @Override
    public void handle() {
        Position position = map.getPositionEntity(entity);
        if(position == null) return;
int s = 0;
        pacmanModel.addScore(10);
        if (entity != null) {
            if (entity.getGraphicsComponent().name.equalsIgnoreCase("/Image/object/cerise.png")) {
                System.out.println("stop ghosts");
                LevelGenerator.ghosts.get(0).setControllerComponent(new NothingAl());
                LevelGenerator.ghosts.get(1).setControllerComponent(new NothingAl());
                LevelGenerator.ghosts.get(2).setControllerComponent(new NothingAl());
                LevelGenerator.ghosts.get(3).setControllerComponent(new NothingAl());
                LevelGenerator.ghosts.get(4).setControllerComponent(new NothingAl());
                LevelGenerator.ghosts.get(5).setControllerComponent(new NothingAl());


                s = GameModel.getInstance().getPacmanModel().getScore();

            }
            if (GameModel.getInstance().getPacmanModel().getScore() > s + 5 * c) {
                System.out.println("run ghosts");
                LevelGenerator.ghosts.get(0).setControllerComponent(CopyGhosts.get(0).getControllerComponent());
                LevelGenerator.ghosts.get(1).setControllerComponent(CopyGhosts.get(1).getControllerComponent());
                LevelGenerator.ghosts.get(2).setControllerComponent(CopyGhosts.get(2).getControllerComponent());

                LevelGenerator.ghosts.get(3).setControllerComponent(CopyGhosts.get(3).getControllerComponent());
                LevelGenerator.ghosts.get(4).setControllerComponent(CopyGhosts.get(4).getControllerComponent());
                LevelGenerator.ghosts.get(5).setControllerComponent(CopyGhosts.get(5).getControllerComponent());
            }

        }
        assert entity != null;
        entity.getGraphicsComponent().getCurrentImage().setImage(null);
        map.deleteEntity(position, entity);
        SoundManager.getInstance().addSound("pacman_chomp.wav", "chomp", false, 0.2f, 500L);
    }
}
