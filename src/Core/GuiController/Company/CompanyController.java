package Core.GuiController.Company;


import Core.GameWindow;
import Core.GuiController.Civilian.CivDeskController;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CompanyController
{

    @FXML
    BorderPane borderPane;
    @FXML
    Pane baseData, centerPane;

    HBox centerHbox;
    CompanyBaseDataC companyBaseDataC = new CompanyBaseDataC();
    CivDeskController civDeskController = new CivDeskController();
    CompPlanningController compPlanningController;
    CompMarketAnalysisC compMarketAnalysisC;

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
        companyBaseDataC.removePropertyListeners(); //Just remove if leaving the whole menu
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
        compMarketAnalysisC = new CompMarketAnalysisC();
        borderPane.setCenter(compMarketAnalysisC.load());
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

