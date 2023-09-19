package org.sp.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.logic.Damagable;
import org.sp.physics.BoxCollider;
import org.sp.physics.Collider;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;
import org.sp.state.BunkerState;

import java.awt.*;

public class Bunker implements Damagable, Renderable, ConfigReader, GameObject, Collider {
    private Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 300;
    private double width;
    private double height;
    private  Image image = null;
    private BoxCollider boxCollider;
    private BunkerState currentState;


    public void setCurrentState(BunkerState state){this.currentState = state;}
    public BunkerState getCurrentState(){return this.currentState;}
    public void changeColor(){currentState.changeColor(this);}
    public void setBoxCollider(BoxCollider boxCollider){this.boxCollider = boxCollider;}
    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }
    public void setHealth(double health) {this.health = health;}
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
    public void setWidth(double width){this.width = width;}
    @Override
    public double getWidth() {
        return width;
    }
    public void setHeight(double height){this.height= height;}

    @Override
    public double getHeight() {
        return height;
    }
    public void setPosition(Vector2D vector2D) {this.position = vector2D;}
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

