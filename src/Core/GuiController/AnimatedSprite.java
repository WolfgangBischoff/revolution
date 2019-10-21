package Core.GuiController;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends AnimationTimer
{
    //https://stackoverflow.com/questions/10708642/javafx-2-0-an-approach-to-game-sprite-animation
    private final ImageView imageView; //Image view that will display our sprite
    private final float fps; //frames per second I.E. 24
    Image[] imageArray = new Image[3];

    /*
    private final int totalFrames; //Total number of frames in the sequence

    private final int cols; //Number of columns on the sprite sheet
    private final int rows; //Number of rows on the sprite sheet
    private final int frameWidth; //Width of an individual frame
    private final int frameHeight; //Height of an individual frame
    private int currentCol = 0;
    private int currentRow = 0;*/
    private long lastFrame = 0;

    public AnimatedSprite(ImageView imageView, float fps)
    {
        this.imageView = imageView;
        this.fps = fps;
        imageArray[0] = new Image("img/diffuserSmoke.png");
        imageArray[1] = new Image("img/diffuserSmoke0.png");
        imageArray[2] = new Image("img/diffuserSmoke1.png");
    }


    long lastTime = System.nanoTime();
    int idx = 0;

    public void handle(long now)
    {
        int frameJump = (int) Math.floor((now - lastFrame) / (1000000000 / fps)); //Determine how many frames we need to advance to maintain frame rate independence
//Do a bunch of math to determine where the viewport needs to be positioned on the sprite sheet
        if (frameJump >= 1)
        {
            lastFrame = now;
            System.out.println("CHANGE");
           /*
            int addRows = (int) Math.floor((float) frameJump / (float) cols);

            int frameAdd = frameJump - (addRows * cols);

            if (currentCol + frameAdd >= cols)
            {
                currentRow += addRows + 1;
                currentCol = frameAdd - (cols - currentCol);
            }
            else
            {
                currentRow += addRows;
                currentCol += frameAdd;
            }
            currentRow = (currentRow >= rows) ? currentRow - ((int) Math.floor((float) currentRow / rows) * rows) : currentRow;

            //The last row may or may not contain the full number of columns
            if ((currentRow * cols) + currentCol >= totalFrames)
            {
                currentRow = 0;
                currentCol = Math.abs(currentCol - (totalFrames - (int) (Math.floor((float) totalFrames / cols) * cols)));
            }

            imageView.setViewport(new Rectangle2D(currentCol * frameWidth, currentRow * frameHeight, frameWidth, frameHeight));
            */
        }



        double time = (now - lastTime);
        if (time > 400000000)
        {
            idx++;
            idx = idx % imageArray.length;
            lastTime = now;
        }
        //System.out.println("Animated Sprite: " + this);
        imageView.setImage(imageArray[idx]);
    }

}
