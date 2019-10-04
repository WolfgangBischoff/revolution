package Core.GuiController.Civilian;

import Core.Company;
import Core.GameWindow;
import Core.GuiController.Controller;
import Core.GuiController.IndustryOverviewController;
import Core.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivilianController implements Controller
{


    FXMLLoader loader;
    @FXML
    BorderPane borderPane;
    @FXML
    Pane baseData, centerPane, leftPane;
    @FXML
    Button backButton;

    Player player;
    HBox centerHbox;

    IndustryOverviewController industryOverviewController;
    CivilianBaseData civilianBaseData;


    public CivilianController()
    {
        //load base fxml
        loader = new FXMLLoader();
        civilianBaseData = new CivilianBaseData();

        centerHbox = new HBox();
        centerHbox.setAlignment(Pos.CENTER);
        centerHbox.setSpacing(3);
        centerHbox.setStyle("-fx-border-style: solid inside;"); //For debugging
    }

    @FXML
    private void initialize() throws IOException
    {
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
        borderPane.setTop(civilianBaseData.load());

    }

    /*
    public Pane load()
    {
        try
        {
            //borderPane = loader.load();
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
            borderPane.setTop(civilianBaseData.load());
            return borderPane;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }*/

    public void job(javafx.event.ActionEvent event) throws IOException
    {
        System.out.println("Job Market");
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
    }

    @FXML
    private void backToMenu()
    {
        civilianBaseData.removePropertyListeners();
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }

    @FXML
    public void showMarket(ActionEvent event) throws IOException
    {
        industryOverviewController = new IndustryOverviewController(this);
        centerHbox.getChildren().clear();
        centerHbox.getChildren().add(industryOverviewController.load());
        borderPane.setCenter(centerHbox);
    }

    @Override
    public void getMessage(Object object)
    {
        if (object instanceof Company)
        {
            showCompanyDetail((Company) object);
        }

    }

    @FXML
    private void showCompanyDetail(Company company)
    {
        CivCompanyDetail com = new CivCompanyDetail(company);
        if (centerHbox.getChildren().size() > 1)
            centerHbox.getChildren().remove(1);
        centerHbox.getChildren().add(com.load());
    }



}
