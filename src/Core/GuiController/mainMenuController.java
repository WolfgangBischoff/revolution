package Core.GuiController;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class mainMenuController
{

    @FXML
    private void initialize()
    {

    }

    @FXML
    protected void gotoSocietyButton(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/societyOverview.fxml");
    }

    @FXML
    protected void gotoEconomyButton(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/economyOverview.fxml");
    }

    @FXML
    protected void gotoGovermentButton(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/govermentOverview.fxml");
    }

    @FXML
    protected void gotoMarketButton(ActionEvent event)
    {
        System.out.println(Market.getMarket().dataMarketCompanies());
    }


    @FXML
    protected void quickPop(ActionEvent event)
    {
        //Market.getMarket().clear();
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        Simulation.getSingleton().getEconomy().populateEconomy(Util.DEFAULT_NUM_COMPANIES);
        Simulation.getSingleton().getEconomy().fillWorkplaces();
        Simulation.getSingleton().getSociety().shop();
    }
}
