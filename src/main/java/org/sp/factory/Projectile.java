package org.sp.factory;

import javafx.scene.image.Image;
import org.sp.GameObject;
import org.sp.physics.BoxCollider;
import org.sp.physics.Collider;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

public interface Projectile extends Renderable, Collider, Moveable, GameObject {

    double damage = 100;

    public double getDamage();
    public void setPosition(Vector2D vector2D);
    public void setImage(Image image);
    public void setVelocity(double velocity);
    public Double getVelocity();
    public BoxCollider getBoxCollider();
    public void setBoxCollider(BoxCollider boxCollider);

}
