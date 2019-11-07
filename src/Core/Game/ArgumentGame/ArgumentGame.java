package Core.Game.ArgumentGame;

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

public class ArgumentGame
{
    FXMLLoader loader;
    Pane pane;
    List<Option> argumentsoptions = new ArrayList<>();
    Option opponent;
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
        opponent = new Option(0,30, Color.YELLOW);
        hboxopponent.getChildren().add(opponent.load());
        argumentsoptions.add(new Option(0,30, Color.RED));
        argumentsoptions.add(new Option(1,30, Color.BLACK));
        argumentsoptions.add(new Option(1,45, Color.ALICEBLUE));
        argumentsoptions.add(new Option(0,0, Color.YELLOW));
        score = 0;
        scoreLabel.setText("Score: " + score);

        for (int i = 0; i < argumentsoptions.size(); i++)
        {
            Button button = new Button();
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
        Option chosen = argumentsoptions.get(Integer.parseInt(txt));
        reactionColor.setText("Same Color: " + (chosen.color == opponent.color));
        reactionRotation.setText("Rotation: " + chosen.rotation + " " + opponent.rotation);
        reactionShape.setText("Shape: " + (chosen.shape.getClass() == opponent.shape.getClass()));
        if(chosen.color == opponent.color)
            score++;
        if(chosen.rotation == opponent.rotation)
            score++;
        if(chosen.shape.getClass() == opponent.shape.getClass())
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
