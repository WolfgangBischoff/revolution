package Core.GuiController;

import Core.Company;
import Core.GameWindow;
import Core.GuiController.Company.CompanyController;
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
    @FXML Pane stat;

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
    {
        CompanyController companyController = new CompanyController(company);
        stat.getChildren().add(companyController.load());
    }
}
