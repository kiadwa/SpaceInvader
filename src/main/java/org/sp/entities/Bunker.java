package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.logic.Damagable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

public class Bunker implements Damagable, Renderable, ConfigReader {
    private Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 100;
    private final double width = 25;
    private final double height = 30;
    private final Image image = null;

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
    public Image getImage() {
        return null;
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
