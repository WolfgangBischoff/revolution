package Core.GuiController;

import Core.Company;
import Core.GameWindow;
import Core.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class CompanyDetailController
{
    Company company;
    FXMLLoader loader;
    @FXML
    private Button back;
    @FXML
    private Text name, deposit, numberEmployees, numberFreeWorkplaces, products;


    public CompanyDetailController(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("../../fxml/companyDetail.fxml"));
        loader.setController(this);
    }

    public Pane load()
    {
        try
        {
            return loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void initialize()
    {//Is used by fxml after this is set as Controller
        back.setOnAction((event) -> {
            GameWindow.getSingleton().createNextScene("../fxml/companyList.fxml");
        });

        name.setText(company.getName());
        deposit.setText(company.getDeposit().toString());
        numberEmployees.setText(company.getNumberEmployees().toString());
        numberFreeWorkplaces.setText(company.calcNumberFreeWorkpositions().toString());
        //products.setText(company.calcNumberProducts().toString());
    }
}
