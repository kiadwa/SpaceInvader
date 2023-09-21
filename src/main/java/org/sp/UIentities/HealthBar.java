package org.sp.UIentities;

import javafx.scene.image.Image;
import org.sp.entities.Player;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

import java.io.File;

public class HealthBar implements Renderable {
    private Vector2D position;
    private double width = 50;
    private double height = 50;
    private Image image = new Image(new File("src/main/resources/heart.png").toURI().toString(),width,height,
            true,
            true );
    public HealthBar(Vector2D position){
        this.position = position;
    }
    public void setPosition(Vector2D vector2D){this.position = vector2D;}

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.BACKGROUND;
    }
}
