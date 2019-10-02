package Core.GuiController;

import Core.GameWindow;
import Core.Market;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivilianController
{

    @FXML
    FXMLLoader loader;
    @FXML
    BorderPane borderPane;
    //@FXML
    //Pane centerPane;//needed?

    @FXML
    private void initialize()
    {
        //centerPane = new Pane();
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
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civilianCenterEmpty.fxml")));
            return borderPane;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @FXML
    private void shopView()
    {
        System.out.println(Market.getMarket().dataMarketCompanies());
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
        IndustryOverviewController industryOverviewController = new IndustryOverviewController();
        borderPane.setCenter(industryOverviewController.load());
        GameWindow.getSingleton().createNextScene(borderPane);
    }

}
