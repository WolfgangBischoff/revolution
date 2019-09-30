package Core.GuiController.Civilian;

import Core.GameWindow;
import Core.Market;
import javafx.fxml.FXML;

public class CivilianLocationController
{
    @FXML
    public void backToMainMenu(javafx.event.ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }

    @FXML
    private void shopView()
    {
        System.out.println(Market.getMarket().dataMarketCompanies());
    }
}
