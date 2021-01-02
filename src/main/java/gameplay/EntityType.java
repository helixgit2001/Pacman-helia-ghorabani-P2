package gameplay;

/**
 * Definition of entity names
 */
public enum EntityType {
    WALL("wall"),
    Blue("blue"),
    GIFT("gift"),
    RED("red"),
    PACMAN("pacman"),
    GHOST("ghost");

    public final String name;
    private EntityType(String name){
        this.name = name;
    }
}
