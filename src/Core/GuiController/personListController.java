package Core.GuiController;

import Core.GameWindow;
import Core.Person;
import Core.Simulation;
import Core.Society;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


public class personListController implements PropertyChangeListener
{
    @FXML private ListView listView;
    private List<String> stringList = new ArrayList<>(5);
    private ObservableList observableList = FXCollections.observableArrayList();
    private List<Person> personList;
    private ObservableList observableListPerson = FXCollections.observableArrayList();


    public void setListView(){
/*
        stringList.add("String 1");
        stringList.add("String 2");
        stringList.add("String 3");
        stringList.add("String 4");

        observableList.setAll(stringList);
        listView.setItems(observableList);
        listView.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> listView) {
                        return new ListViewCell();
                    }
                });*/

        observableListPerson.setAll(personList);
        listView.setItems(observableListPerson);

        listView.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>()
        {
            @Override
            public ListCell<Person> call(ListView<Person> param)
            {
                return new ListCellPerson();
            }
        });
    }

    @FXML
    private void initialize()
    {
        Society.getSociety().getSocietyStatistics().addPropertyChangeListener(this);
        personList = Society.getSociety().getPeople();

        setListView();
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
