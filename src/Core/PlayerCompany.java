package Core;

import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerCompany extends Company
{
    PropertyChangeSupport propertyChangeSupport;
    public static final String PROPERTY_DEPOSIT = "deposit";

    public PlayerCompany(String name, IndustryType industry, Integer Initdeposit, Integer price, Integer luxury)
    {
        super(name, industry, Initdeposit, price, luxury);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void setDeposit(Integer deposit)
    {
        Integer tmp = deposit;
        super.setDeposit(deposit);
        propertyChangeSupport.firePropertyChange(PROPERTY_DEPOSIT, tmp, deposit);
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

}
