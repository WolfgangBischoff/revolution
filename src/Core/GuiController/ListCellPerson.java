package Core.GuiController;

import Core.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ListCellPerson extends ListCell<Person>
{

    @Override
    public void updateItem(Person person, boolean empty)
    {
        super.updateItem(person,empty);
        if(person != null)
        {
            PersonData data = new PersonData(person);
            data.setInfo();
            setGraphic(data.getGridpane());
        }
    }

    class PersonData
    {

        @FXML
        private GridPane gridpane;
        @FXML
        private Text name;
        @FXML
        private Text age;
        @FXML
        private Text deposit;

        Person person;

        public PersonData(Person person)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/personListItem.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            this.person = person;
        }

        public void setInfo()
        {
            name.setText(""+ person.getName());
            age.setText("" + person.getAge());
            deposit.setText("" + person.getDeposit());
        }

        public GridPane getGridpane()
        {
            return gridpane;
        }
    }
}
