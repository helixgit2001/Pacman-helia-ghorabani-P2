package engine.core_kernel.builder;

import engine.phy.Position;

/**
 * Class building entity
 */
public class Director {
    //corresponds to the length of a box
    private final double length;
    //corresponds to the width of a box
    private final double width;

    public Director(double length, double width) {
        this.length = length;
        this.width = width;
    }


    // Method which takes an entity builder and which calls its different methods to build the corresponding entity
    public void constructEntity (EntityBuilder entityBuilder, Position position){
        entityBuilder.createEntity();
        entityBuilder.buildPosition(position);
        entityBuilder.buildName();
        entityBuilder.buildPosition();
        entityBuilder.buildContComp();
        entityBuilder.buildPhysComp(length, width);
        entityBuilder.buildGraphComp(length, width);
    }
}
