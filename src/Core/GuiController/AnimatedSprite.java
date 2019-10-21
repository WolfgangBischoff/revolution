package Core.GuiController;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends AnimationTimer
{
    ImageView imageView;
    Image[] imageArray = new Image[3];

    public AnimatedSprite(ImageView imageView)
    {
        this.imageView = imageView;
        imageArray[0] = new Image( "img/diffuserSmoke.png" );
        imageArray[1] = new Image( "img/diffuserSmoke0.png" );
        imageArray[2] = new Image( "img/diffuserSmoke1.png" );
    }


    long lastTime = System.nanoTime();
    int idx = 0;
    public void handle(long currentNanoTime)
    {

        double time = (currentNanoTime - lastTime);
        if(time > 400000000)
        {
            idx++;
            idx = idx % imageArray.length;
            lastTime = currentNanoTime;
        }
        System.out.println("Animated Sprite: " + this);
        imageView.setImage(imageArray[idx]);
}

}
