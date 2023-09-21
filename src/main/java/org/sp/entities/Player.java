package org.sp.entities;

import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.factory.PlayerProjectile;
import org.sp.factory.PlayerProjectileFactory;
import org.sp.factory.Projectile;
import org.sp.factory.ProjectileFactory;
import org.sp.logic.Damagable;
import org.sp.physics.BoxCollider;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;
import org.sp.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class Player implements Moveable, Damagable, Renderable, ConfigReader, GameObject {

    private  Vector2D position = new Vector2D(0,0);
    private final Animator anim = null;
    private double health = 100;
    private final double width = 25;
    private final double height = 30;
    private  Image image;
    private int lives = 0;
    private String color;
    private BoxCollider boxCollider;
    private Double speed;


    public Player(){
        setPlayerData();
        setPlayerImage();

    }
    public void setPlayerImage(){
        if(this.color.equalsIgnoreCase("green") ){
            this.image = new Image(new File("src/main/resources/player.png").toURI().toString(),
                    width,
                    height,
                    false, true);
        }else if(this.color.equalsIgnoreCase("white")){
            this.image = new Image(new File("src/main/resources/player1.png").toURI().toString(),
                    width,
                    height,
                    false, true);
        }else if(this.color.equalsIgnoreCase("blue")){
            this.image = new Image(new File("src/main/resources/player2.png").toURI().toString(),
                    width,
                    height,
                    false, true);
        }
    }
    public void setPlayerData(){
        HashMap<String, Object> playerData;
        playerData = ConfigReader.readPlayerData();
        this.position.setX(((Number)playerData.get("x")).doubleValue());
        this.position.setY(((Number) playerData.get("y")).doubleValue());
        this.lives = ((Number)playerData.get("lives")).intValue();
        this.color = (String) playerData.get("colour");
        this.speed = ((Number)playerData.get("speed")).doubleValue();
        this.boxCollider = new BoxCollider(width,height,position, this);
    }
    public int getLives(){return this.lives;}

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
        if(this.health <= 0 && this.lives > 0){
            this.lives -= 1;
            this.health = 100;
        }
    }

    @Override
    public double getHealth() {
        return health;
    }
    public BoxCollider getBoxCollider(){return this.boxCollider;}

    @Override
    public boolean isAlive() {
        return this.lives > 0;
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
        this.position.setX(this.position.getX() - speed);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + speed);
    }

    public PlayerProjectile shoot(){
        // todo
        PlayerProjectileFactory playerProjectileFactory = new PlayerProjectileFactory();
        PlayerProjectile projectile = playerProjectileFactory.createProjectile(new Vector2D(this.position.getX() + 3 ,
                this.position.getY() + 2), 100);
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

    @Override
    public void start() {

    }

    @Override
    public void update() {
        boxCollider.setPosition(position);
    }
}
