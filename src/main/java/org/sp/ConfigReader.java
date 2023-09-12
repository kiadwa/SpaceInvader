package org.sp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public interface ConfigReader {
    /**
     * You will probably not want to use a static method/class for this.
     *
     * This is just an example of how to access different parts of the json
     *
     * @param path The path of the json file to read
     */
    public static void parse(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(path));
// convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) object;
// reading the Game section:
            JSONObject jsonGame = (JSONObject) jsonObject.get("Game");
// reading a coordinate from the nested section within the game
// note that the game x and y are of type Long (i.e. they are int)

            Long gameX = (Long) ((JSONObject) jsonGame.get("size")).get("x");
            Long gameY = (Long) ((JSONObject) jsonGame.get("size")).get("y");
// TODO: delete me, this is just a demonstration:
            System.out.println("Game details: x: " + gameX + " Game details: y: " + gameY);
// reading the "Enemies" array:
            JSONArray jsonEnemies = (JSONArray) jsonObject.get("Enemies");
// reading from the array:
            for (Object obj : jsonEnemies) {
                JSONObject jsonEnemy = (JSONObject) obj;
// the enemy position is a double
                Double positionX;
                Double positionY;
                positionX = ((Number) ((JSONObject) jsonEnemy.get("position")).get("x")).doubleValue();
                positionY = ((Number) ((JSONObject) jsonEnemy.get("position")).get("y")).doubleValue();
    String projectileStrategy = (String) jsonEnemy.get("projectile");
// TODO: delete me, this is just a demonstration:
                System.out.println("Enemy x: " +
                        positionX + " Enemy y: " + positionY +
                        ", projectile: " +
                        projectileStrategy);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}