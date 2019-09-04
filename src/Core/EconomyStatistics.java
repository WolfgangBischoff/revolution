package Core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class EconomyStatistics extends Statistics
{
    public static final String NAME_NUMBER_COMPANIES = "numberCompanies";
    public static final String NAME_SUM_COMPANY_DEPOSITS = "sumCompanyDeposits";

    private Economy economy;
    private List<Company> companyList;
    private PropertyChangeSupport propertyChangeSupport;

    private Integer numberCompanies;
    private Integer sumCompanyDeposits;

    //Constructors
    public EconomyStatistics(Economy economy)
    {
        this.economy = economy;
        companyList = economy.getCompanies();
        propertyChangeSupport = new PropertyChangeSupport(this);
        calcState();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println("ADDED");
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    //Calculations
    void calcState()
    {
        numberCompanies = calcNumberCompanies();
        sumCompanyDeposits = calcSumCompanyDeposits();
        //GRP
        //GINI
    }

    private Integer calcSumCompanyDeposits()
    {
        Integer sum = 0;
        for(Company company : companyList)
        {
            sum += company.getDeposit();
        }
        propertyChangeSupport.firePropertyChange(NAME_SUM_COMPANY_DEPOSITS, sumCompanyDeposits, sum);
        return sum;
    }

    private Integer calcNumberCompanies()
    {
        Integer tmp = companyList.size();
        propertyChangeSupport.firePropertyChange(NAME_NUMBER_COMPANIES, numberCompanies, tmp);
        return tmp;
    }

    //Getter and Setter
    public Integer getNumberCompanies()
    {
        return numberCompanies;
    }

    public Integer getSumCompanyDeposits()
    {
        return sumCompanyDeposits;
    }
}
