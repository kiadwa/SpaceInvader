package org.sp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.sp.engine.GameEngine;
import org.sp.engine.GameWindow;

import java.util.Map;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Map<String, String> params = getParameters().getNamed();

        GameEngine model = new GameEngine("src/main/resources/config.json");
        GameWindow window = new GameWindow(model);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();
    }
}
