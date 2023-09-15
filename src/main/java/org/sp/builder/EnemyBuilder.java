package org.sp.builder;

import javafx.scene.image.Image;
import org.sp.entities.Enemy;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;

public class EnemyBuilder implements ObjectBuilder{
    public Enemy createEnemy(){
        return new Enemy();
    }
    @Override
    public void setImage(Image image) {

    }

    @Override
    public void setVector2D(Vector2D vector2D) {

    }

    @Override
    public void setHealth(double health) {

    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void setAnimator(Animator animator) {

    }
}
