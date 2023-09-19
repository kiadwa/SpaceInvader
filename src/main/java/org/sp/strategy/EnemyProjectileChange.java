package org.sp.strategy;

import org.sp.factory.Projectile;

import javafx.scene.image.Image;
public class EnemyProjectileChange implements EnemyProjectileStrategy{
    @Override
    public void changeProjectileVelocity(Projectile projectile) {
        projectile.setVelocity(projectile.getVelocity() * 2);
    }

    @Override
    public void changeProjectileSprite(Projectile projectile, Image image) {
        projectile.setImage(image);
    }
}
