package Core.GuiController.Civilian;

import Core.Company;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class civCompanyListCellConsumer extends ListCell<Company>
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
        private Label luxury, price;

        Company company;

        public CompanyData(Company company)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../fxml/civilian/civCompanyListCellConsumer.fxml"));
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
            luxury.setText(company.getLuxury().toString());
            price.setText(company.getPrice().toString());
        }

        public GridPane getGridpane()
        {
            return gridpane;
        }
    }
}

