package org.sp.builder;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.entities.Bunker;
import org.sp.physics.BoxCollider;
import org.sp.physics.Vector2D;
import org.sp.rendering.Animator;

public class BunkerBuilder implements ObjectBuilder{
    private Bunker product = new Bunker();
    public Bunker create(){
        return this.product;
    }
    @Override
    public void setImage(Image image) {
        product.setImage(image);
    }

    @Override
    public void setVector2D(Vector2D vector2D) {
        product.setPosition(vector2D);
    }

    @Override
    public void setHealth(double health) {
        this.product.setHealth(health);
    }

    @Override
    public void setWidth(double width) {
        this.product.setWidth(width);
    }

    @Override
    public void setHeight(double height) {
        this.product.setHeight(height);
    }


    @Override
    public void setAnimator(Animator animator) {
    }

    @Override
    public void setProjectileType(boolean bool) {
        return;
    }

    @Override
    public void setBoxCollider(BoxCollider boxCollider) {
        this.product.setBoxCollider(boxCollider);
    }
}
