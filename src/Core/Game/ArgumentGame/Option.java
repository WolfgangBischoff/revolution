package Core.Game.ArgumentGame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;

public class Option
{
    String symbol;
    String hasCircle;
    int rotation = 30;
    Color color = Color.RED;

    public Option(int rotation, Color color)
    {
this.rotation = rotation;
this.color = color;
    }

    public Pane load()
    {
        Rectangle rectangle = new Rectangle(10,10);
        rectangle.setFill(color);
        rectangle.setRotate(rotation);
        Pane pane = new Pane(rectangle);
        return pane;
    }
}
