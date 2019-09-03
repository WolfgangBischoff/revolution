package Core;

import javafx.application.Application;
import javafx.scene.Scene;
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
    }
}
