package Core;

import Core.Enums.IndustryType;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;


public class Market //implements ProductOwner
{
    private static Market singleton;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String NUMBER_PRODUCTS_NAME = "numberProducts";
    //private ProductStorage productStorage;
    private Map<IndustryType, List<Company>> marketCompanies = new TreeMap<>();


    private Integer productPrice = 5;

    //Constructors
    private Market()
    {
        //productStorage = new ProductStorage(this);
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

    /*public void clear()
    {
        productStorage.clear();
    }*/

    public Company getBestOffer(IndustryType type, Integer budget)
    {//TODO check capacity & remember reason for not fitting
        List<Company> companies = marketCompanies.get(type);
        Company bestCompany = null;
        for(int i=0; i<companies.size(); i++)
        {
            Company toCheck = companies.get(i);
            if(toCheck.getPrice() <= budget)
            {
                if(bestCompany == null)
                {
                    bestCompany = toCheck; continue;
                }
                if(toCheck.getLuxury() > bestCompany.getLuxury())
                    bestCompany = toCheck;
                else if (toCheck.getLuxury() == bestCompany.getLuxury() && toCheck.getPrice() < bestCompany.getPrice())
                    bestCompany = toCheck;

            }
        }
        return bestCompany;
    }

    //Prints
    public String dataMarketCompanies()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----Market----\n");
        for(Map.Entry<IndustryType, List<Company>> industry : marketCompanies.entrySet())
        {
            if(industry.getValue().isEmpty())
                continue;
            stringBuilder.append(industry.getKey() + "\n");
            List<Company> companies = industry.getValue();
            for(Company company : companies)
                stringBuilder.append("\t" + company.baseData() + "\n");
        }
        return stringBuilder.toString();
    }
/*
    @Override
    public String getName()
    {
        return "Market";
    }

    @Override
    public void addProduct(Product product)
    {

        Integer tmpNumberProducts = productStorage.size();
        productStorage.add(product);
        propertyChangeSupport.firePropertyChange(NUMBER_PRODUCTS_NAME, tmpNumberProducts, productStorage.size());
    }

    @Override
    public void removeProduct(Product product)
    {
        /*
        Integer tmpNumberProducts = productStorage.size();
        productStorage.remove(product);
        propertyChangeSupport.firePropertyChange(NUMBER_PRODUCTS_NAME, tmpNumberProducts, productStorage.size());
    }

    @Override
    public void getPaid(Integer amount)
    {
        throw new RuntimeException("Should not happen");
    }

    @Override
    public void pay(Product price)
    {
        throw new RuntimeException("Should not happen");
    }

    @Override
    public void pay(List<Product> product)
    {
        throw new RuntimeException("Should not happen");
    }*/

    //Getter and Setter
    public static Market getMarket()
    {
        if (singleton == null)
            singleton = new Market();
        return singleton;
    }

/*
    public List<Product> sellProductsUnchecked(ProductOwner buyer, List<Product> bought)
    {
        if(bought.size() == 0)
        {
            //System.out.println(buyer.getName() + " No products to buy");
            return bought;
        }
        buyer.pay(bought);
        Product.transfer(this, buyer, bought);
        return bought;
    }
*/
    public Integer getProductPrice(IndustryType type)
    {
        //Different prices TODO
        return productPrice;
    }

  /*  public List<List<Product>> getAllProducts(IndustryType type)
    {
        return productStorage.getAllProducts(type);
    }

    @Override
    public double getMargin()
    {
        return 0;
    }*/

    public void addCompany(Company company)
    {
        marketCompanies.get(company.getIndustry()).add(company);
    }
}
