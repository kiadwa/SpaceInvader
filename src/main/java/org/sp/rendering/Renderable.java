package org.sp.rendering;

import org.sp.GameObject;
import org.sp.physics.Vector2D;
import org.sp.engine.GameEngine;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Represents something that can be rendered
 */
public interface Renderable {

    public Image getImage();

    public double getWidth();
    public double getHeight();

    public Vector2D getPosition();

    public Renderable.Layer getLayer();

    /**
     * The set of available layers
     */
    public static enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }
}
