package gameplay.events.eat;

import engine.core_kernel.builder.Director;
import engine.core_kernel.builder.EntityBuilder;
import gameplay.LevelGenerator;
import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.Map;
import engine.phy.Position;
import engine.sound.SoundManager;

import java.util.Random;

/**
 * Event managing the action eat a gift (gift)
 */
public class EventEatGift extends Event {
    private PacmanModel pacmanModel;
    private final Map map;
    private final int[] scorecard = new int[4];

    public EventEatGift(PacmanModel pacmanModel, Entity entity, Map map) throws InterruptedException {
        super(entity);
        this.pacmanModel = pacmanModel;
        this.map = map;
        scorecard[0] = 1000;
        scorecard[1] = 2000;
        scorecard[2] = 3000;
        scorecard[3] = 4000;


    }

    @Override
    public void handle() {
        Position position = map.getPositionEntity(entity);
        if(position == null) return;
        int temp = (int) (Math.random() * 4);
        pacmanModel.addScore(scorecard[temp]);
        map.deleteEntity(position, entity);
        entity.getGraphicsComponent().getCurrentImage().setImage(null);
        entity = new Entity();
        Random random = new Random();
        int h = 0;
        for (int i = 0; i < LevelGenerator.coins.size(); i++){
            if (LevelGenerator.coins.get(i) != null)
                h++;
        }
        EntityBuilder builder;
        //Director director = ne
        String[] coins = new String[5];

        try {
            for (int i = 0; i < 5; i++){
                int c = random.nextInt(h - 1);
                if (LevelGenerator.coins.get(c) != null) {
                    Entity newEntity = LevelGenerator.CopyGhosts.get(c);
                   // newEntity.getGraphicsComponent().getCurrentImage().setImage();
                    map.deleteEntity(newEntity.getPosition(), newEntity);
                    pacmanModel.addScore(10);
                }
                coins[i] = ".";
            }

        }catch (IndexOutOfBoundsException e){
            System.out.println("Remove Five Coins Randomly!");
        }

        SoundManager.getInstance().addSound("pacman_eatfruit.wav", "gift", false, 0.2f, 0L);


    }
}
