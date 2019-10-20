package Core.GuiController.Company;

import Core.Company;
import Core.Player;
import Core.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CompPlanningController
{
    FXMLLoader loader;

    @FXML
    TextField priceInputField;
    @FXML
    Button setPrice, increaseLuxury;

    public CompPlanningController()
    {

        loader = new FXMLLoader(getClass().getResource("/fxml/company/compPlanning.fxml"));
        loader.setController(this);
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void initialize()
    {

        setPrice.setOnAction(
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        System.out.println("Price: " + priceInputField.getText());
                    }
                }
        );

        increaseLuxury.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Increase Quality");
            }
        });
    }



}
