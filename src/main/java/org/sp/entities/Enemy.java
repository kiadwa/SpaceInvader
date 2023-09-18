package org.sp.entities;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.factory.*;
import org.sp.logic.Damagable;
import org.sp.physics.BoxCollider;
import org.sp.physics.Collider;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

public class Enemy
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
    private double movementSPD = 0.5;
    private BoxCollider boxCollider;

    public Enemy() {
    }
    public EnemyProjectile shoot(){
        EnemyProjectileFactory enemyProjectileFactory = new EnemyProjectileFactory();
        EnemyProjectile projectile = enemyProjectileFactory.createProjectile(new Vector2D(this.position.getX() - 8,
                                                                                            this.position.getY()));
        return projectile;
    }
    public void setBoxCollider(BoxCollider boxCollider){this.boxCollider = boxCollider;}
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
        return;
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + 10);
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - movementSPD);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + movementSPD);
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
