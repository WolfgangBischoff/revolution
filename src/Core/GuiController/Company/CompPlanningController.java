package Core.GuiController.Company;

import Core.Company;
import Core.Simulation;
import Core.Util;
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
    Company company;

    @FXML
    TextField priceInputField, luxuryInputField;
    @FXML
    Button setPrice, setLuxury;

    public CompPlanningController(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("/fxml/company/compPlanning.fxml"));
        loader.setController(this);
    }

    public CompPlanningController()
    {
        this(Simulation.getSingleton().getPlayerCompany());
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void initialize()
    {

        setPrice.setOnAction(
                event -> {
                    System.out.println("Price: " + priceInputField.getText());
                    if (Util.tryParseInt(priceInputField.getText()))
                        company.setPrice(Integer.parseInt(priceInputField.getText()));
                }
        );

        setLuxury.setOnAction(event -> {
            System.out.println("Luxury: " + luxuryInputField.getText());
            if (Util.tryParseInt(luxuryInputField.getText()))
                company.setLuxury(Integer.parseInt(luxuryInputField.getText()));
        });
    }


}
