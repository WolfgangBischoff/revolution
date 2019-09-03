package Core.GuiController;

import Core.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class PersonData
{
    @FXML
    private HBox hbox;
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

    public HBox gethbox()
    {
        return hbox;
    }

    public GridPane getGridpane()
    {
        return gridpane;
    }
}
