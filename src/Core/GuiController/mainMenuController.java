package Core.GuiController;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        System.out.println(Market.getMarket().productData());
    }


    @FXML
    protected void quickPop(ActionEvent event)
    {
        Market.getMarket().clear();
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        Simulation.getSingleton().getEconomy().populateEconomy(Util.DEFAULT_NUM_COMPANIES);
        Simulation.getSingleton().getEconomy().fillWorkplaces();
        Simulation.getSingleton().getEconomy().comaniesProduce();
    }
}
