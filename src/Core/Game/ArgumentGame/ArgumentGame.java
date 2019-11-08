package Core.Game.ArgumentGame;

import Core.Util;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArgumentGame
{
    FXMLLoader loader;
    Pane pane;
    List<ShapeOption> argumentsoptions = new ArrayList<>();
    ShapeOption opponent;
    int score = 0;
    @FXML
    HBox hboxOptions, hboxopponent;
    @FXML
    Label reactionColor, reactionRotation, reactionShape, scoreLabel;


    public ArgumentGame()
    {
        loader = new FXMLLoader(getClass().getResource("../../../fxml/Game/Argument/argumentGame.fxml"));
        loader.setController(this);
    }

    private void init()
    {
        /*
        opponent = new ShapeOption(2,30,true, Color.YELLOW);
        argumentsoptions.add(new ShapeOption(0,1, true,Color.BLACK));
        argumentsoptions.add(new ShapeOption(0,2, false,Color.BLACK));
        argumentsoptions.add(new ShapeOption(1,3,true,  Color.BLACK));
        argumentsoptions.add(new ShapeOption(1,4,false,  Color.BLACK));
        argumentsoptions.add(new ShapeOption(2,0,true, Color.BLACK));
        argumentsoptions.add(new ShapeOption(2,1,true, Color.BLACK));
        argumentsoptions.add(new ShapeOption(2,2,true, Color.BLACK));
        argumentsoptions.add(new ShapeOption(2,3,true, Color.BLACK));
        argumentsoptions.add(new ShapeOption(3,6, true,Color.BLACK));
        argumentsoptions.add(new ShapeOption(4,7, true,Color.BLACK));
        */
        opponent = ShapeOption.createRandomShapeOption();
        for(int i=0; i<9; i++)
            argumentsoptions.add(ShapeOption.createRandomShapeOption());

        hboxopponent.getChildren().add(opponent.load());
        score = 0;
        scoreLabel.setText("Score: " + score);

        for (int i = 0; i < argumentsoptions.size(); i++)
        {
            Button button = new Button();
            button.setPrefSize(80,80);
            button.setGraphic(argumentsoptions.get(i).load());
            button.setId("" + i);
            button.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    checkChosen(button.getId());
                }
            });
            hboxOptions.getChildren().add(button);
        }
    }

    private void checkChosen(String txt)
    {
        score = 0;
        ShapeOption chosen = argumentsoptions.get(Integer.parseInt(txt));
        reactionColor.setText("Same Color: " + (chosen.shape.getFill() == opponent.shape.getFill()));
        reactionRotation.setText("Rotation: " + chosen.rotateSteps + " " + opponent.rotateSteps);
        reactionShape.setText("Shape: " + (chosen.shape.getClass() == opponent.shape.getClass()));
        reactionShape.setText("Circle: " + (chosen.hasCircle == opponent.hasCircle));
        if(chosen.shape.getFill() == opponent.shape.getFill())
            score++;
        if(chosen.shape.getClass() == opponent.shape.getClass())
            score++;
        if(chosen.shape.getClass() == opponent.shape.getClass() && chosen.rotateSteps == opponent.rotateSteps)
            score++;
        if(chosen.hasCircle == opponent.hasCircle)
            score++;
        scoreLabel.setText("Score: " + score);
    }

    public Pane load()
    {
        try
        {
            pane = loader.load();
            init();
            return pane;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
