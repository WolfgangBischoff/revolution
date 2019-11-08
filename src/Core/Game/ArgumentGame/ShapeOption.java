package Core.Game.ArgumentGame;

import Core.Util;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class ShapeOption
{
    Shape shape;
    Circle circle;
    boolean hasCircle;
    int rotateSteps;
    int ROTATESTEP = 45;
    static final int MAXROT_RECT = 1, MAXROT_CIRCLE = 0, MAXROTATION_ELIPSE = 3, MAXROTATION_MAX = 7;

    private boolean checkRotationIsVisible(int shape, int rotation)
    {
        if (rotation < 0)
            return false;
        switch (shape)
        {
            case 0:
                return rotation <= MAXROT_RECT;
            case 1:
                return rotation == MAXROT_CIRCLE;
            case 2:
            case 3:
                return rotation <= MAXROTATION_MAX;
            case 4:
                return rotation <= MAXROTATION_ELIPSE;
            default:
                return false;
        }
    }

    public static ShapeOption createRandomShapeOption()
    {
        int shapetype = Util.getRandom().nextInt(5);
        int rotation = Util.getRandom().nextInt(MAXROTATION_MAX);
        boolean circle = Util.getRandom().nextBoolean();
        Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN};
        Color color = colors[Util.getRandom().nextInt(colors.length)];
        ShapeOption sh = new ShapeOption(shapetype, rotation, circle, color);
        return sh;
    }

    public ShapeOption(int shapeclass, int rotationStepsParam, boolean hasCircle, Color color)
    {
        this.hasCircle = hasCircle;
        this.rotateSteps = 0;
        if (checkRotationIsVisible(shapeclass, rotationStepsParam))
            rotateSteps = rotationStepsParam;

        switch (shapeclass)
        {
            case 0:
                shape = new Rectangle(-20, -20, 40, 40);//Rectangle(double x, double y, double width, double height)
                ((Rectangle) shape).setArcHeight(10);
                ((Rectangle) shape).setArcWidth(10);
                shape.setRotate(rotateSteps * ROTATESTEP);
                break;
            case 1:
                shape = new Circle(20);
                break;
            case 2:
                shape = new Arc(0, 0, 20, 20, 45 * rotateSteps, 270);//Arc(double centerX, double centerY, double radiusX, double radiusY, double startAngle, double length)
                break;
            case 3:
                shape = new Polygon(0, -20, -10, 20, 10, 20);
                shape.setRotate(rotateSteps * ROTATESTEP);
                break;
            case 4:
                shape = new Ellipse(20, 10);
                shape.setRotate(rotateSteps * ROTATESTEP);
                break;
            default:
                shape = new Circle(10);
                break;
        }

        circle = new Circle(25);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.TRANSPARENT);
        if (hasCircle)
        {
            circle.setStroke(color);
        }
        shape.setFill(color);
    }

    public Node load()
    {
        Group node = new Group(shape);
        node.getChildren().add(circle);
        return node;
    }
}
