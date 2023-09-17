package org.sp.entities;

import org.sp.ConfigReader;
import org.sp.factory.PlayerProjectile;
import org.sp.factory.PlayerProjectileFactory;
import org.sp.factory.Projectile;
import org.sp.factory.ProjectileFactory;
import org.sp.logic.Damagable;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class Player implements Moveable, Damagable, Renderable, ConfigReader {

    private  Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 100;
    private final double width = 25;
    private final double height = 30;
    private final Image image;
    private int lives = 0;
    private String color;


    public Player(){
        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        setPlayerData();

    }
    public void setPlayerData(){
        HashMap<String, Object> playerData;
        playerData = ConfigReader.readPlayerData();
        this.position.setX(((Number)playerData.get("x")).doubleValue());
        this.position.setY(((Number) playerData.get("y")).doubleValue());
        this.lives = ((Number)playerData.get("lives")).intValue();
        this.color = (String) playerData.get("color");
        System.out.println(this.getPosition().getX());
        System.out.println(this.getPosition().getY());
    }


    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        if(lives > 0 && health == 0)
            return 100;
        return health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0 || this.lives > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - 1);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + 1);
    }

    public Projectile shoot(){
        // todo
        PlayerProjectileFactory playerProjectileFactory = new PlayerProjectileFactory();
        Projectile projectile = playerProjectileFactory.createProjectile(new Vector2D(this.position.getX() - 8 ,
                this.position.getY()));
        return projectile;
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
