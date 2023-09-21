package org.sp.engine;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import org.sp.ConfigReader;
import org.sp.entities.EntityViewImpl;
import org.sp.entities.SpaceBackground;
import javafx.util.Duration;

import org.sp.entities.EntityView;
import org.sp.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import static javafx.scene.canvas.GraphicsContext.*;

public class GameWindow implements ConfigReader {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private Renderable background;
    private GraphicsContext gc;
    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameEngine model){
		List<Integer> windowSize = ConfigReader.readGameWindowSize();
        this.width = windowSize.get(0);
        this.height = windowSize.get(1);
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        entityViews = new ArrayList<EntityView>();
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);

    }

	public void run() {

         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();

    }
    public void printScoreBoard(){
        gc.setFill(Paint.valueOf("WHITE"));
        gc.fillText(model.getScore().getScore().toString(),
                model.getScore().getPosition().getX(),
                model.getScore().getPosition().getY(), 100);
    }

    private void draw(){
        gc.clearRect(0, 0, width, height);
        printScoreBoard();

        if(model.checkIfGameEnd()) return;

        model.update();
        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                //if there are already entity view and exist renderable object, update its position.
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            // if there is renderables object and no entity view of it,
            // create new entity view and add it to 'entityViews'
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }
        //removing object
        for (EntityView entityView : entityViews) {
            Renderable renderable = entityView.getEntity();
            if(!renderables.contains(renderable)){
                entityView.markForDelete();
            }
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }

        entityViews.removeIf(EntityView::isMarkedForDelete);

    }

	public Scene getScene() {
        return scene;
    }
}
