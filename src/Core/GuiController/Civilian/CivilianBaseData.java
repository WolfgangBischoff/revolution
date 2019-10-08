package Core.GuiController.Civilian;

import Core.Player;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CivilianBaseData implements PropertyChangeListener
{
    @FXML
    Label name, date, deposit, effectiveHappiness, workplace;
    Player player;

    public CivilianBaseData()
    {
    }

    @FXML
    private void initialize()
    {
        player = Simulation.getSingleton().getPlayer();
        name.setText(player.getName());
        deposit.setText(player.getDeposit().toString());
        effectiveHappiness.setText(player.getEffectiveHappiness().toString());
        date.setText(Simulation.getSingleton().getCalender().dataDateWeekday());
        workplace.setText(player.dataWorksAt());

        player.addPropertyChangeListener(this);
    }

    public void removePropertyListeners()
    {
        player.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {

            case Player.PROPERTYNAME_DEPOSIT:
                deposit.setText(evt.getNewValue().toString());
                break;
            case Player.PROPERTYNAME_WORKPOSITION:
                workplace.setText(player.dataWorksAt());
                break;
        }
    }
}
