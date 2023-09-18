package org.sp.factory;

import org.sp.physics.Vector2D;

public class EnemyProjectileFactory implements ProjectileFactory{
    @Override
    public EnemyProjectile createProjectile(Vector2D v2D) {
        return new EnemyProjectile(v2D);
    }
}
