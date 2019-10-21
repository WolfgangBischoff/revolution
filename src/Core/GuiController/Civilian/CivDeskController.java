package Core.GuiController.Civilian;

import Core.GuiController.AnimatedSprite;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivDeskController extends AnimationTimer
{
    @FXML
    ImageView smokeSprite;
    @FXML
    Canvas canvas;
    GraphicsContext gc;
    FXMLLoader loader;
    AnimatedSprite animatedSprite;
    final long startNanoTime = System.nanoTime();

    public CivDeskController()
    {
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civDesk2.fxml"));
        loader.setController(this);
    }

    @Override
    public void handle(long currentNanoTime)
    {
        Image sun = new Image("img/bee.png");
        gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, 500, 500);
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        double x = 150 + 80 * Math.cos(t);
        double y = 100 + 80 * Math.sin(t);
        gc.drawImage(sun, x, y);
    }

    @FXML
    private void initialize()
    {
        animatedSprite = new AnimatedSprite(smokeSprite, "diffuserSmokeSprites.png", 8, 6, 6, 1, 120, 140);
        animatedSprite.start();
        start();
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void stopSprites()
    {
        animatedSprite.stop();
        stop();
    }

}
