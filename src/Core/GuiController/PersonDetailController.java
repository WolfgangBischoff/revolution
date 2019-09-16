package Core.GuiController;

import Core.Enums.IndustryType;
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
    private Text age, edu, deposit, grossincome, worksat, effectivehappiness,
            numberFood, numberClothing, numberHousing, numberEnergy, numberElectronics, numberHealth, numberTraffic, numberEducation, numberSparetime;


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
        numberFood.setText(person.getUtilFromType(IndustryType.FOOD).toString());
        numberClothing.setText(person.getUtilFromType(IndustryType.CLOTHS).toString());
        numberHousing.setText(person.getUtilFromType(IndustryType.HOUSING).toString());
        numberEnergy.setText(person.getUtilFromType(IndustryType.ENERGY).toString());
        numberElectronics.setText(person.getUtilFromType(IndustryType.ELECTRONICS).toString());
        numberHealth.setText(person.getUtilFromType(IndustryType.HEALTH).toString());
        numberTraffic.setText(person.getUtilFromType(IndustryType.TRAFFIC).toString());
        numberEducation.setText(person.getUtilFromType(IndustryType.EDUCATION).toString());
        numberSparetime.setText(person.getUtilFromType(IndustryType.SPARETIME).toString());
    }

    @FXML
    private void showStorageDetail()
    {
        System.out.println("Storage Detail Screen");
    }
}

