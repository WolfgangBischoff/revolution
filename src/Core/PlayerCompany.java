package Core;

import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerCompany extends Company
{
    PropertyChangeSupport propertyChangeSupport;

    public PlayerCompany(String name, IndustryType industry, Integer Initdeposit, Integer price, Integer luxury)
    {
        super(name, industry, Initdeposit, price, luxury);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void setDeposit(Integer newdeposit)
    {
        Integer tmp = getDeposit();
        super.setDeposit(newdeposit);
        propertyChangeSupport.firePropertyChange(PROPERTY_DEPOSIT, tmp, newdeposit);
    }

    @Override
    public void  doMarketDecisions()
    {
        calcMarketAnalysis();
    }

}
