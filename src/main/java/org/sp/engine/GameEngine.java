package org.sp.engine;

import java.util.ArrayList;
import java.util.List;

import org.sp.ConfigReader;
import org.sp.GameObject;
import org.sp.builder.BunkerBuilder;
import org.sp.builder.BunkerBuilderDirector;
import org.sp.builder.EnemyBuilder;
import org.sp.builder.EnemyBuilderDirector;
import org.sp.entities.Bunker;
import org.sp.entities.Enemy;
import org.sp.entities.Player;
import org.sp.physics.Moveable;
import org.sp.physics.Vector2D;
import org.sp.rendering.Renderable;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements ConfigReader {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private Player player;

	private boolean left;
	private boolean right;


	public GameEngine(String config){
		// read the config here
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		BunkerBuilderDirector bunkerbuilder = new BunkerBuilderDirector();
		EnemyBuilderDirector enemyBuilder = new EnemyBuilderDirector();
		List<Bunker> bunkerList = bunkerbuilder.constructBunkers(new BunkerBuilder());
		List<Enemy> enemiesList = enemyBuilder.constructListEnemy(new EnemyBuilder());

		player = new Player();
		renderables.add(player);
		renderables.addAll(bunkerList);
		renderables.addAll(enemiesList);

	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		movePlayer();
		for(GameObject go: gameobjects){
			go.update();
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
		player.shoot();
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
}
