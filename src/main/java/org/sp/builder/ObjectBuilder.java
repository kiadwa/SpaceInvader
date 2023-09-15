package org.sp.builder;

import javafx.scene.image.Image;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;

public interface ObjectBuilder {
    public void setImage(Image image);
    public void setVector2D(Vector2D vector2D);
    public void setHealth(double health);
    public void setAnimator(Animator animator);
}
