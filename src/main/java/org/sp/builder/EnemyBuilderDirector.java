package org.sp.builder;

import javafx.scene.image.Image;
import org.sp.entities.Enemy;
import org.sp.physics.BoxCollider;
import org.sp.physics.Vector2D;

public class EnemyBuilderDirector {
    private EnemyBuilder builder;
    private Image image;
    private Vector2D position;
    private Double width ;
    private Double height;
    private BoxCollider boxCollider;
    private boolean isFastProjectile;

    public EnemyBuilderDirector(EnemyBuilder enemyBuilder,
                                Image image,
                                Vector2D position,
                                Double width,
                                Double height,
                                BoxCollider boxCollider,
                                boolean isFastProjectile
                                ){
        this.builder = enemyBuilder;
        this.position =position;
        this.width = width;
        this.height = height;
        this.boxCollider = boxCollider;
        this.isFastProjectile = isFastProjectile;
        this.image = image;

    }
    public void constructEnemy() {
        builder.setVector2D(position);
        builder.setHeight(height);
        builder.setWidth(width);
        builder.setBoxCollider(boxCollider);
        builder.setHealth(100);
        builder.setImage(image);
        builder.setProjectileType(isFastProjectile);
    }
    public Enemy returnEnemy(){
        return builder.createEnemy();
    }

}
