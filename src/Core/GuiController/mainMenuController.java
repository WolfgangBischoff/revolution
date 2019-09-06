package Core.GuiController;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class mainMenuController
{
    @FXML
    private ImageView testimage;


    @FXML
    private void initialize()
    {
        Image image = new Image("./img/enviro.jpg");
        testimage = new ImageView();
        testimage.setImage(image);
    }

    @FXML
    protected void gotoSocietyButton(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/societyOverview.fxml");
    }

    @FXML protected void gotoEconomyButton(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/economyOverview.fxml");
    }

    @FXML protected void quickPop(ActionEvent event)
    {
        Society.getSociety().populateSociety(Util.NUM_PERS_DEFAULT);
        Simulation.getSingleton().getEconomy().populateEconomy(Util.DEFAULT_NUM_COMPANIES);
        Simulation.getSingleton().getEconomy().fillWorkplaces();
    }
}
