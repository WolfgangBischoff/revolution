package Core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static Core.Util.RESOLUTION_HEIGTH;
import static Core.Util.RESOLUTION_WIDTH;

public class GameWindow extends Stage
{
    private static GameWindow singleton;
    private Stage gameStage;

    private GameWindow()
    {
        gameStage = new Stage();
    }

    public static GameWindow getSingleton()
    {
        if(singleton == null)
            singleton = new GameWindow();
        return singleton;
    }

    public Stage getGameStage()
    {
        return gameStage;
    }

    public Scene createNextScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            gameStage.setScene(new Scene (root, RESOLUTION_WIDTH, RESOLUTION_HEIGTH));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNextScene(Parent parent)
    {
        gameStage.setScene(new Scene(parent, RESOLUTION_WIDTH, RESOLUTION_HEIGTH));
    }

    public double getScreenWidth()
    {
        return gameStage.getScene().getWidth();
    }

    public double getScreenHeight()
    {
        return gameStage.getScene().getHeight();
    }

    public void showWindow()
    {
        gameStage.show();
    }

}
