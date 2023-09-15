package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.logic.Damagable;
import org.sp.physics.Collider;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

public class Bunker implements Damagable, Renderable, ConfigReader, GameObject, Collider {
    private Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 300;
    private final double width = 25;
    private final double height = 30;
    private  Image image = null;

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }
    public void setImage(Image image){this.image = image;}

    @Override
    public Image getImage() {
        return image;
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
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }
}
