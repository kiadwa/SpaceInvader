package org.sp.builder;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.entities.Bunker;
import org.sp.physics.Vector2D;

import java.io.File;
import java.util.HashMap;

import java.util.*;
public class BunkerBuilderDirector implements ConfigReader {
    public List<Bunker> constructBunkers(BunkerBuilder builder){
        List< HashMap<String, Double[]>> bunkerDetails = ConfigReader.readBunkersData();
        List<Bunker> result = new ArrayList<>();

        for(HashMap<String, Double[]> hm: bunkerDetails){
            Double[] coord = hm.get("position") ;
            Double[] size = hm.get("size");
            builder.setHeight(size[1]);
            builder.setWidth(size[0]);
            Vector2D v2D = new Vector2D(coord[0], coord[1]);
            builder.setVector2D(v2D);
            Image image = new Image(new File("src/main/resources/player.png").toURI().toString(), size[0], size[1], true, true);
            builder.setImage(image);
            Bunker bunker = builder.create();
            result.add(bunker);

        }
        return result;
    }
}
