package Core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    // https://stackoverflow.com/questions/33494052/javafx-redirect-console-output-to-textarea-that-is-created-in-scenebuilder
    private GameLoop gameLoop;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage gameWindow)
    {
        Simulation simulation = Simulation.getSingleton();

        //Console Window
        Stage consoleWindow = new Stage();
        consoleWindow.setTitle("SeGame Console");
        consoleWindow.setScene(new Scene(simulation.getConsole()));
        consoleWindow.show();

        //Game Window
        GameWindow gameWindowController = GameWindow.getSingleton();
        gameWindowController.createNextScene("../fxml/mainMenu.fxml");
        gameWindowController.showWindow();

        gameLoop = new GameLoop();
        gameLoop.start();
    }

    private class GameLoop extends AnimationTimer
    {

        private long before = System.nanoTime();
        private float delta;

        @Override
        public void handle(long now)
        {

            delta = (float) ((now - before) / 1E9);

            //System.out.println(this + " " + delta);

            before = now;
        }
    }
}
