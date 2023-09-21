package org.sp.UIentities;

import javafx.scene.image.Image;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

public class Score implements Renderable {
    private Integer Score = 0;
    Vector2D position = new Vector2D(300,10);
    public Integer getScore(){return this.Score;}


    public void incrementScore(){
        Score += 10;
    }
    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public Vector2D getPosition() {
         return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.BACKGROUND;
    }
}
