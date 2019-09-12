package Core;


import Core.Enums.BudgetPost;
import Core.Enums.IndustryType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static Core.Enums.IndustryType.*;

public class Market implements ProductOwner
{
    private static Market singleton;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String NUMBER_PRODUCTS_NAME = "numberProducts";

    //private List<Product> products = new ArrayList<>();
    private Integer productPrice = 5;

    private Map<IndustryType, List<Product>> products = new TreeMap<>();
    private Integer numberProducts = 0;

    //Constructors
    private Market()
    {
        List<Product> foodProducts = new ArrayList<>();
        List<Product> clothsProducts = new ArrayList<>();
        List<Product> housingProducts = new ArrayList<>();
        List<Product> energyProducts = new ArrayList<>();
        List<Product> electronicsProducts = new ArrayList<>();
        List<Product> healthProducts = new ArrayList<>();
        List<Product> trafficProducts = new ArrayList<>();
        List<Product> educationProducts = new ArrayList<>();
        List<Product> sparetimeProducts = new ArrayList<>();
        products.put(FOOD, foodProducts);
        products.put(CLOTHS, clothsProducts);
        products.put(HOUSING, housingProducts);
        products.put(ENERGY, energyProducts);
        products.put(ELECTRONICS, electronicsProducts);
        products.put(HEALTH, healthProducts);
        products.put(TRAFFIC,trafficProducts);
        products.put(EDUCATION, educationProducts);
        products.put(SPARETIME, sparetimeProducts);
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

    public List<Product> getProduct(IndustryType type)
    {
        if (!products.isEmpty())
            return products.get(type);
        else
            return null;
    }

    public void clear()
    {
        for (Map.Entry<IndustryType,List<Product> > productgroup : products.entrySet())
            productgroup.getValue().clear();
    }


    //Prints
    public String productData()
    {
        StringBuilder stringBuilder = new StringBuilder("Market Products: \n\t");

        for (Map.Entry<IndustryType,List<Product> > productgroup : products.entrySet())
            stringBuilder.append(productDataIndustry(productgroup.getKey()) + "\n\t");
        return stringBuilder.toString();
    }

    public String productDataIndustry(IndustryType industryType)
    {
        List<Product> industryProducts = getProduct(industryType);
        StringBuilder stringBuilder = new StringBuilder(industryType + ": \n\t");
        for(Product product : industryProducts)
            stringBuilder.append(product.baseData() + "\n\t");
        return stringBuilder.toString();
    }

    @Override
    public String getName()
    {
        return "Market";
    }

    private Integer countProductgroup(IndustryType type)
    {
        return products.get(type).size();
    }

    private Integer countProducts()
    {
        Integer sum = 0;
        for(Map.Entry<IndustryType, List<Product>> productgroup : products.entrySet())
            sum += productgroup.getValue().size();
        return sum;
    }

    @Override
    public void addProduct(Product product)
    {
        Integer tmpNumberProducts = numberProducts;
        switch (product.type)
        {
            case FOOD:
                products.get(product.type).add(product); break;
            case CLOTHS:
                products.get(product.type).add(product); break;
            case ENERGY:
                products.get(product.type).add(product); break;
            case HEALTH:
                products.get(product.type).add(product); break;
            case HOUSING:
                products.get(product.type).add(product); break;
            case TRAFFIC:
                products.get(product.type).add(product); break;
            case EDUCATION:
                products.get(product.type).add(product); break;
            case SPARETIME:
                products.get(product.type).add(product); break;
            case ELECTRONICS:
                products.get(product.type).add(product); break;
            default:
                throw new RuntimeException("addProduct(): industryType unknown");
        }
        numberProducts = countProducts();
        propertyChangeSupport.firePropertyChange(NUMBER_PRODUCTS_NAME, tmpNumberProducts, numberProducts);
    }

    @Override
    public void removeProduct(Product product)
    {
        Integer tmpNumberProducts = numberProducts;
        switch (product.type)
        {
            case FOOD:
                products.get(product.type).remove(product); break;
            case CLOTHS:
                products.get(product.type).remove(product); break;
            case ENERGY:
                products.get(product.type).remove(product); break;
            case HEALTH:
                products.get(product.type).remove(product); break;
            case HOUSING:
                products.get(product.type).remove(product); break;
            case TRAFFIC:
                products.get(product.type).remove(product); break;
            case EDUCATION:
                products.get(product.type).remove(product); break;
            case SPARETIME:
                products.get(product.type).remove(product); break;
            case ELECTRONICS:
                products.get(product.type).remove(product); break;
            default:
                throw new RuntimeException("removeProduct(): industryType unknown");
        }
        numberProducts = countProducts();
        propertyChangeSupport.firePropertyChange(NUMBER_PRODUCTS_NAME, tmpNumberProducts, numberProducts);
    }

    @Override
    public void getPaid(Integer amount)
    {
        throw new RuntimeException("Should not happen");
    }

    //Getter and Setter
    public static Market getMarket()
    {
        if (singleton == null)
            singleton = new Market();
        return singleton;
    }

    public Integer getNumberProducts()
    {
        return numberProducts;
    }

    public List<Product> getProducts(IndustryType type)
    {
        return products.get(type);
    }

    public Integer getProductPrice()
    {
        return productPrice;
    }
}
