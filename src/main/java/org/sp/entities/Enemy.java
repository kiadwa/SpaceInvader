package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.factory.Projectile;
import org.sp.factory.ProjectileFactory;
import org.sp.logic.Damagable;
import org.sp.physics.Collider;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

import java.io.File;

public class Enemy extends
        ProjectileFactory
        implements
        Renderable,
        Damagable,
        Moveable,
        Collider,
        ConfigReader,
        GameObject {
    private Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 100;
    private double width = 25;
    private double height = 30;
    private Image image;
    private boolean Fastprojectile = false;

    public Enemy() {
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    public void setFastprojectile(boolean bool){this.Fastprojectile = bool;}
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
    public void setImage(Image image){this.image = image;}
    @Override
    public Image getImage() {
        return this.image;
    }
    public void setWidth(double width){this.width = width;}
    @Override
    public double getWidth() {
        return width;
    }
    public void setHeight(double height){this.height = height;}
    @Override
    public double getHeight() {return height;}
    public void setPosition(Vector2D vector2D){this.position = vector2D;}

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    protected Projectile createProjectile() {
        Projectile projectile = new EnemyProjectile(new Vector2D(this.getPosition().getX(), this.getPosition().getY() + 2));
        return projectile;    }

    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }
}
