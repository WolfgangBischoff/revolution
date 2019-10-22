package Core;

import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerCompany extends Company
{
    PropertyChangeSupport propertyChangeSupport;
    public static final String PROPERTY_DEPOSIT = "deposit";
    public static final String PROPERTY_PRICE = "price";
    public static final String PROPERTY_LUXURY = "luxury";

    public PlayerCompany(String name, IndustryType industry, Integer Initdeposit, Integer price, Integer luxury)
    {
        super(name, industry, Initdeposit, price, luxury);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void setDeposit(Integer newdeposit)
    {
        //System.out.println("PlayerCompany setDeposit");
        Integer tmp = getDeposit();
        super.setDeposit(newdeposit);
        propertyChangeSupport.firePropertyChange(PROPERTY_DEPOSIT, tmp, newdeposit);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println("Player added Listener: " + listener + " total: " + propertyChangeSupport.getPropertyChangeListeners().length);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void setPrice(Integer newPrice)
    {
        propertyChangeSupport.firePropertyChange(PROPERTY_PRICE, getPrice(), newPrice);
        super.setPrice(newPrice);
    }

    @Override
    public void setLuxury(Integer newLuxury)
    {
        propertyChangeSupport.firePropertyChange(PROPERTY_LUXURY, getLuxury(), newLuxury);
        super.setLuxury(newLuxury);
    }

    @Override
    public void  doMarketDecisions()
    {
        calcMarketAnalysis();
    }

}
