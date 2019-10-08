package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class Player extends Person
{
    PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROPERTYNAME_DEPOSIT = "deposit";

    public Player(PersonName name, Integer age, EducationalLayer educationalLayer, Integer deposit)
    {
        super(name, age, educationalLayer, deposit);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println("Player added Listener: " +listener + " total: " + propertyChangeSupport.getPropertyChangeListeners().length);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public boolean isAffordable(Company company)
    {
        return deposit>= company.getPrice();
    }

    public boolean playerIsAvailable(Company company)
    {
        return company.canProduce();
    }

    public boolean playerIsSaturated(IndustryType type)
    {
        LocalDate today = Simulation.getSingleton().getDate();
        ConsumeData consumeData = null;
        if(consumeDataStorage.hasDataOf(today))
            consumeData = consumeDataStorage.getConsumeData(today);
        else
            return false;

        return consumeData.consumeData.containsKey(type);
    }
    public void playerBuyUnchecked(Company company)
    {
        consumeDataStorage.consume(company.getIndustry(), company.getLuxury());
        Integer oldDeposit = deposit;
        deposit -= company.getPrice();
        company.produce();
        company.getPaid(company.getPrice());

        Market.getMarket().playerCollectMarketDataForCompetitors(company);

        propertyChangeSupport.firePropertyChange(PROPERTYNAME_DEPOSIT, oldDeposit, deposit);
    }


}
