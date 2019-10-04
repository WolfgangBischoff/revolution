package Core.GuiController.Civilian;

import Core.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
        player.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            /*
            case EconomyStatistics.NAME_NUMBER_COMPANIES:
                numberCompanies.setText(evt.getNewValue().toString());
                break;
            case EconomyStatistics.NAME_SUM_COMPANY_DEPOSITS:
                depositsCompanies.setText(evt.getNewValue().toString());
                break;
            case Market.NUMBER_PRODUCTS_NAME:
                productsOnMarket.setText(evt.getNewValue().toString());
                break;
            default:
                throw new RuntimeException("PropertyChange() don´t know: " + evt.getPropertyName());
                */
        }
    }
}
