package org.sp.factory;

import org.sp.physics.Vector2D;

public class PlayerProjectileFactory implements ProjectileFactory{

    @Override
    public PlayerProjectile createProjectile(Vector2D v2D) {
        return new PlayerProjectile(v2D);
    }
}
