package org.sp.builder;
import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.entities.Enemy;
import org.sp.physics.Vector2D;

import java.io.File;
import java.util.*;

public class EnemyBuilderDirector implements ConfigReader{
    public List<Enemy> constructListEnemy(EnemyBuilder builder){
        List<Enemy> result = new ArrayList<>();
        List<List<Object>> enemyData = ConfigReader.readEnemiesData();
        for(List<Object> obj: enemyData){
            builder.setImage(new Image(new File("src/main/resources/enemy.png").toURI().toString(),
                    100,
                    25,
                    true, true));
            Vector2D v2D = new Vector2D(((Number)obj.get(0)).doubleValue(),((Number) obj.get(1)).doubleValue());
            builder.setWidth(100);
            builder.setHeight(25);
            String projectileType = (String) obj.get(2);
            if(projectileType.equals("fast_straight")) builder.setProjectileType(true);
            else builder.setProjectileType(false);

            Enemy enemy = builder.createEnemy();
            result.add(enemy);


        }
        return result;
}
}
