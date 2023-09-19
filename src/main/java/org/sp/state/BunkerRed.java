package org.sp.state;

import javafx.scene.image.Image;
import org.sp.entities.Bunker;

import java.io.File;

public class BunkerRed implements BunkerState{
    @Override
    public void changeColor(Bunker bunker) {
        Image image = new Image(new File("src/main/resources/bunker_red.png").toURI().toString(),
                        bunker.getWidth(),
                        bunker.getHeight(), true, true);
        bunker.setImage(image);
    }
}
