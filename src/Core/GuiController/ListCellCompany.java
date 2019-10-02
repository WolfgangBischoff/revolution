package Core.GuiController;

import Core.Company;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ListCellCompany extends ListCell<Company>
{
    @Override
    public void updateItem(Company company, boolean empty)
    {
        super.updateItem(company, empty);
        if (company != null)
        {
            CompanyData data = new CompanyData(company);
            data.setInfo();
            setGraphic(data.getGridpane());
        }
    }


    class CompanyData
    {
        @FXML
        private GridPane gridpane;
        @FXML
        private Text companyName;
        @FXML
        private Text numberEmployees;
        @FXML
        private Text numberWorkplaces;
        @FXML
        private Text companyDeposit;

        Company company;

        public CompanyData(Company company)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/companyListItem.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            this.company = company;
        }

        public void setInfo()
        {
            companyName.setText(company.getName());
            numberEmployees.setText("" + company.getNumberEmployees());
            numberWorkplaces.setText("" + company.getWorkpositions().size());
            companyDeposit.setText("" + company.getDeposit());
        }

        public GridPane getGridpane()
        {
            return gridpane;
        }
    }
}
