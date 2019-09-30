package Core.GuiController;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class mainMenuController
{
    @FXML
    private ImageView leftBar, rightBar;
    @FXML
    private Text date;

    @FXML
    private void initialize()
    {
        leftBar.setImage(new Image("/img/säulePlakat.png"));
        rightBar.setImage(new Image("/img/säule.png"));
        date.setText(Simulation.getSingleton().getCalender().dataDateWeekday());
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
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        //Simulation.getSingleton().getEconomy().populateEconomy(Util.DEFAULT_NUM_COMPANIES);
        //Simulation.getSingleton().getEconomy().populateEconomy("oneCheapCompany");
        Simulation.getSingleton().getEconomy().populateEconomy("comp");
        Simulation.getSingleton().getEconomy().fillWorkplaces();
        Market.getMarket().calcState();
        //Simulation.getSingleton().getSociety().shop();
    }
}
