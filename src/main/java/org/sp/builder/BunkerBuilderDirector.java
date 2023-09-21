package org.sp.builder;

import javafx.scene.image.Image;
import org.sp.entities.Bunker;
import org.sp.physics.BoxCollider;
import org.sp.physics.Vector2D;
import org.sp.state.BunkerState;

public class BunkerBuilderDirector {
    private BunkerBuilder builder;
    private Vector2D position;
    private double width;
    private double height;
    private BunkerState state;
    private BoxCollider boxCollider;
    private Image image;
    public BunkerBuilderDirector(BunkerBuilder bunkerBuilder,
                                 Image image,
                                 Vector2D position,
                                 double width,
                                 double height,
                                 BunkerState state,
                                 BoxCollider boxCollider){
        this.builder = bunkerBuilder;
        this.image = image;
        this.position = position;
        this.width = width;
        this.height = height;
        this.state = state;
        this.boxCollider = boxCollider;
    }
    public void constructBunker(){
        builder.setWidth(width);
        builder.setHeight(height);
        builder.setImage(image);
        builder.setVector2D(position);
        builder.setBoxCollider(boxCollider);
        builder.setState(state);

    }
    public Bunker returnBunker(){
        return builder.create();
    }

}
