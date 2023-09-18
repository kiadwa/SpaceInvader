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
import org.sp.factory.EnemyProjectile;
import org.sp.factory.PlayerProjectile;
import org.sp.factory.Projectile;
import org.sp.physics.BoxCollider;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements ConfigReader {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private  List<Projectile> projectiles = new ArrayList<>();
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
			Bunker bunker = bunkerBuilder.create();
			Vector2D v2D = new Vector2D(hm.get("position")[0],hm.get("position")[1]);
			bunkerBuilder.setHeight(hm.get("size")[0]);
			bunkerBuilder.setWidth(hm.get("size")[1]);
			bunkerBuilder.setImage(new Image(new File("src/main/resources/bunker.png").toURI().toString(),
					hm.get("size")[0],
					hm.get("size")[1], true, true));
			bunkerBuilder.setVector2D(v2D);
			bunkerBuilder.setBoxCollider(new BoxCollider(hm.get("size")[0], hm.get("size")[1], v2D));
			renderables.add(bunker);
			gameobjects.add(bunker);
		}
		//generate enemy
		for(List<Object> lobj: enemyData){
			EnemyBuilder enemyBuilder = new EnemyBuilder();
			Enemy enemy = enemyBuilder.createEnemy();
			Vector2D v2D = new Vector2D(((Number)lobj.get(0)).doubleValue(),((Number) lobj.get(1)).doubleValue());
			String projectileFast = new String((String) lobj.get(2));
			enemyBuilder.setImage(new Image(new File("src/main/resources/enemy_white.png").toURI().toString(),
					100,
					25,
					true,
					true));
			enemyBuilder.setVector2D(v2D);
            enemyBuilder.setProjectileType(projectileFast.equals("fast_straight"));
			enemyBuilder.setBoxCollider(new BoxCollider(enemy.getWidth(),enemy.getHeight(),v2D));
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
		//move enemy in group
		enemyGroup.moveEnemy();
		enemyGroup.updateMoveScheme();
		// remove projectile if reach end of screen. (1.0 is out of screen)
		if(isPlayerProjectileReachEnd()){
			Projectile projectile = projectiles.remove(projectiles.size() - 1);
			renderables.remove(projectile);
			gameobjects.remove(projectile);
		}
		//System.out.println(projectiles.size());
		//System.out.println(renderables.size());
		//System.out.println(gameobjects.size());


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
		//ensure to detect any collision that happens
		//todo
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
		boolean existenceCheck = (!projectiles.isEmpty());
		boolean projectileReachEnd = isPlayerProjectileReachEnd();
		if(existenceCheck && !projectileReachEnd){
			return false;
		}else{
			Projectile projectile = player.shoot();
			renderables.add(projectile);
			gameobjects.add(projectile);
			projectiles.add(projectile);
			return true;
		}
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public boolean isPlayerProjectileReachEnd(){
		if(projectiles.size() == 0) return false;
		else{
			for(Projectile projectile: projectiles){
				if(projectile.getPosition().getY() <= 1) return true;
			}
		}
		return false;
	}

	public void removeProjectile(Projectile projectile){
		if(!projectiles.contains(projectile)) return;
		projectiles.remove(projectile);
	}

	public static boolean containsPlayerProjectileRenderable(List<Renderable> array) {
		for (Object obj : array) {
			if (obj != null && obj instanceof PlayerProjectile) {
				return true;
			}
		}
		return false;
	}
	public static boolean containsPlayerProjectileGameObjects (List<GameObject> array) {
		for (Object obj : array) {
			if (obj != null && obj instanceof PlayerProjectile) {
				return true;
			}
		}
		return false;
	}
	public static boolean containsEnemyProjectileRenderable(List<Renderable> array) {
		int cnt = 3;
		for (Object obj : array) {
			if (obj != null && obj instanceof EnemyProjectile) {
				cnt --;
			}
			if(cnt == 0) return true;
		}
		return false;
	}
	public static boolean containsEnemyProjectileGameObjects (List<GameObject> array) {
		int cnt = 3;
		for (Object obj : array) {
			if (obj != null && obj instanceof EnemyProjectile) {
				cnt --;
			}
			if(cnt == 0) return true;
		}
		return false;
	}

}
