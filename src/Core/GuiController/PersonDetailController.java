package Core.GuiController;

import Core.Enums.ProductType;
import Core.GameWindow;
import Core.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class PersonDetailController
{
    Person person;
    FXMLLoader loader;
    @FXML
    private Text name;
    @FXML
    private Button back;
    @FXML
    private Text age, edu, deposit, grossincome, worksat, effectivehappiness, numberProducts;


    public PersonDetailController(Person person)
    {
        this.person = person;
        loader = new FXMLLoader(getClass().getResource("../../fxml/PersonDetail.fxml"));
        loader.setController(this);
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller
        back.setOnAction((event) -> {
            GameWindow.getSingleton().createNextScene("../fxml/personList.fxml");
        });

        name.setText(person.getName().toString());
        age.setText(person.getAge().toString());
        effectivehappiness.setText(person.getEffectiveHappiness().toString());
        edu.setText(person.getEducationalLayer().toString());
        deposit.setText(person.getDeposit().toString());
        grossincome.setText(person.getGrossIncome().toString());
        worksat.setText(person.printWorksAt());
        numberProducts.setText(person.getNumberProducts(ProductType.FOOD).toString());
    }
}
