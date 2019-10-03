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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CivilianController implements Controller
{

    @FXML
    FXMLLoader loader;
    @FXML
    BorderPane borderPane;
    @FXML
    HBox centerHbox;

    IndustryOverviewController industryOverviewController;
    Pane companyDetailView = new Pane();

    @FXML
    private void initialize()
    {
    }

    public CivilianController()
    {
        loader = new FXMLLoader(getClass().getResource("/fxml/civilian/civilian.fxml"));
        loader.setController(this);

        centerHbox = new HBox();
        centerHbox.setAlignment(Pos.CENTER);
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

    public void showMarket(ActionEvent event) throws IOException
    {
        //border becomes null, so we reload
        borderPane = loader.load();
        industryOverviewController = new IndustryOverviewController(this);
        centerHbox.getChildren().add(industryOverviewController.load());
        //centerHbox.getChildren().add(companyDetailView);
        centerHbox.setStyle("-fx-border-style: solid inside;");
        borderPane.setCenter(centerHbox);//borderPane.setCenter(industryOverviewController.load());
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
        CompanyDetailController com = new CompanyDetailController(company);
        //companyDetailView = com.load();
        if(centerHbox.getChildren().size() > 1)
            centerHbox.getChildren().remove(1);
        centerHbox.getChildren().add(com.load());
    }

}
