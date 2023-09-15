package org.sp.factory;

import javafx.scene.image.Image;
import org.sp.physics.Vector2D;

public interface Projectile {
    final double damage = 100;
    public void tick();
    public void setPosition(Vector2D vector2D);
    public boolean hit();
    public void setImage(Image image);

}
