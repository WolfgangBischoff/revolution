package Core.GuiController.Civilian;

import Core.Company;
import Core.Player;
import Core.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivCompanyDetailWorkSeeking
{
    Company company;
    FXMLLoader loader;
    @FXML
    Label companyName, companyDescription, wp;
    @FXML
    Button buy;

    public CivCompanyDetailWorkSeeking(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("../../../fxml/civilian/civCompanyDetailWorkSeeking.fxml"));
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
        companyName.setText(company.getName());
        wp.setText("LIST");
        companyDescription.setText("This is a great employer");

        buy.setOnAction(
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Player player = Simulation.getSingleton().getPlayer();
                        System.out.println("Application send");

                    }
                }
        );
    }

}

