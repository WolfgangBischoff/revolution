package Core.GuiController;

import Core.GameWindow;
import Core.Person;
import Core.Society;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class personListController implements PropertyChangeListener {
    @FXML
    private ListView listView;
    private List<Person> personList;
    private ObservableList observableListPerson = FXCollections.observableArrayList();


    public void setListView() {

        observableListPerson.setAll(personList);
        listView.setItems(observableListPerson);

        listView.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {
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
        listView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent)
            {
                //System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                personDetail((Person)listView.getSelectionModel().getSelectedItem());
            }
        });
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


    protected void personDetail(Person person)
    {
        System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());

        //Create PersonDetailViewController which can pass a parameter
        PersonDetailController personDetailController = new PersonDetailController(person);
        GameWindow.getSingleton().createNextScene(personDetailController.load());

        Society.getSociety().getSocietyStatistics().removePropertyChangeListener(this);
    }

}
