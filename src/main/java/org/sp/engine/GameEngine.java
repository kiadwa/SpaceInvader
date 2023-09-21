package org.sp.engine;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.image.Image;
import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.UIentities.HealthBar;
import org.sp.UIentities.Score;
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
	private  List<Projectile> enemyProjectiles;
	private List<BoxCollider> enemyProjectileHitBox;
	private Projectile playerProjectile;
	private List<BoxCollider> enemyHitBox;
	private List<BoxCollider> bunkersHitBox;
	private EnemyGroup enemyGroup = new EnemyGroup();
	private Player player;
	private double PlayerLivesCount;
	private Score gameScore = new Score();
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
		PlayerLivesCount = player.getLives();
		renderables.add(player);
		playerHitBox = player.getBoxCollider();
		double healthBarRootX = 550;
		for(int i = 0; i < player.getLives(); i++){
			renderables.add(new HealthBar(new Vector2D(healthBarRootX,5)));
			healthBarRootX -= 50;
		}

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
			boxCollider.setBunker(bunker);
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
			Projectile toDelete = getProjectileReachEnd();
			renderables.remove(toDelete);
			gameobjects.remove(toDelete);
			enemyProjectiles.remove(toDelete);
			enemyProjectileHitBox.remove(toDelete.getBoxCollider());
		}

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
		//ensure to detect and process any collision that happens
		checkBunkerHitByPlayerProjectile();
		if(checkEnemyHit() || checkPlayerProjectileHitEnemyProjectile()) gameScore.incrementScore();
		checkBunkerHitByEnemyProjectile();
		BunkerHitByEnemy();
		checkPlayerHit();
		removeAHeart();


	}
	public void removeAHeart(){
		double currentLivesCount = player.getLives();

		if(currentLivesCount != PlayerLivesCount){
			for(Renderable renderable: renderables){
				if(renderable instanceof HealthBar){
					renderables.remove(renderable);
					break;
				}
			}
			PlayerLivesCount = currentLivesCount;
		}
	}
	public boolean checkIfGameEnd(){

		if(!player.isAlive()) return true;
		List<List<Enemy>> enemyLists = enemyGroup.getEnemyList();
		for(List<Enemy> enemyList: enemyLists){
			for(Enemy enemy: enemyList){
				if(enemy.getPosition().getY() >= player.getPosition().getY()) return true;
			}
		}
        return enemyLists.get(0).isEmpty() && enemyLists.get(1).isEmpty();
    }
	public void checkPlayerHit(){
		for(BoxCollider boxCollider: enemyProjectileHitBox){
			if(boxCollider != null && boxCollider.isColliding(playerHitBox)){
				player.takeDamage(boxCollider.getProjectile().getDamage());
				renderables.remove(boxCollider.getEntity());
				enemyProjectileHitBox.remove(boxCollider);
			}
		}
	}
	public boolean checkEnemyHit(){

		for(BoxCollider boxCollider: enemyHitBox){
			if(playerProjectileHitBox!= null && playerProjectileHitBox.isColliding(boxCollider)){
					enemyGroup.removeEnemy(boxCollider.getEnemy());
					renderables.remove(boxCollider.getEntity());
					enemyHitBox.remove(boxCollider);
					renderables.remove(playerProjectile);
					gameobjects.remove(playerProjectile);
					playerProjectile = null;
					playerProjectileHitBox = null;
					return true;
			}
		}
		return false;
	}
	public boolean checkPlayerProjectileHitEnemyProjectile(){
		for(BoxCollider boxCollider: enemyProjectileHitBox){
			if(playerProjectileHitBox != null && playerProjectileHitBox.isColliding(boxCollider)){
				renderables.remove(playerProjectile);
				renderables.remove(boxCollider.getProjectile());
				gameobjects.remove(playerProjectile);
				playerProjectile = null;
				playerProjectileHitBox = null;
				enemyProjectileHitBox.remove(boxCollider);
				return true;

			}
		}
		return false;
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
				Bunker bunkerHit = boxCollider.getBunker();
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
					enemyProjectiles.remove(boxColliderEnemyProjectile.getProjectile());
					//Change state of bunker
					Bunker bunkerHit = boxCollider.getBunker();
					//pass to bunkerStateRealtimeManagement to change bunker
					bunkerStateRealtimeManagement(bunkerHit, boxCollider);
				}
			}
		}
	}
	public void BunkerHitByEnemy(){
		for(BoxCollider bunkerBoxCollider: bunkersHitBox){
			for(BoxCollider enemyBoxCollider: enemyHitBox){
				if(enemyBoxCollider.isColliding(bunkerBoxCollider)){
					renderables.remove(bunkerBoxCollider.getEntity());
					bunkersHitBox.remove(bunkerBoxCollider);
					gameobjects.remove(bunkerBoxCollider.getBunker());
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
	public Score getScore(){return this.gameScore;}


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
	public Projectile getProjectileReachEnd(){
		if(enemyProjectiles.isEmpty()) return null;
		else{
			for(Projectile projectile: enemyProjectiles){
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
		int randomRowIndex = rand.nextInt(0,enemyGroup.getEnemyList().size());
		if(enemyGroup.getEnemyList().get(randomRowIndex).isEmpty()){return null;}
		int randomColIndex = rand.nextInt(0,enemyGroup.getEnemyList().get(randomRowIndex).size());
		chosenOne = enemyGroup.getEnemyList().get(randomRowIndex).get(randomColIndex);
		return chosenOne;
	}


}
