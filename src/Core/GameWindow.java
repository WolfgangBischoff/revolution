package Core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

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
            gameStage.setScene(new Scene (root, 600, 700));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNextScene(Parent parent)
    {
        gameStage.setScene(new Scene(parent, 600, 700));
    }

    public void showWindow()
    {
        gameStage.show();
    }

}
