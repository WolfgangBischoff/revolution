package Core.GuiController.Civilian;

import Core.Company;
import Core.GameWindow;
import Core.GuiController.CompanyDetailController;
import Core.GuiController.Controller;
import Core.GuiController.IndustryOverviewController;
import Core.Market;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivilianController implements Controller
{

    @FXML
    FXMLLoader loader;
    @FXML
    BorderPane borderPane;

    IndustryOverviewController industryOverviewController;

    @FXML
    private void initialize()
    {
    }

    public CivilianController()
    {
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civilian.fxml"));
        loader.setController(this);
    }

    public Pane load()
    {
        try
        {
            borderPane = loader.load();
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
            return borderPane;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void job(javafx.event.ActionEvent event) throws IOException
    {
        System.out.println("Job Market");
        borderPane = loader.load();
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civTest.fxml")));
        GameWindow.getSingleton().createNextScene(borderPane);
    }

    @FXML
    private void backToMenu()
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }

    public void consume(ActionEvent event) throws IOException
    {
        System.out.println("Consume");

        //border becomes null, so we reload
        borderPane = loader.load();
        industryOverviewController = new IndustryOverviewController(this);
        borderPane.setCenter(industryOverviewController.load());
        GameWindow.getSingleton().createNextScene(borderPane);
    }

    @Override
    public void getMessage(Object object)
    {
        if(object instanceof Company)
        {
            showCompanyDetail((Company)object);
        }

    }

    @FXML
    private void showCompanyDetail(Company company)
    {
        System.out.println(company.baseData());
    }

}
