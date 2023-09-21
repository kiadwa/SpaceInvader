package org.sp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public interface ConfigReader {
    static final String path = "src/main/resources/config.json";
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
    static List<List<Object>> readEnemiesData(String filepath){
        List<List<Object>> result = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try{
            Object object = jsonParser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonEnemies = (JSONArray) jsonObject.get("Enemies");
            for(Object obj: jsonEnemies){
                JSONObject jsonEnemy = (JSONObject) obj;
                List<Object> temp = new ArrayList<>();
                temp.add((Object) ((JSONObject)jsonEnemy.get("position")).get("x"));
                temp.add((Object) ((JSONObject)jsonEnemy.get("position")).get("y"));
                temp.add((Object) (jsonEnemy.get("projectile")));
                result.add(temp);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
    static List<HashMap<String, Double[]>> readBunkersData(String filepath){
        List<HashMap<String, Double[]>> result = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try{
            Object object = jsonParser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonBunkers = (JSONArray) jsonObject.get("Bunkers");
            for(Object obj: jsonBunkers){
                JSONObject jsonBunker = (JSONObject) obj;
                HashMap<String, Double[]> temp = new HashMap<>();
                Double positionX;
                Double positionY;
                positionX = ((Number) ((JSONObject) jsonBunker.get("position")).get("x")).doubleValue();
                positionY = ((Number) ((JSONObject) jsonBunker.get("position")).get("y")).doubleValue();
                Double sizeX;
                Double sizeY;
                sizeX = ((Number) ((JSONObject) jsonBunker.get("size")).get("x")).doubleValue();
                sizeY = ((Number) ((JSONObject) jsonBunker.get("size")).get("y")).doubleValue();

                temp.put("position", new Double[]{positionX,positionY});
                temp.put("size", new Double[]{sizeX,sizeY});

                result.add(temp);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
    }
        return result;
    }
    static List<Integer> readGameWindowSize(){
        List<Integer> result = new ArrayList<>();
        JSONParser jsonparser = new JSONParser();
        try{
            Object object = jsonparser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONObject jsonGame = (JSONObject) jsonObject.get("Game");

            Integer gameX = ((Number) ((JSONObject) jsonGame.get("size")).get("x")).intValue();
            Integer gameY = ((Number) ((JSONObject) jsonGame.get("size")).get("y")).intValue();
            result.add(gameX);
            result.add(gameY);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

    }
    static  HashMap<String, Object> readPlayerData(){
        HashMap<String, Object> result = new HashMap<>();
        JSONParser jsonparser = new JSONParser();
        try{
            Object object = jsonparser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONObject jsonPlayer= (JSONObject) jsonObject.get("Player");

            Object playerX = ((JSONObject)(jsonPlayer.get("position"))).get("x");
            Object playerY = ((JSONObject)(jsonPlayer.get("position"))).get("y");
            Object speed = jsonPlayer.get("speed");
            Object lives = jsonPlayer.get("lives");
            Object colour = jsonPlayer.get("colour");
            result.put("colour", colour);
            result.put("x",  playerX);
            result.put("y", playerY);
            result.put("speed", speed);
            result.put("lives", lives);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}