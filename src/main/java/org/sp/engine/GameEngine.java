package org.sp.engine;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
import org.sp.factory.Projectile;
import org.sp.physics.BoxCollider;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;
import org.sp.state.BunkerGreen;
import org.sp.state.BunkerRed;
import org.sp.state.BunkerYellow;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements ConfigReader {
	private BoxCollider playerProjectileHitBox;
	private BoxCollider playerHitBox;
	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private  List<EnemyProjectile> enemyProjectiles;
	private List<BoxCollider> enemyProjectileHitBox;
	private Projectile playerProjectile;
	private List<BoxCollider> enemyHitBox;
	private List<BoxCollider> bunkersHitBox;
	private EnemyGroup enemyGroup = new EnemyGroup();
	private Player player;

	private boolean left;
	private boolean right;


	public GameEngine(String config){
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		enemyProjectiles = new CopyOnWriteArrayList<>();
		enemyHitBox = new CopyOnWriteArrayList<>();
		bunkersHitBox = new CopyOnWriteArrayList<>();
		enemyProjectileHitBox = new CopyOnWriteArrayList<>();
		initializeBunkers(config);
		initializeEnemies(config);
		player = new Player();
		renderables.add(player);
		playerHitBox = player.getBoxCollider();

	}
	public void initializeBunkers(String config){
		List<HashMap<String, Double[]>> bunkerData = ConfigReader.readBunkersData(config);
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
	}
	public void initializeEnemies(String config){
		List<List<Object>> enemyData = ConfigReader.readEnemiesData(config);
		List<Enemy> firstRowEnemy = new CopyOnWriteArrayList<>();
		List<Enemy> secondRowEnemy = new CopyOnWriteArrayList<>();
		for(List<Object> lobj: enemyData){
			EnemyBuilder enemyBuilder = new EnemyBuilder();
			Enemy enemy = enemyBuilder.createEnemy();
			Image slow_shooter_alien_img = new Image(new File("src/main/resources/enemy_white.png").toURI().toString(),
					enemy.getWidth(),
					enemy.getHeight(),
					false,
					true);
			Image fast_shooter_alien_img = new Image(new File("src/main/resources/enemywhite2.png").toURI().toString(),
					enemy.getWidth(),
					enemy.getHeight(),
					false,
					true);
			Vector2D v2D = new Vector2D(((Number)lobj.get(0)).doubleValue(),((Number) lobj.get(1)).doubleValue());
			String projectileFast = (String) lobj.get(2);
			enemyBuilder.setVector2D(v2D);
			BoxCollider boxCollider = new BoxCollider(enemy.getWidth(),enemy.getHeight(),v2D,enemy);
			boxCollider.setEnemy(enemy);
			enemyBuilder.setBoxCollider(boxCollider);
			if(projectileFast.equals("fast_straight")){
				enemyBuilder.setProjectileType(true);
				enemyBuilder.setImage(fast_shooter_alien_img);

			}else{
				enemyBuilder.setProjectileType(false);
				enemyBuilder.setImage(slow_shooter_alien_img);
			}
			if(enemy.getPosition().getY() == 100) firstRowEnemy.add(enemy);
			else secondRowEnemy.add(enemy);
			renderables.add(enemy);
			//gameobjects.add(enemy);

			enemyHitBox.add(boxCollider);
		}
		enemyGroup.addEnemy(firstRowEnemy);
		enemyGroup.addEnemy(secondRowEnemy);
		enemyGroup.setEnemyCount();
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
		enemyShoot();
		enemyGroup.updateMoveScheme();
		// remove Player projectile if reach end of screen. (1.0 is out of screen)
		if(isPlayerProjectileReachEnd()){
			renderables.remove(playerProjectile);
			gameobjects.remove(playerProjectile);
			playerProjectile = null;
			if(!(playerProjectileHitBox == null)) playerProjectileHitBox = null;
		}
		//remove enemy projectile if reach end of screen. (799 is out of screen)
		if(isEnemyProjectilesReachEnd()){
			EnemyProjectile toDelete = getProjectileReachEnd();
			renderables.remove(toDelete);
			gameobjects.remove(toDelete);
			enemyProjectiles.remove(toDelete);
			enemyProjectileHitBox.remove(toDelete.getBoxCollider());
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
		//ensure to detect any collision by Player projectile that happens
		checkBunkerHitByPlayerProjectile();
		checkEnemyHit();
		checkBunkerHitByEnemyProjectile();

	}
	int cnt = 0;
	public void checkEnemyHit(){

		for(BoxCollider boxCollider: enemyHitBox){
			if(playerProjectileHitBox!= null && playerProjectileHitBox.isColliding(boxCollider)){
					enemyGroup.removeEnemy(boxCollider.getEnemy());
					renderables.remove(boxCollider.getEntity());
					enemyHitBox.remove(boxCollider);
					renderables.remove(playerProjectile);
					gameobjects.remove(playerProjectile);
					playerProjectile = null;
					playerProjectileHitBox = null;
			}
		}
	}
	public void checkBunkerHitByPlayerProjectile(){
		for(BoxCollider boxCollider: bunkersHitBox){
			//Check if projectile hit any bunker
			if(playerProjectileHitBox!= null && playerProjectileHitBox.isColliding(boxCollider)){
				playerProjectileHitBox = null;
				if(playerProjectile != null) {
					renderables.remove(playerProjectile);
					gameobjects.remove(playerProjectile);
					playerProjectile = null;
				}
				//Change state of bunker
				Renderable renderableHit = boxCollider.getEntity();
				Bunker bunkerHit = (Bunker) renderableHit;
				//pass to bunkerStateRealtimeManagement to change bunker
				bunkerStateRealtimeManagement(bunkerHit,boxCollider);
			}
		}
	}
	public void checkBunkerHitByEnemyProjectile(){
		for(BoxCollider boxCollider: bunkersHitBox){
			//Check if projectile hit any bunker
			for(BoxCollider boxColliderEnemyProjectile: enemyProjectileHitBox){
				if(boxCollider.isColliding(boxColliderEnemyProjectile)) {
					enemyProjectileHitBox.remove(boxColliderEnemyProjectile);
					renderables.remove(boxColliderEnemyProjectile.getEntity());
					gameobjects.remove(boxColliderEnemyProjectile.getProjectile());
					enemyProjectiles.remove((EnemyProjectile) boxColliderEnemyProjectile.getProjectile());
					//Change state of bunker
					Renderable renderableHit = boxCollider.getEntity();
					Bunker bunkerHit = (Bunker) renderableHit;
					//pass to bunkerStateRealtimeManagement to change bunker
					bunkerStateRealtimeManagement(bunkerHit, boxCollider);
				}
			}
		}
	}
	public void bunkerStateRealtimeManagement(Bunker bunkerHit, BoxCollider boxCollider){
		if(bunkerHit.getCurrentState() instanceof BunkerGreen){
			bunkerHit.setCurrentState(new BunkerYellow());
			bunkerHit.changeColor();
		}else if(bunkerHit.getCurrentState() instanceof BunkerYellow){
			bunkerHit.setCurrentState(new BunkerRed());
			bunkerHit.changeColor();
		}else if(bunkerHit.getCurrentState() instanceof BunkerRed){
			bunkerHit.setCurrentState(null);
			renderables.remove(bunkerHit);
			gameobjects.remove(bunkerHit);
			bunkersHitBox.remove(boxCollider);
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
		boolean existenceCheck = (playerProjectile != null);
		boolean projectileReachEnd = isPlayerProjectileReachEnd();
		if(existenceCheck && !projectileReachEnd){
			return false;
		}else{
			Projectile projectile = player.shoot();
			playerProjectileHitBox = projectile.getBoxCollider();
			renderables.add(projectile);
			gameobjects.add(projectile);
			playerProjectile = projectile;
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
		if(playerProjectile == null) return false;
        return playerProjectile.getPosition().getY() <= 1;
    }

	 public boolean isEnemyProjectilesReachEnd(){
		if(enemyProjectiles.isEmpty()) return false;
		else{
			for(Projectile projectile: enemyProjectiles){
				if(projectile.getPosition().getY() >= 774) return true;
			}
		}
		return false;
	}
	public EnemyProjectile getProjectileReachEnd(){
		if(enemyProjectiles.isEmpty()) return null;
		else{
			for(EnemyProjectile projectile: enemyProjectiles){
				if(projectile.getPosition().getY() >= 774) return projectile;
			}
		}
		return null;
	}

	public void enemyShoot(){
		Enemy enemy = getRandomEnemy();
		if(enemyProjectiles.size() >= 3 || enemy == null) return;

		EnemyProjectile enemyProjectile = enemy.shoot();
		enemyProjectile.getBoxCollider().setProjectile(enemyProjectile);
		enemyProjectiles.add(enemyProjectile);
		renderables.add(enemyProjectile);
		gameobjects.add(enemyProjectile);
		enemyProjectileHitBox.add(enemyProjectile.getBoxCollider());
	}
	public Enemy getRandomEnemy(){
		Enemy chosenOne;
		Random rand = new Random();
		if(enemyGroup.getRealTimeCount() == 0) return null;
		int randomRowIndex = rand.nextInt(enemyGroup.getEnemyList().size());
		int randomColIndex = rand.nextInt(enemyGroup.getEnemyList().get(randomRowIndex).size());
		chosenOne = enemyGroup.getEnemyList().get(randomRowIndex).get(randomColIndex);
		return chosenOne;
	}


}
