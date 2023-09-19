package org.sp.physics;

import org.sp.logic.Damagable;
import org.sp.rendering.Renderable;

import javax.swing.text.html.parser.Entity;

public class BoxCollider implements Collider {
    private Damagable damagable;
    private double width;
    private double height;
    private Vector2D position;
    Renderable entity;

    public BoxCollider(double width, double height, Vector2D position, Renderable entity){
        this.height = height;
        this.width = width;
        this.position = position;
        this.entity = entity;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public Vector2D getPosition(){
        return this.position;
    }

    public void setPosition(Vector2D vector2D){this.position = vector2D;}
    public Damagable setDamagable(){return this.damagable;}
    public void setDamagable(Damagable damagable){this.damagable = damagable;}

}
