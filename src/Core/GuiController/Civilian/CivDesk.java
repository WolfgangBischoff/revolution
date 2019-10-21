package Core.GuiController.Civilian;

import Core.GameWindow;
import Core.GuiController.AnimatedSprite;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class CivDesk
{
    //https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
    @FXML Image pen;
    @FXML Canvas canvas, diffusercloud;
    @FXML ImageView smokeSprite;
    GraphicsContext gc, gcCloud;

    Image sun = new Image("img/bee.png");
    Image[] imageArray = new Image[3];
    long lastTime = 0;
    final long startNanoTime = System.nanoTime();
    AnimatedSprite animatedSprite;

    @FXML
    private void initialize()
    {
        //animatedSprite = new AnimatedSprite(smokeSprite);
        //animatedSprite.start();

        imageArray[0] = new Image( "img/diffuserSmoke.png" );
        imageArray[1] = new Image( "img/diffuserSmoke0.png" );
        imageArray[2] = new Image( "img/diffuserSmoke1.png" );

        Image sun = new Image("img/bee.png");
        gc = canvas.getGraphicsContext2D();
        final long startNanoTime = System.nanoTime();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gc.clearRect(0,0,500,500);

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double x = 150 + 80 * Math.cos(t);
                double y = 100 + 80 * Math.sin(t);
                gc.drawImage(sun, x,y);
                //System.out.println("Bee " + this);
            }
        }.start();

        Image[] imageArray = new Image[3];
        imageArray[0] = new Image( "img/diffuserSmoke.png" );
        imageArray[1] = new Image( "img/diffuserSmoke0.png" );
        imageArray[2] = new Image( "img/diffuserSmoke1.png" );
        //imageArray[3] = new Image( "img/diffuserSmoke3.png" );
        gcCloud = diffusercloud.getGraphicsContext2D();
        new AnimationTimer()
        {
            double x = 130;
            double y = 90;
            long lastTime = System.nanoTime();
            int idx = 0;
            public void handle(long currentNanoTime)
            {

                double time = (currentNanoTime - lastTime);
                if(time > 400000000)
                {
                    gcCloud.clearRect(0,0,500,500);
                    idx++;
                    idx = idx % imageArray.length;
                    lastTime = currentNanoTime;
                    //System.out.println("Diff " + this);
                }
                //gcCloud.drawImage(imageArray[(int)((time % (imageArray.length * 0.1)) / 0.1)], 130, 90);
                gcCloud.drawImage(imageArray[idx], 145, 60);

            }
        }.start();




/*

<ImageView translateX="-145" translateY="-195" preserveRatio="true">
        <Image url="@/img/diffuserSmoke.png" />
    </ImageView>
 */


/*
        Image cloud = new Image("img/diffusercloud.png", 70,70,true, true);
        gcCloud = diffusercloud.getGraphicsContext2D();
        new AnimationTimer()
        {
            double x = 130;
            double y = 90;
            long lastTime = System.nanoTime();
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - lastTime);
                //System.out.println((currentNanoTime - lastTime));
                if(t > 12000000)
                {
                    gcCloud.clearRect(0,0,500,500);
                    x += 1.4;
                    y -= 1;
                    gcCloud.drawImage(cloud, x,y);
                }

                if(y < 0)
                {
                    x=130;
                    y=90;
                }

                lastTime = currentNanoTime;
            }
        }.start();
*/
    }

    public void stopAnimations()
    {
        animatedSprite.stop();
    }

}
