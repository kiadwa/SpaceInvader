package org.sp.factory;

import org.sp.physics.Vector2D;

public interface Projectile {
    double damage = 50;
    public void tick();
    public void setPosition(Vector2D vector2D);
    public boolean hit();

}
