package org.sp.physics;

import org.sp.entities.Enemy;
import org.sp.entities.Player;
import org.sp.factory.Projectile;
import org.sp.logic.Damagable;
import org.sp.rendering.Renderable;

import javax.swing.text.html.parser.Entity;

public class BoxCollider implements Collider {
    private Damagable damagable;
    private double width;
    private double height;
    private Vector2D position;
    Renderable entity;
    Projectile projectile;
    Player player;
    Enemy enemy;

    public BoxCollider(double width, double height, Vector2D position, Renderable entity){
        this.height = height;
        this.width = width;
        this.position = position;
        this.entity = entity;
    }
    public void setProjectile(Projectile projectile){this.projectile = projectile;}
    public void setPlayer(Player player){this.player = player;}
    public void setEnemy(Enemy enemy){this.enemy = enemy;}

    public Enemy getEnemy(){return this.enemy;}
    public Projectile getProjectile(){return this.projectile;}

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
    public Renderable getEntity(){return this.entity;}

}
