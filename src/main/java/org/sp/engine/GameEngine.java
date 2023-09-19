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
import org.sp.state.BunkerGreen;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements ConfigReader {
	private BoxCollider playerProjectileHitBox;
	private BoxCollider playerHitBox;
	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private  List<Projectile> projectiles;
	private List<BoxCollider> enemyHitBox;
	private List<BoxCollider> bunkersHitBox;
	private EnemyGroup enemyGroup= new EnemyGroup();
	private Player player;

	private boolean left;
	private boolean right;


	public GameEngine(String config){
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		projectiles = new ArrayList<>();
		enemyHitBox = new ArrayList<>();
		bunkersHitBox = new ArrayList<>();

		// read the config here
		List<HashMap<String, Double[]>> bunkerData = ConfigReader.readBunkersData(config);
		List<List<Object>> enemyData = ConfigReader.readEnemiesData(config);
		// generate bunkers
		for(HashMap<String, Double[]> hm: bunkerData){
			BunkerBuilder bunkerBuilder = new BunkerBuilder();
			Bunker bunker = bunkerBuilder.create();
			Vector2D v2D = new Vector2D(hm.get("position")[0],hm.get("position")[1]);
			Double width = hm.get("size")[0];
			Double height = hm.get("size")[1];
			//builder
			bunkerBuilder.setHeight(height);
			bunkerBuilder.setWidth(width);
			//state
			bunker.setCurrentState(new BunkerGreen());
			bunker.changeColor();
			//builder
			bunkerBuilder.setVector2D(v2D);
			BoxCollider boxCollider = new BoxCollider(hm.get("size")[0], hm.get("size")[1],v2D,bunker);
			bunkerBuilder.setBoxCollider(boxCollider);
			//add into GameEngine
			renderables.add(bunker);
			gameobjects.add(bunker);
			bunkersHitBox.add(boxCollider);
		}
		//generate enemy
		for(List<Object> lobj: enemyData){
			EnemyBuilder enemyBuilder = new EnemyBuilder();
			Enemy enemy = enemyBuilder.createEnemy();
			Vector2D v2D = new Vector2D(((Number)lobj.get(0)).doubleValue(),((Number) lobj.get(1)).doubleValue());
			String projectileFast = new String((String) lobj.get(2));
			enemyBuilder.setImage(new Image(new File("src/main/resources/enemy.png").toURI().toString(),
					enemy.getWidth(),
					enemy.getHeight(),
					true,
					true,
					false));
			enemyBuilder.setVector2D(v2D);
            enemyBuilder.setProjectileType(projectileFast.equals("fast_straight"));
			BoxCollider boxCollider = new BoxCollider(enemy.getWidth(),enemy.getHeight(),v2D,enemy);
			enemyBuilder.setBoxCollider(boxCollider);
			renderables.add(enemy);
			//gameobjects.add(enemy);
			enemyGroup.addEnemy(enemy);
			enemyHitBox.add(boxCollider);
		}


	//	BunkerBuilderDirector bunkerbuilder = new BunkerBuilderDirector();
	//	EnemyBuilderDirector enemyBuilder = new EnemyBuilderDirector();


		player = new Player();
		renderables.add(player);
		playerHitBox = player.getBoxCollider();


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
			if(!(playerProjectileHitBox == null)) playerProjectileHitBox = null;
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
		checkBunkerHit();
		checkEnemyHit();
	}
	public void checkEnemyHit(){
		for(BoxCollider boxCollider: enemyHitBox){
			if(playerProjectileHitBox!= null && playerProjectileHitBox.isColliding(boxCollider)){
				System.out.println("collided");
				playerProjectileHitBox = null;
				Projectile projectile = projectiles.remove(projectiles.size() - 1);
				renderables.remove(projectile);
				gameobjects.remove(projectile);
			}
		}
	}
	public void checkBunkerHit(){
		for(BoxCollider boxCollider: bunkersHitBox){
			if(playerProjectileHitBox!= null && playerProjectileHitBox.isColliding(boxCollider)){
				System.out.println("collided");
				playerProjectileHitBox = null;
				Projectile projectile = projectiles.remove(projectiles.size() - 1);
				renderables.remove(projectile);
				gameobjects.remove(projectile);
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
		boolean existenceCheck = (!projectiles.isEmpty());
		boolean projectileReachEnd = isPlayerProjectileReachEnd();
		if(existenceCheck && !projectileReachEnd){
			return false;
		}else{
			Projectile projectile = player.shoot();
			playerProjectileHitBox = projectile.getBoxCollider();
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
