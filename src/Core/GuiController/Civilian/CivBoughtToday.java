package Core.GuiController.Civilian;

import Core.Enums.IndustryType;
import Core.Player;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Map;

public class CivBoughtToday implements PropertyChangeListener
{
    @FXML
    Label foodBought, clothsBought, energy, education;
    Player player;
    LocalDate today = Simulation.getSingleton().getDate();
    Map<IndustryType, Integer> consumeData;

    @FXML
    private void initialize()
    {
        player = Simulation.getSingleton().getPlayer();
        consumeData = player.getConsumeDataStorage().getConsumeDataMap(today); //Add consumed goods today
        System.out.println("MAP " + consumeData);
        if(consumeData != null)
        {
            if(consumeData.containsKey(IndustryType.FOOD))
                foodBought.setText(consumeData.get(IndustryType.FOOD).toString());
            else
                foodBought.setText("None");
        }
     //   clothsBought.setText(consumeData.get(IndustryType.CLOTHS).toString());
        //       player.addPropertyChangeListener(this);
    }

    public void removePropertyListeners()
    {
        //       player.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {

            /*case Player.PROPERTYNAME_DEPOSIT:
                deposit.setText(evt.getNewValue().toString());
                break;
*/
        }
    }
}
