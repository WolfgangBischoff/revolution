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
    @FXML
    HBox hboxOptions, opponent;
    @FXML
    Label reaction;


    public ArgumentGame()
    {
        loader = new FXMLLoader(getClass().getResource("../../../fxml/Game/Argument/argumentGame.fxml"));
        loader.setController(this);


    }

    private void init()
    {
        reaction.setText("TODO");
        opponent.getChildren().add(new Option(30, Color.YELLOW).load());
        argumentsoptions.add(new Option(30, Color.RED));
        argumentsoptions.add(new Option(45, Color.BLACK));
        argumentsoptions.add(new Option(45, Color.ALICEBLUE));
        argumentsoptions.add(new Option(0, Color.YELLOW));

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

    private int checkChosen(String txt)
    {
        reaction.setText(argumentsoptions.get(Integer.parseInt(txt)).color.toString());
        /*
        if (txt.equals(opponent.getText()))
            reaction.setText("Positive");
        else
            reaction.setText("Negative");
        */return 0;
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
