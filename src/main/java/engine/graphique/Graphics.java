package engine.graphique;

import javafx.scene.image.ImageView;
import engine.core_kernel.Component;
import engine.core_kernel.Entity;
import engine.core_kernel.Timer;
import javafx.scene.image.Image;

/**
 * Class corresponding to the graphic component of an entity
 */
public class Graphics implements Component {
    private ImageView currentImage;
    // Denied in builders if it is an animated object
    private AnimationManager animation;
    // Corresponding to the layer number
    private Integer layer;
    private double height;
    private double width;
    public String name;

    public Graphics(Integer layer) {
        this.layer = layer;
    }

    @Override
    public  void update(Entity entity){
        if (animation != null){
            if (animation.getCurrentAnimation() !=  null) {
                Image im = animation.playAnimation(Timer.getInstance().getTime());
                currentImage.setImage(im);
            }
        }
        if (currentImage != null) {
            currentImage.setLayoutX(entity.getPosition().getX());
            currentImage.setLayoutY(entity.getPosition().getY());
        }
    }

    public void setImage(String chemin) {
        if (chemin == null)
            currentImage = null;
        else {
            this.currentImage = new ImageView(new Image(Graphics.class.getResourceAsStream(chemin)));
            name = chemin;
        }
    }



    public ImageView getCurrentImage() {
        return currentImage;
    }

    public AnimationManager getAnimationManager() {
        return animation;
    }

    public void setHeight(double height) {
        this.height = height;
        currentImage.setFitHeight(height);
    }

    public void setWidth(double width) {
        this.width = width;
        currentImage.setFitWidth(width);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setAnimationManager(AnimationManager animation) {
        this.animation = animation;
    }

    public void setCurrentImage(ImageView currentImage) {
        this.currentImage = currentImage;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }
}