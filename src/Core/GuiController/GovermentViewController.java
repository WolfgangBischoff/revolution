package Core.GuiController;

import Core.GameWindow;
import Core.Government;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GovermentViewController
{
    @FXML
    Text partyAtPower, deposit;
    @FXML
    Image rulingPartyIcon;

    @FXML
    private void initialize()
    {
        //Listener Init
        //Variable Init
        partyAtPower.setText(Government.getGoverment().getRulingParty().toString());
        deposit.setText(Government.getGoverment().getDeposit().toString());

        //rulingPartyIcon = new Image("./img/enviro.jpg");
    }

    @FXML
    private void backtoMain()
    {
        //unlisten
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
    }
}
