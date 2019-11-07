package Core.Game.ArgumentGame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.TriangleMesh;

public class Option
{
    //Line, Rectangle, Circle, Ellipse, Polygon, Polyline, Cubic Curve, Quad Curve, Arc
    Shape shape;
    String hasCircle;
    int rotation = 30;
    Color color = Color.RED;

    public Option(int shapeclass, int rotation, Color color)
    {
        this.rotation = rotation;
        this.color = color;
        if(shapeclass == 0)
            shape = new Rectangle(30,10);
        else
            shape = new Ellipse(20,5);

        shape.setFill(color);
        shape.setRotate(rotation);

    }

    public Pane load()
    {
        Pane pane = new Pane(shape);
        return pane;
    }
}
