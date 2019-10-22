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
    TextField priceInputField;
    @FXML
    Button setPrice, increaseLuxury;

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

        increaseLuxury.setOnAction(event -> {
            company.setLuxury(company.getLuxury() + 1);
            System.out.println("Increase Quality");
        });
    }


}
