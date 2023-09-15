package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.factory.Projectile;
import org.sp.physics.Collider;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

public class EnemyProjectile implements Projectile, Moveable, Renderable, Collider {
    private Vector2D position = new Vector2D(0,0);
    private final double width = 5;
    private final double height = 10;
    private final Image image = null;

    public EnemyProjectile(Vector2D position){
        this.position = position;
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
    public void setImage(Image image) {

    }

    @Override
    public void up() {

    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() - 1);
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
}
