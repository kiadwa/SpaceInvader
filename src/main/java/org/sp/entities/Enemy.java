package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.logic.Damagable;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

import java.io.File;

public class Enemy implements Renderable, Damagable, Moveable, ConfigReader {
    private Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 100;
    private final double width = 25;
    private final double height = 30;
    private final Image image;

    public Enemy() {
        this.image = new Image(new File("src/main/resources/enemy.png").toURI().toString(), width, height, true, true);
    }

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

    @Override
    public void up() {
        this.position.setY(this.position.getY() + 1);
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() - 1);
    }

    @Override
    public void left() {
        this.position.setX(this.position.getY() - 1);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getY() + 1);
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {return height;}

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }
}
