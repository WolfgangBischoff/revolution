package Core.GuiController;

import Core.GameWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class societyOverviewController
{
    @FXML
    protected void backToMenu(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
        System.out.println("back to Menu");
    }

}
