package org.sp.factory;

import javafx.scene.image.Image;
import org.sp.GameObject;
import org.sp.factory.Projectile;
import org.sp.physics.BoxCollider;
import org.sp.physics.Collider;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;
import org.sp.strategy.EnemyProjectileStrategy;

import java.io.File;

public class EnemyProjectile implements
        Projectile,
        Moveable,
        Renderable,
        GameObject {
    private double velocity = 3;
    private Vector2D position;
    private final double width = 5;
    private final double height = 15;
    private  Image image;
    BoxCollider boxCollider;
    private double damage = 0;

    EnemyProjectileStrategy projectileStrategy;

    public EnemyProjectile(Vector2D v2D, double damage) {
        this.position = v2D;
        this.boxCollider = new BoxCollider(width,height,position,this);
        this.damage = damage;
    }
    public void setProjectileStrategy(EnemyProjectileStrategy projectileStrategy){
        this.projectileStrategy = projectileStrategy;
    }
    public void slowProjectileStrategy(){
        Image image =  new Image(new File("src/main/resources/purple_projectile.png").toURI().toString(), width, height, false, true);
        projectileStrategy.changeProjectileSprite(this, image);
    }
    public void fastProjectileStrategy(){
        Image image =  new Image(new File("src/main/resources/blue_projectile.png").toURI().toString(), width, height, false, true);
        projectileStrategy.changeProjectileSprite(this,image);
        projectileStrategy.changeProjectileVelocity(this);
    }

    @Override
    public void tick() {

    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public double getDamage() {
        return damage;
    }
    public static double getClassDamage(){
        return 100;
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
        this.image = image;
    }

    @Override
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    @Override
    public Double getVelocity() {
        return this.velocity;
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }

    @Override
    public void setBoxCollider(BoxCollider boxCollider) {
        this.boxCollider = boxCollider;
    }

    @Override
    public void up() {

    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + velocity);
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

    @Override
    public void start() {

    }

    @Override
    public void update() {
        down();
    }
}
