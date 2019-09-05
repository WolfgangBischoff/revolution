package Core.GuiController;

import Core.GameWindow;
import Core.Interpreter;
import Core.Simulation;
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
        //testimage.setCache(true);
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

}
