package org.sp.state;

import javafx.scene.image.Image;
import org.sp.entities.Bunker;

import java.io.File;

public class BunkerGreen implements BunkerState{
    @Override
    public void changeColor(Bunker bunker) {
        Image image = new Image(new File("src/main/resources/bunker_green.png").toURI().toString(),
                bunker.getWidth(),
                bunker.getHeight(), false, true);
        bunker.setImage(image);
        bunker.setHealth(300);
    }
}
