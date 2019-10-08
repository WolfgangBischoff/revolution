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

public class CivCompanyDetail
{
    Company company;
    FXMLLoader loader;
    @FXML
    Label companyName, companyPrice, companyLuxury, companyDescription;
    @FXML
    Button buy;

    public CivCompanyDetail(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("../../../fxml/civilian/civCompanyDetail.fxml"));
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
        companyPrice.setText(company.getPrice().toString());
        companyLuxury.setText(company.getLuxury().toString());
        companyDescription.setText("This is a great company");

        buy.setOnAction(
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Player player = Simulation.getSingleton().getPlayer();
                        System.out.println("Is Saturated: " + player.playerIsSaturated(company.getIndustry()));
                        if (player.isAffordable(company) && player.playerIsAvailable(company) && !player.playerIsSaturated(company.getIndustry()))
                            player.playerBuyUnchecked(company);

                    }
                }
        );
    }

}

