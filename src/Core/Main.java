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





/*
        Stage theStage = new Stage();
        theStage.setTitle("Timeline Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("img/pen.png");
        Image sun = new Image("img/food.png");
        Image space = new Image("img/background.jpg");

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // background image clears canvas
                gc.drawImage(space, 0, 0);
                gc.drawImage(earth, x, y);
                gc.drawImage(sun, 196, 196);
            }
        }.start();

        theStage.show();
*/

    }
}
