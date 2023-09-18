package org.sp.entities;

import javafx.scene.Node;
import org.sp.rendering.Renderable;

public interface EntityView {
    void update(double xViewportOffset, double yViewportOffset);

    boolean matchesEntity(Renderable entity);
    Renderable getEntity();

    void markForDelete();

    Node getNode();

    boolean isMarkedForDelete();
}
