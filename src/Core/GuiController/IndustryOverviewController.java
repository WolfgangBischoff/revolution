package Core.GuiController;

import Core.Enums.IndustryType;
import Core.Enums.ViewPerspective;
import Core.GameWindow;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class IndustryOverviewController extends AnimationTimer
{
    FXMLLoader loader;
    Controller parentController;
    ViewPerspective perspective;

    @FXML
    private Tab foodTab, clothsTabPage, housingTabPage, educationTabPage, energyTabPage, electronicsTabPage, sparetimeTabPage, healthTabPage, trafficTabPage;

    public IndustryOverviewController()
    {
        loader = new FXMLLoader(getClass().getResource("../../fxml/industryOverview.fxml"));
        loader.setController(this);
        perspective = ViewPerspective.GENERAL;
    }

    public IndustryOverviewController(Controller parentController, ViewPerspective perspective)
    {
        this();
        this.parentController = parentController;
        this.perspective = perspective;
    }

    @Override
    public void handle(long l)
    {
        System.out.println(this + " INDUST");
    }



    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller
        //Init Tabs
        foodTab.setGraphic(new ImageView(new Image("/img/food.png", 64, 64, false, false)));
        clothsTabPage.setGraphic(new ImageView(new Image("/img/textileShirt.png", 64, 64, false, false)));
        healthTabPage.setGraphic(new ImageView(new Image("/img/medicine.png", 64, 64, false, false)));
        electronicsTabPage.setGraphic(new ImageView(new Image("/img/mobilephone.png", 64, 64, false, false)));

        foodTab.setContent(new IndustryOverviewTabPageController(IndustryType.FOOD, parentController, perspective).load());
        clothsTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.CLOTHS, parentController, perspective).load());
        housingTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.HOUSING, parentController, perspective).load());
        energyTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.ENERGY, parentController, perspective).load());
        electronicsTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.ELECTRONICS, parentController, perspective).load());
        educationTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.EDUCATION, parentController, perspective).load());
        sparetimeTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.SPARETIME, parentController, perspective).load());
        healthTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.HEALTH, parentController, perspective).load());
        trafficTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.TRAFFIC, parentController, perspective).load());
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
