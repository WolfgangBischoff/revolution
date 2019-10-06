package Core.GuiController;

import Core.Enums.IndustryType;
import Core.GameWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class IndustryOverviewController
{
    FXMLLoader loader;
    Controller parentController;

    @FXML
    Tab foodTab, clothsTabPage, housingTabPage, educationTabPage, energyTabPage, electronicsTabPage;

    public IndustryOverviewController()
    {
        loader = new FXMLLoader(getClass().getResource("../../fxml/industryOverview.fxml"));
        loader.setController(this);
    }

    public IndustryOverviewController(Controller parentController)
    {
        this();
        this.parentController = parentController;
    }

    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller
        //Init Tabs
        foodTab.setGraphic(new ImageView(new Image("/img/food.png", 20, 20, false, false)));

        foodTab.setContent(new IndustryOverviewTabPageController(IndustryType.FOOD, parentController).load());
        clothsTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.CLOTHS, parentController).load());
        housingTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.HOUSING, parentController).load());
        energyTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.ENERGY, parentController).load());
        electronicsTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.ELECTRONICS, parentController).load());
        educationTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.EDUCATION, parentController).load());
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
