package Core.GuiController;

import Core.Enums.PoliticalOpinion;
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
    ImageView rulingPartyIcon, sprite;

    AnimatedSprite animatedSprite;

    @FXML
    private void initialize()
    {
        //Variable Init
        partyAtPower.setText(Government.getGoverment().getRulingParty().toString());
        deposit.setText(Government.getGoverment().getDeposit().toString());
        setImageBasedOnPartyOnPower();

        animatedSprite = new AnimatedSprite(sprite,0.5f);
        animatedSprite.start();
    }

    private void setImageBasedOnPartyOnPower()
    {
        PoliticalOpinion ruling = Government.getGoverment().getRulingParty();

        switch (ruling)
        {
            case SocialDemocratic:
                rulingPartyIcon.setImage(new Image("/img/communist.gif"));
                break;
            case Enviromental:
                rulingPartyIcon.setImage(new Image("/img/enviro.jpg"));
                break;
            case Conservativ:
                rulingPartyIcon.setImage(new Image("/img/capital.png"));
                break;
            default:
                rulingPartyIcon.setImage(new Image("/img/hammeSickle.jpg"));
                break;
        }


    }

    @FXML
    private void backtoMain()
    {
        //unlisten
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
        animatedSprite.stop();
    }
}
