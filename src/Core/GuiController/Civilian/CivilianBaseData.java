package Core.GuiController.Civilian;

import Core.Player;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CivilianBaseData implements PropertyChangeListener
{
    @FXML
    Label name, date, deposit, effectiveHappiness, workplace;
    FXMLLoader loader;
    Player player;

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
        System.out.println("PLAYER INIT" + player);
    }

    public Pane load()
    {
        loader = new FXMLLoader();
        try
        {
            return FXMLLoader.load(getClass().getResource("/fxml/civilian/civilianBaseData.fxml"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void removePropertyListeners()
    {
        System.out.println("PLAYER REMOVE" + player);
        player.removePropertyChangeListener(this);
        //Simulation.getSingleton().getPlayer().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {

            case Player.PROPERTYNAME_DEPOSIT:
                deposit.setText(evt.getNewValue().toString());
                break;

        }
    }
}
