package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.factory.Projectile;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

public class PlayerProjectile implements Projectile, Moveable, Renderable {
    private Vector2D position = null;
    private final double width = 5;
    private final double height = 10;
    private final Image image;
    public PlayerProjectile(Vector2D vector2D, Image image){
        this.position = vector2D;
        this.image = image;
    }
    @Override
    public void tick() {

    }

    @Override
    public void setPosition(Vector2D vector2D) {

    }

    @Override
    public boolean hit() {
        return false;
    }

    @Override
    public void up() {
        this.position.setY(this.position.getY() + 10);
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        return;
    }

    @Override
    public void right() {
        return;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    @Override
    public Layer getLayer() {
        return null;
    }
}
