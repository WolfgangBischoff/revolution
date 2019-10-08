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
    Label foodBought, clothBought, housingBought, energyBought, electronicsBought, educationBought;
    Player player;
    LocalDate today = Simulation.getSingleton().getDate();
    Map<IndustryType, Integer> consumeData;

    public CivBoughtToday()
    {
        player = Simulation.getSingleton().getPlayer();
    }

    @FXML
    private void initialize()
    {
        update();
        player.addPropertyChangeListener(this);
    }

    private void update()
    {
        consumeData = player.getConsumeDataStorage().getConsumeDataMap(today); //Add consumed goods today
        foodBought.setText("None");
        clothBought.setText("None");
        housingBought.setText("None");
        energyBought.setText("None");
        electronicsBought.setText("None");
        educationBought.setText("None");
        if(consumeData != null)
        {
            for(Map.Entry<IndustryType, Integer> entry : consumeData.entrySet())
            {
                IndustryType type = entry.getKey();
                Integer luxury = entry.getValue();
                switch (type)
                {
                    case ENERGY:energyBought.setText(luxury.toString());break;
                    case ELECTRONICS: electronicsBought.setText(luxury.toString());break;
                    case HEALTH://healthBought.setText(consumeData.get(IndustryType.HEALTH).toString());break;
                    case HOUSING:housingBought.setText(luxury.toString());break;
                    case TRAFFIC:
                    case EDUCATION:educationBought.setText(luxury.toString());break;
                    case SPARETIME://Bought.setText(consumeData.get(IndustryType.SPARETIME).toString());break;
                    case CLOTHS: clothBought.setText(luxury.toString());break;
                    case FOOD: foodBought.setText(luxury.toString()); break;
                }
            }
        }
    }

    public void removePropertyListeners()
    {
        //System.out.println("Player: " + player);
        player.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        update();
        switch (evt.getPropertyName())
        {

            /*case Player.PROPERTYNAME_DEPOSIT:
                deposit.setText(evt.getNewValue().toString());
                break;
*/
        }
    }
}
