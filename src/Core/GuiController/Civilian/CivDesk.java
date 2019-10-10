package Core.GuiController.Civilian;

import Core.GameWindow;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;




public class CivDesk
{
    @FXML Image pen;
    @FXML Canvas canvas, diffusercloud;
    GraphicsContext gc, gcCloud;

    @FXML
    private void initialize()
    {
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
            }
        }.start();


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

                /*
                * <ImageView translateX="-150" translateY="-180" preserveRatio="true">
        <Image url="@/img/diffusercloud.png" />
    </ImageView>
                * */

            }
        }.start();

    }

}
