package Core.GuiController.Civilian;

import Core.GameWindow;
import Core.Person;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CivilianBaseData
{
    @FXML
    Label name, date, deposit, effectiveHappiness, workplace;

    Person player;

    @FXML
    private void initialize()
    {
        player = Simulation.getSingleton().getPlayer();
        name.setText(player.getName());
        deposit.setText(player.getDeposit().toString());
        effectiveHappiness.setText(player.getEffectiveHappiness().toString());
        date.setText(Simulation.getSingleton().getCalender().dataDateWeekday());
        workplace.setText(player.dataWorksAt());
    }
}
