package Core.GuiController;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class economyOverviewController implements PropertyChangeListener
{
    Economy economy = Simulation.getSingleton().getEconomy();

    @FXML
    Text numberCompanies, depositsCompanies;

    @FXML
    private void initialize()
    {
        economy.getEconomyStatistics().addPropertyChangeListener(this);
        Market.getMarket().addPropertyChangeListener(this);

        numberCompanies.setText("" + economy.getEconomyStatistics().getNumberCompanies());
        depositsCompanies.setText("" + economy.getEconomyStatistics().getSumCompanyDeposits());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case EconomyStatistics.NAME_NUMBER_COMPANIES:
                numberCompanies.setText(evt.getNewValue().toString());
                break;
            case EconomyStatistics.NAME_SUM_COMPANY_DEPOSITS:
                depositsCompanies.setText(evt.getNewValue().toString());
                break;
            //case Market.NUMBER_PRODUCTS_NAME:
            //    productsOnMarket.setText(evt.getNewValue().toString());
             //   break;
            default:
                throw new RuntimeException("PropertyChange() don´t know: " + evt.getPropertyName());
        }

    }

    @FXML
    protected void backToMenu(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/mainMenu.fxml");
        removePropertyListeners();
    }

    @FXML
    private void hire(ActionEvent event)
    {
        economy.fillWorkplaces();
    }

    @FXML
    private void populate(ActionEvent event)
    {
        economy.populateEconomy(Util.DEFAULT_NUM_COMPANIES);
    }

    @FXML
    private void companyList(ActionEvent event)
    {
        GameWindow.getSingleton().createNextScene("../fxml/companyList.fxml");
        removePropertyListeners();
    }

    private void removePropertyListeners()
    {
        economy.getEconomyStatistics().removePropertyChangeListener(this);
        Market.getMarket().removePropertyChangeListener(this);
    }

}
