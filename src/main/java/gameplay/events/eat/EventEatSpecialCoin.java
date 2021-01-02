package gameplay.events.eat;

import gameplay.LevelGenerator;
import gameplay.model.PacmanModel;
import engine.core_kernel.Entity;
import engine.core_kernel.Event;
import engine.core_kernel.EventManager;
import engine.core_kernel.Map;
import engine.phy.Position;
import engine.sound.SoundManager;

import java.util.Random;

/**
 * Event managing the action to eat a Christmas tree
 */
public class EventEatSpecialCoin extends Event {
    private final PacmanModel pacmanModel;
    private final Entity ownedEntity;
    private final Map map;

    public EventEatSpecialCoin(PacmanModel pacmanModel, Entity entity, Entity ownedEntity, Map map) throws InterruptedException {
        super(entity);
        this.pacmanModel = pacmanModel;
        this.map = map;
        this.ownedEntity = ownedEntity;
    }

    @Override
    public void handle() {
        Position position = map.getPositionEntity(entity);
        if(position == null) return;

        map.deleteEntity(position, entity);
        entity.getGraphicsComponent().getCurrentImage().setImage(null);
        Random random = new Random();
        try {
            for (int i = 0; i < 5; i++){
                int c = random.nextInt(LevelGenerator.coins.size() - 1);
                if (LevelGenerator.coins.get(c) == null) {
                    map.addEntity((int) LevelGenerator.copyCoins.get(c).getPosition().getX(), (int) LevelGenerator.copyCoins.get(c).getPosition().getY(), LevelGenerator.coins.get(c));
                    pacmanModel.addScore(-10);
                }
            }
            System.out.println("Add Five Coins Randomly!");
        } catch (IndexOutOfBoundsException e){
            System.out.println("Fail To Add Five Coins!");
        }

        pacmanModel.addScore(100);
        if(!pacmanModel.isRed()){
            pacmanModel.setRed(true);
            SoundManager.getInstance().stopAllSound();
            SoundManager.getInstance().addSound("isNoel.wav", "isNoel", false, 0.8f, 0L);
            EventManager.getEventManager().addEvent(new EventEndPower(pacmanModel, ownedEntity, 660));



        }
    }
}
