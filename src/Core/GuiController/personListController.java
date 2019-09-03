package Core.GuiController;

import Core.GameWindow;
import Core.Person;
import Core.Society;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class personListController implements PropertyChangeListener
{
    @FXML private ListView listView;
    private List<Person> personList;
    private ObservableList observableListPerson = FXCollections.observableArrayList();


    public void setListView(){

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
