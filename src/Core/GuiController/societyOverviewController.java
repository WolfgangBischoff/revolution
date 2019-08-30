package Core.GuiController;

import Core.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


import java.util.Observable;
import java.util.Observer;

import static Core.Util.*;

public class societyOverviewController implements Observer
{
    @FXML
    Text numberPeople;

    @FXML
    private void initialize()
    {
        Society.getSociety().getSocietyStatistics().addObserver(this);
        numberPeople.setText("" + Society.getSociety().getSocietyStatistics().getPersons().size());
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof SocietyStatistics)
        {
            numberPeople.setText("" + ((SocietyStatistics) o).getPersons().size());
        }
        else
            System.out.println("Not");
    }


    @FXML
    protected void backToMenu(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
        System.out.println("back to Menu");
    }

    @FXML
    protected void populate(ActionEvent event)
    {
        Simulation.getSingleton().getSociety().populateSociety(NUM_PERS_DEFAULT);
    }


}
