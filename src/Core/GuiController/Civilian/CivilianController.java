package Core.GuiController.Civilian;

import Core.Company;
import Core.GameWindow;
import Core.GuiController.Controller;
import Core.GuiController.IndustryOverviewController;
import Core.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class CivilianController implements Controller
{


    FXMLLoader loader;
    @FXML
    BorderPane borderPane;
    @FXML
    Pane baseData, centerPane;

    //Player player;
    HBox centerHbox;

    IndustryOverviewController industryOverviewController;
    CivilianBaseData civilianBaseData;
    CivBoughtToday civBoughtToday;


    public CivilianController() throws IOException
    {
        //load baseData fxml
        civilianBaseData = new CivilianBaseData();
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civilianBaseData.fxml"));
        loader.setController(civilianBaseData);

        centerHbox = new HBox();
        //centerHbox.setAlignment(Pos.CENTER);
        centerHbox.setSpacing(3);
        centerHbox.setStyle("-fx-border-style: solid inside;"); //For debugging
    }

    @FXML
    private void initialize() throws IOException
    {
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/civilian/civDesk.fxml")));
        borderPane.setTop(loader.load());
    }

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
        centerHbox.setPadding(new Insets(0,0,0,50)); //public Insets(double top, double right, double bottom,double left)
        centerHbox.getChildren().add(industryOverviewController.load());

        //load today consume
        civBoughtToday = new CivBoughtToday();
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civBoughtToday.fxml"));
        loader.setController(civBoughtToday);
        borderPane.setRight(loader.load());

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
