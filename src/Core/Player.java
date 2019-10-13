package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class Player extends Person
{
    PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROPERTYNAME_DEPOSIT = "deposit";
    public static final String PROPERTYNAME_WORKPOSITION = "workposition";

    public Player(PersonName name, Integer age, EducationalLayer educationalLayer, Integer deposit)
    {
        super(name, age, educationalLayer, deposit);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        //System.out.println("Player added Listener: " +listener + " total: " + propertyChangeSupport.getPropertyChangeListeners().length);
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

        Market.getMarket().playerCollectMarketData(company);


        propertyChangeSupport.firePropertyChange(PROPERTYNAME_DEPOSIT, oldDeposit, deposit);
    }

    @Override
    public void startAtWorkposition(Workposition workposition)
    {
        super.startAtWorkposition(workposition);
        propertyChangeSupport.firePropertyChange(PROPERTYNAME_WORKPOSITION, null, worksAt);
    }

    @Override
    public void shop()
    {
        //ignore
    }

    @Override
    void receiveSalary(Integer salary)
    {
        Integer oldDeposit = deposit;
        super.receiveSalary(salary);
        propertyChangeSupport.firePropertyChange(PROPERTYNAME_DEPOSIT, oldDeposit, deposit);
    }


}
