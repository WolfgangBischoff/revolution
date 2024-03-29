package Core;

import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.*;


public class Market
{
    private static Market singleton;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    //public static final String NUMBER_PRODUCTS_NAME = "numberProducts";
    private Map<IndustryType, List<Company>> marketCompanies = new TreeMap<>();
    private Map<Company, Integer> companySaleNumbers = new HashMap<>();
    private MarketanalysisDataStorage marketanalysisDataStorage = new MarketanalysisDataStorage();

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

    //Calculations
    public void initPeriod()
    {
        marketanalysisDataStorage.initNewDay();
        companySaleNumbers.clear();
    }

    public void collectMarketData()
    {
        //Collect Offers
        for (Map.Entry<IndustryType, List<Company>> industry : marketCompanies.entrySet())
        {
            for (Company company : industry.getValue())
                marketanalysisDataStorage.addSupplierOffer(company, company.getPrice(), company.getLuxury());
        }
        marketanalysisDataStorage.calculateMarketAnalysis();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println("Number Market Listeners: " + propertyChangeSupport.getPropertyChangeListeners().length);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public Company getBestOffer(IndustryType type, Integer budget)
    {
        /*
        if (budget == 0)
            return null;
        */

        List<Company> companies = marketCompanies.get(type);
        Company bestCompany = null;

        for (int i = 0; i < companies.size(); i++)
        {
            Company toCheck = companies.get(i);
            //Init sold data
            if (!companySaleNumbers.containsKey(toCheck))
                companySaleNumbers.put(toCheck, 0);

            //Can afford & company can supply
            if (toCheck.getPrice() <= budget && toCheck.canProduce())
            {
                //Init first affordable company
                if (bestCompany == null)
                {
                    bestCompany = toCheck;
                    continue;
                }

                //If same offer
                if (toCheck.getLuxury().equals(bestCompany.getLuxury()) && toCheck.getPrice().equals(bestCompany.getPrice()))
                {
                    if (companySaleNumbers.get(toCheck) < companySaleNumbers.get(bestCompany))
                        bestCompany = toCheck;
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

            }
        }

        //Update sold data
        if (bestCompany != null)
        {
            bestCompany.addSellData();
            companySaleNumbers.put(bestCompany, companySaleNumbers.get(bestCompany) + 1);
        }
        marketanalysisDataStorage.addCustomerBudget(type, budget, bestCompany);

        return bestCompany;
    }

    public void calcState()
    {
        companySaleNumbers = new HashMap<>();
    }

    public void playerCollectMarketData(Company bestCompany)
    {
        marketanalysisDataStorage.addNewDataPlayer(bestCompany);
        bestCompany.addSellDataPlayer();
    }

    public MarketAnalysisData getMarketAnalysisData(IndustryType type, LocalDate date)
    {
        return marketanalysisDataStorage.getAnalysisData(type, date);
    }

    public List<MarketAnalysisData> getMarketAnalysisData(IndustryType type)
    {
        return marketanalysisDataStorage.getAnalysisData(type);
    }

    //Prints
    public String dataMarketCompanies()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----Market Companies----\n");
        for (Map.Entry<IndustryType, List<Company>> industry : marketCompanies.entrySet())
        {
            if (industry.getValue().isEmpty())
                continue;
            stringBuilder.append(industry.getKey() + "\n");
            List<Company> companies = industry.getValue();
            for (Company company : companies)
                stringBuilder.append("\t" + company.dataBase() + "\n");
        }
        stringBuilder.append(companySaleNumbers);
        return stringBuilder.toString();
    }

    public String dataMarketAnalysis(IndustryType type)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----Market Analysis----\n");
        if(type == null)
            stringBuilder.append(marketanalysisDataStorage.dataAnalysis());
        else
            stringBuilder.append(marketanalysisDataStorage.dataAnalysis(type));
        return stringBuilder.toString();
    }

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
