package Core.GuiController.Company;

import Core.Company;
import Core.Player;
import Core.PlayerCompany;
import Core.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import static Core.Company.*;
import static Core.Simulation.PROPERTY_DATE;

public class CompanyBaseDataC implements PropertyChangeListener
{
    @FXML
    Label name, date, deposit, capacity, freeWorkplaces, price, luxury;

    Company company;
    FXMLLoader loader;

    //For Player
    CompanyBaseDataC()
    {
        loader = new FXMLLoader(getClass().getResource("/fxml/company/companyBaseData.fxml"));
        loader.setController(this);
        company = Simulation.getSingleton().getPlayerCompany();

    }

    //For other Comp
    CompanyBaseDataC(Company company)
    {
        this.company = company;
        loader = new FXMLLoader(getClass().getResource("/fxml/company/companyBaseData.fxml"));
        loader.setController(this);
        company.addPropertyChangeListener(this);
        Simulation.getSingleton().addPropertyChangeListener(this);
    }

    @FXML
    private void initialize()
    {
        name.setText(company.getName());
        deposit.setText(company.getDeposit().toString());
        date.setText(Simulation.getSingleton().getCalender().dataDateWeekday());
        capacity.setText(company.getUsedCapacity().toString() + " / " + company.getMaxCapacity());
        freeWorkplaces.setText("" + company.getFreeWorkpositions().size() + " free jobs");
        price.setText(company.getPrice().toString());
        luxury.setText(company.getLuxury().toString());
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

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName() == PROPERTY_DEPOSIT)
            deposit.setText(evt.getNewValue().toString());
        else if (evt.getPropertyName() == PROPERTY_PRICE)
            price.setText(evt.getNewValue().toString());
        else if (evt.getPropertyName() == PROPERTY_LUXURY)
            luxury.setText(evt.getNewValue().toString());
        else if (evt.getPropertyName() == PROPERTY_DATE)
            date.setText(Simulation.getSingleton().getCalender().dataDateWeekday());
    }

    public void removePropertyListeners()
    {
        company.removePropertyChangeListener(this);
        Simulation.getSingleton().removePropertyChangeListener(this);
    }


}
