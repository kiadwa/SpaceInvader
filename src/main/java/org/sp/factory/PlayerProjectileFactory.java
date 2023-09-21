package org.sp.factory;

import org.sp.entities.Player;
import org.sp.physics.Vector2D;

public class PlayerProjectileFactory implements ProjectileFactory{

    @Override
    public PlayerProjectile createProjectile(Vector2D v2D, double damage) {
        return new PlayerProjectile(v2D, damage);
    }
}
