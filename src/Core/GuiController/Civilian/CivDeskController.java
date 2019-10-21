package Core.GuiController.Civilian;

import Core.GuiController.AnimatedSprite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivDeskController
{
    @FXML
    ImageView smokeSprite;
    FXMLLoader loader;
    AnimatedSprite animatedSprite;

    public CivDeskController()
    {
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civDesk2.fxml"));
        loader.setController(this);
    }

    @FXML
    private void initialize()
    {
        animatedSprite = new AnimatedSprite(smokeSprite);
        animatedSprite.start();
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void stopSprites()
    {
        animatedSprite.stop();
    }

}
