package Core.GuiController;

import Core.GameWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class EcoIndustryOverview
{
    @FXML
    Button backButton;
    @FXML
    BorderPane borderPane;

    FXMLLoader loader;

    @FXML
    private void initialize()
    {
        backButton.setOnAction((event) -> {
            GameWindow.getSingleton().createNextScene("../fxml/economyOverview.fxml");
        });

    }

    public EcoIndustryOverview()
    {
        //InitBase FXML
        loader = new FXMLLoader(getClass().getResource("../../fxml/ecoIndustryOverview.fxml"));
        loader.setController(this);
    }

    public Pane load()
    {
        try
        {
            //Add centerpane before returned
            borderPane = loader.load();
            IndustryOverviewController storageOverviewController = new IndustryOverviewController();
            borderPane.setCenter(storageOverviewController.load());
            return borderPane;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
