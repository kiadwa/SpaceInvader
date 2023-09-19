package org.sp.strategy;

import org.sp.factory.Projectile;

import javafx.scene.image.Image;

public interface EnemyProjectileStrategy {
    void changeProjectileVelocity(Projectile projectile);
    void changeProjectileSprite( Projectile projectile,Image image);
}
