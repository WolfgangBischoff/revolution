package Core.GuiController;

import Core.Enums.EducationalLayer;
import Core.*;
import Core.Enums.IndustryType;
import Core.GuiController.Civilian.CivilianController;
import Core.GuiController.Company.CompanyController;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static Core.Util.*;


public class mainMenuController
{
    @FXML
    private ImageView leftBar, rightBar;

    @FXML
    private void initialize()
    {
        leftBar.setImage(new Image("/img/säulePlakat.png"));
        rightBar.setImage(new Image("/img/säuleCommEco.png"));
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
    private void startAsCompany()
    {
        PlayerCompany playerCompany;

        if (Simulation.getSingleton().getPlayerCompany() == null)
        {
            playerCompany = new PlayerCompany("Wolfgangs Leckereiern", IndustryType.FOOD, 420000, 2000, -1);
        }
        else
            playerCompany = Simulation.getSingleton().getPlayerCompany();
        if(!Economy.getEconomy().getCompanies().contains(playerCompany))
            Economy.getEconomy().getCompanies().add(playerCompany);
        Simulation.getSingleton().setPlayerCompany(playerCompany);
        Economy.getEconomy().calcState();

        CompanyController companyController = new CompanyController(playerCompany);
        GameWindow.getSingleton().createNextScene(companyController.load());
        //GameWindow.getSingleton().createNextScene("../fxml/company/comp.fxml");
    }

    @FXML
    protected void quickPop(ActionEvent event)
    {
        //Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        Society.getSociety().populateSociety(NUM_BASIC_EDU,NUM_APP_EDU,NUM_HIGHER_EDU,NUM_UNIVERSITY_EDU);
        //Simulation.getSingleton().getEconomy().populateEconomy(Util.DEFAULT_NUM_COMPANIES);
        //Simulation.getSingleton().getEconomy().populateEconomy("oneCheapCompany");
        Simulation.getSingleton().getEconomy().populateEconomy("comp");
        //Simulation.getSingleton().getEconomy().populateEconomy("twoCompetitors");
        Simulation.getSingleton().getEconomy().fillWorkplaces();
        Market.getMarket().calcState();
        Simulation.getSingleton().nextPeriod();
    }

}
