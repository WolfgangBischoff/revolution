package Core.GuiController;

import Core.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import static Core.Util.*;

public class societyOverviewController implements PropertyChangeListener
{
    @FXML
    Text numberPeople;

    @FXML
    private void initialize()
    {
        Society.getSociety().getSocietyStatistics().addPropertyChangeListener(this);
        numberPeople.setText("" + Society.getSociety().getSocietyStatistics().getPersons().size());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if(evt.getPropertyName() == "numberPersons")
            numberPeople.setText(evt.getNewValue().toString());
    }


    @FXML
    protected void backToMenu(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
        Society.getSociety().getSocietyStatistics().removePropertyChangeListener(this);
        System.out.println("back to Menu");
    }

    @FXML
    protected void populate(ActionEvent event)
    {
        Simulation.getSingleton().getSociety().populateSociety(NUM_PERS_DEFAULT);
    }



}
