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

    @FXML
    Button backButton;
    @FXML
    Tab foodTab, clothsTabPage, housingTabPage, educationTabPage;

    public IndustryOverviewController()
    {
        loader = new FXMLLoader(getClass().getResource("../../fxml/industryOverview.fxml"));
        loader.setController(this);
    }

    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller
        //Init Tabs
        foodTab.setGraphic(new ImageView(new Image("/img/food.png", 20, 20, false, false)));

        foodTab.setContent(new IndustryOverviewTabPageController(IndustryType.FOOD).load());
        clothsTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.CLOTHS).load());
        housingTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.HOUSING).load());
        educationTabPage.setContent(new IndustryOverviewTabPageController(IndustryType.EDUCATION).load());

        backButton.setOnAction((event) -> {
            GameWindow.getSingleton().createNextScene("../fxml/economyOverview.fxml");
        });

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
