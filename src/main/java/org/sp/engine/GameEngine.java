package org.sp.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.builder.BunkerBuilder;
import org.sp.builder.EnemyBuilder;
import org.sp.entities.Bunker;
import org.sp.entities.Enemy;
import org.sp.entities.EnemyGroup;
import org.sp.entities.Player;
import org.sp.factory.PlayerProjectile;
import org.sp.factory.Projectile;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements ConfigReader {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private EnemyGroup enemyGroup= new EnemyGroup();
	private Player player;

	private boolean left;
	private boolean right;


	public GameEngine(String config){
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();

		// read the config here
		List<HashMap<String, Double[]>> bunkerData = ConfigReader.readBunkersData(config);
		List<List<Object>> enemyData = ConfigReader.readEnemiesData(config);
		// generate bunkers
		for(HashMap<String, Double[]> hm: bunkerData){
			BunkerBuilder bunkerBuilder = new BunkerBuilder();
			Vector2D v2D = new Vector2D(hm.get("position")[0],hm.get("position")[1]);
			bunkerBuilder.setHeight(hm.get("size")[0]);
			bunkerBuilder.setWidth(hm.get("size")[1]);
			bunkerBuilder.setImage(new Image(new File("src/main/resources/bunker.png").toURI().toString(),
					hm.get("size")[0],
					hm.get("size")[1], true, true));
			bunkerBuilder.setVector2D(v2D);
			Bunker bunker = bunkerBuilder.create();
			renderables.add(bunker);
			//gameobjects.add(bunker);
		}
		//generate enemy
		for(List<Object> lobj: enemyData){
			EnemyBuilder enemyBuilder = new EnemyBuilder();
			Vector2D v2D = new Vector2D(((Number)lobj.get(0)).doubleValue(),((Number) lobj.get(1)).doubleValue());
			String projectileFast = new String((String) lobj.get(2));
			enemyBuilder.setImage(new Image(new File("src/main/resources/enemy_white.png").toURI().toString(),
					100,
					25,
					true, true));
			enemyBuilder.setVector2D(v2D);
            enemyBuilder.setProjectileType(projectileFast.equals("fast_straight"));
			Enemy enemy = enemyBuilder.createEnemy();
			renderables.add(enemy);
			//gameobjects.add(enemy);
			enemyGroup.addEnemy(enemy);
		}


	//	BunkerBuilderDirector bunkerbuilder = new BunkerBuilderDirector();
	//	EnemyBuilderDirector enemyBuilder = new EnemyBuilderDirector();


		player = new Player();
		renderables.add(player);


	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		movePlayer();
		for(GameObject go: gameobjects){
			go.update();
		}
		enemyGroup.moveEnemy();
		enemyGroup.updateMoveScheme();

		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= 600) {
				ro.getPosition().setX(599-ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= 800) {
				ro.getPosition().setY(799-ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(containsProjectileRenderable(renderables) || containsProjectileGameObjects(gameobjects)) return false;
		Projectile projectile = player.shoot();
		renderables.add(projectile);
		gameobjects.add(projectile);
		return true;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}
	public static boolean containsProjectileRenderable(List<Renderable> array) {
		for (Object obj : array) {
			if (obj != null && obj instanceof Projectile) {
				return true;
			}
		}
		return false;
	}
	public static boolean containsProjectileGameObjects (List<GameObject> array) {
		for (Object obj : array) {
			if (obj != null && obj instanceof Projectile) {
				return true;
			}
		}
		return false;
	}

}
