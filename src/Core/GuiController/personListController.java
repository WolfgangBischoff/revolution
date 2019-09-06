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

import java.util.List;

public class personListController
{
    @FXML
    private ListView listView;
    private List<Person> personList;
    private ObservableList observableListPerson = FXCollections.observableArrayList();


    public void setListView()
    {

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
        personList = Society.getSociety().getPeople();
        listView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>()
        {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent)
            {
                showPersonDetail((Person) listView.getSelectionModel().getSelectedItem());
            }
        });
        setListView();
    }

    @FXML
    protected void backToOverview(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/societyOverview.fxml");
    }


    protected void showPersonDetail(Person person)
    {
        //Create PersonDetailViewController which can pass a parameter
        PersonDetailController personDetailController = new PersonDetailController(person);
        GameWindow.getSingleton().createNextScene(personDetailController.load());
    }

}
