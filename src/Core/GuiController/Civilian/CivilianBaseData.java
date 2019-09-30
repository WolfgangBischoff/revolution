package Core.GuiController.Civilian;

import Core.Person;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CivilianBaseData
{
    @FXML
    Text name, date, deposit, effectiveHappiness;

    Person player;

    @FXML
    private void initialize()
    {
        player = Simulation.getSingleton().getPlayer();
        name.setText(player.getName());
        deposit.setText(player.getDeposit().toString());
        effectiveHappiness.setText(player.getEffectiveHappiness().toString());
        date.setText(Simulation.getSingleton().getCalender().dataDateWeekday());
        System.out.println(Simulation.getSingleton().getPlayer());
    }
}
