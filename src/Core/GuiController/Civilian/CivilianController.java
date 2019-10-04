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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class CivilianController implements Controller
{

    @FXML
    FXMLLoader loader;
    @FXML
    BorderPane borderPane;
    @FXML
    Pane baseData;
    @FXML
    Text test;

    Player player;
    HBox centerHbox;

    IndustryOverviewController industryOverviewController;
    CivilianBaseData civilianBaseData;

    public CivilianController()
    {
        //load base fxml
        System.out.println("AAA");
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civilian.fxml"));
        civilianBaseData = new CivilianBaseData();

        centerHbox = new HBox();
        centerHbox.setAlignment(Pos.CENTER);
    }

    @FXML
    private void initialize()
    {
        System.out.println("Init: " + test);
    }

    public Pane load()
    {
        try
        {
            borderPane = loader.load();
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
            borderPane.setTop(civilianBaseData.load());
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
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
        GameWindow.getSingleton().createNextScene(borderPane);
    }

    @FXML
    private void backToMenu()
    {

        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
        //civilianBaseData.removePropertyListeners();
    }

    public void showMarket(ActionEvent event) throws IOException
    {
        //border becomes null, so we reload
        borderPane = loader.load();
        industryOverviewController = new IndustryOverviewController(this);
        centerHbox.getChildren().add(industryOverviewController.load());
        centerHbox.setSpacing(3);
        centerHbox.setStyle("-fx-border-style: solid inside;"); //For debugging
        borderPane.setCenter(centerHbox);
        borderPane.setTop(civilianBaseData.load());

        GameWindow.getSingleton().createNextScene(borderPane);
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
