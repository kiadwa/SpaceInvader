package org.sp.factory;

import org.sp.entities.Enemy;
import org.sp.physics.Vector2D;

public class EnemyProjectileFactory implements ProjectileFactory{
    @Override
    public EnemyProjectile createProjectile(Vector2D v2D, double damage) {
        return new EnemyProjectile(v2D, damage);
    }
}
