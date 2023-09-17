package org.sp.factory;

import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

public interface ProjectileFactory  {
    Projectile createProjectile(Vector2D v2D);

}
