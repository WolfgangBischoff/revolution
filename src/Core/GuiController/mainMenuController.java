package Core.GuiController;

import Core.Enums.EducationalLayer;
import Core.*;
import Core.GuiController.Civilian.CivilianController;
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
        rightBar.setImage(new Image("/img/säuleCommEco.png"));
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
    private void startAsCiv(ActionEvent event)
    {
        Player pla;
        if (Simulation.getSingleton().getPlayer() == null)
        {
            pla = new Player(new PersonName("Alexander Horst Otto", "Husarl"), 28, EducationalLayer.EDU_HIGHER, 2222);
        }
        else
            pla = Simulation.getSingleton().getPlayer();
        if(!Society.getSociety().getPeople().contains(pla))
            Society.getSociety().addPerson(pla);
        Simulation.getSingleton().setPlayer(pla);

        GameWindow.getSingleton().createNextScene("../fxml/civilian/civilian.fxml");
    }

    @FXML
    protected void quickPop(ActionEvent event)
    {
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        //Simulation.getSingleton().getEconomy().populateEconomy(Util.DEFAULT_NUM_COMPANIES);
        Simulation.getSingleton().getEconomy().populateEconomy("oneCheapCompany");
        //Simulation.getSingleton().getEconomy().populateEconomy("comp");
        Simulation.getSingleton().getEconomy().fillWorkplaces();
        Market.getMarket().calcState();
        Simulation.getSingleton().getSociety().shop();

    }
}
