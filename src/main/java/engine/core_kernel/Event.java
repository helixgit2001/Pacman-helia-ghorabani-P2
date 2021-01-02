package engine.core_kernel;

/**
 * Class for creating programmable events
 */
public abstract class Event {
    protected Entity entity;
    protected int time;

    protected Event(Entity entity){
        this.entity = entity;
        this.time = 0;
    }

    protected Event(Entity entity, int time){
        this.time = time;
    }

    /**
     * call function when triggering the event created
     */
    public abstract void handle();

    public void update(){
        time--;
    }

    public int getTime(){
        return time;
    }
}
