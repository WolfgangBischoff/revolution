package Core.GuiController.Company;


import Core.GameWindow;
import Core.GuiController.Civilian.CivDeskController;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class CompanyController
{

    @FXML
    BorderPane borderPane;
    @FXML
    Pane baseData, centerPane;

    HBox centerHbox;
    CompPlanningController compPlanningController;
    CivDeskController civDeskController = new CivDeskController();
    CompanyBaseDataC companyBaseDataC = new CompanyBaseDataC();


    public CompanyController()
    {
        centerHbox = new HBox();
        centerHbox.setSpacing(3);
        centerHbox.setStyle("-fx-border-style: solid inside;"); //For debugging
    }

    @FXML
    private void initialize()
    {
        borderPane.setCenter(civDeskController.load());
        borderPane.setTop(companyBaseDataC.load());
    }

   /* public Pane load()
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
    }*/

    @FXML
    private void backToMenu()
    {
        //civilianBaseData.removePropertyListeners(); //Just remove here, is valid in all other menues
        companyBaseDataC.removePropertyListeners();
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }

    @FXML
    private void planning()
    {
        System.out.println("Planning");
        compPlanningController = new CompPlanningController();
        borderPane.setCenter(compPlanningController.load());
    }

    @FXML
    private void marketanalysis()
    {
    System.out.println("Market Analysis");
    System.out.println(Simulation.getSingleton().getPlayerCompany().getPriceToExpectedRevenue().toString());
    }



    @FXML
    private void nextDay()
    {
        Simulation.getSingleton().nextPeriod();
    }

    private void removeListeners()
    {
        //civBoughtToday.removePropertyListeners();
    }

}

