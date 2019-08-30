package Core.GuiController;

import Core.Interpreter;
import Core.Simulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class mainMenuController
{
    @FXML
    private Text actiontarget;

    @FXML
    protected void gotoSocietyButton(ActionEvent event)
    {

        System.out.println("Soc Button");
    }

    @FXML protected void gotoEconomyButton(ActionEvent event) {
        System.out.println("Eco Button");
    }

}
