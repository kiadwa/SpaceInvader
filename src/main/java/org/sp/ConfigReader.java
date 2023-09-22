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
                temp.add(((JSONObject)jsonEnemy.get("position")).get("x"));
                temp.add(((JSONObject)jsonEnemy.get("position")).get("y"));
                temp.add(jsonEnemy.get("projectile"));
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