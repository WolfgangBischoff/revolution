package Core;

import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;


public class Market
{
    private static Market singleton;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String NUMBER_PRODUCTS_NAME = "numberProducts";
    private Map<IndustryType, List<Company>> marketCompanies = new TreeMap<>();
    //private MarketanalysisDataStorage marketanalysisDataStorage = new MarketanalysisDataStorage(this);

    //Constructors
    private Market()
    {
        marketCompanies.put(IndustryType.FOOD, new ArrayList<Company>());
        marketCompanies.put(IndustryType.CLOTHS, new ArrayList<Company>());
        marketCompanies.put(IndustryType.SPARETIME, new ArrayList<Company>());
        marketCompanies.put(IndustryType.EDUCATION, new ArrayList<Company>());
        marketCompanies.put(IndustryType.TRAFFIC, new ArrayList<Company>());
        marketCompanies.put(IndustryType.HEALTH, new ArrayList<Company>());
        marketCompanies.put(IndustryType.ELECTRONICS, new ArrayList<Company>());
        marketCompanies.put(IndustryType.ENERGY, new ArrayList<Company>());
        marketCompanies.put(IndustryType.HOUSING, new ArrayList<Company>());
    }

    //Calc
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println("Number Market Listeners: " + propertyChangeSupport.getPropertyChangeListeners().length);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
/*
    public void initNewDay()
    {
        marketanalysisDataStorage.initNewDay();
    }
*/
    public Company getBestOffer(IndustryType type, Integer budget)
    {
        List<Company> companies = marketCompanies.get(type);
        Company bestCompany = null;

        for (int i = 0; i < companies.size(); i++)
        {
            Company toCheck = companies.get(i);

            //Can afford & company can supply
            if (toCheck.getPrice() <= budget && toCheck.canProduce())
            {
                //Init
                if (bestCompany == null)
                {
                    bestCompany = toCheck; continue;
                }

                //Found company with more luxury
                if (toCheck.getLuxury() > bestCompany.getLuxury())
                {
                    bestCompany = toCheck;
                }
                //Found company with same luxury but better price
                else if (toCheck.getLuxury() == bestCompany.getLuxury() && toCheck.getPrice() < bestCompany.getPrice())
                {
                    bestCompany = toCheck;
                }

                //TODO Distibute Same offer
            }
        }

        if(bestCompany == null)
        {
            //System.out.println("TODO ARBEITSLOS KEIN UNTERNEHMEN MÖGLICH: " + budget);
        }
        collectMarketDataForCompetitors(bestCompany, budget, type);
        return bestCompany;
    }


    private void collectMarketDataForCompetitors(Company bestCompany, Integer budget, IndustryType type)
    {
        for (Company comparedCompany : marketCompanies.get(type))
        {
            comparedCompany.getMarketanalysisData().addNewData(bestCompany, budget);
        }
    }

    //Prints
    public String dataMarketCompanies()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----Market----\n");
        for (Map.Entry<IndustryType, List<Company>> industry : marketCompanies.entrySet())
        {
            if (industry.getValue().isEmpty())
                continue;
            stringBuilder.append(industry.getKey() + "\n");
            List<Company> companies = industry.getValue();
            for (Company company : companies)
                stringBuilder.append("\t" + company.baseData() + "\n");
        }
        return stringBuilder.toString();
    }
/*
    public String dataMarketAnalysis()
    {
        return marketanalysisDataStorage.dataAnalysis();
    }*/

    //Getter and Setter
    public static Market getMarket()
    {
        if (singleton == null)
            singleton = new Market();
        return singleton;
    }


    public void addCompany(Company company)
    {
        marketCompanies.get(company.getIndustry()).add(company);
    }
}
