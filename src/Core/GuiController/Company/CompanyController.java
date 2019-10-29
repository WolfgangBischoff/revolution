package Core.GuiController.Company;


import Core.Company;
import Core.GameWindow;
import Core.GuiController.Civilian.CivDeskController;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    CompanyBaseDataC companyBaseDataC;
    CivDeskController civDeskController = new CivDeskController();
    CompPlanningController compPlanningController;
    CompMarketAnalysisC compMarketAnalysisC;
    Company company;
    FXMLLoader loader;

    public CompanyController(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("/fxml/company/comp.fxml"));
        loader.setController(this);
        companyBaseDataC = new CompanyBaseDataC(company);
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
    private void backToMenu()
    {
        companyBaseDataC.removePropertyListeners(); //Just remove if leaving the whole menu
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }

    @FXML
    private void planning()
    {
        compPlanningController = new CompPlanningController(company);
        borderPane.setCenter(compPlanningController.load());
    }

    @FXML
    private void marketanalysis()
    {
        compMarketAnalysisC = new CompMarketAnalysisC(company);
        borderPane.setCenter(compMarketAnalysisC.load());
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

