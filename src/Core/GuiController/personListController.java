package Core.GuiController;

import Core.GameWindow;
import Core.Simulation;
import Core.Society;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class personListController implements PropertyChangeListener
{
    @FXML
    Text numberPeople, depositsPeople;

    @FXML
    private void initialize()
    {
        Society.getSociety().getSocietyStatistics().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {

    }

    @FXML
    protected void backToOverview(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/societyOverview.fxml");
        Society.getSociety().getSocietyStatistics().removePropertyChangeListener(this);
    }

}
