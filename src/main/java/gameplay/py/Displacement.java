package gameplay.py;

/**
 * List of possible directions in 2D
 */
public enum Displacement {
    UP(270.0),
    DOWN(90.0),
    RIGHT(0.0),
    LEFT(180.0),
    NOTHING(null);

    public final Double orientation;

    private Displacement(Double orientation){
        this.orientation = orientation;
    }
}
