package Core.GuiController;

import Core.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class PersonData
{
    @FXML
    private HBox hBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;

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
        label1.setText(""+ person.getName());
        label2.setText("" + person.getAge());
    }

    public HBox getBox()
    {
        return hBox;
    }
}
