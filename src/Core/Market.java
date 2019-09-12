package Core;

import Core.Enums.IndustryType;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;


public class Market implements ProductOwner
{
    private static Market singleton;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String NUMBER_PRODUCTS_NAME = "numberProducts";
    private Integer productPrice = 5;
    private ProductStorage productStorage;

    //Constructors
    private Market()
    {
        productStorage = new ProductStorage(this);
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

    public void clear()
    {
        productStorage.clear();
    }


    //Prints
    public String productData()
    {
        return productStorage.productData();
    }

    public String productDataIndustry(IndustryType industryType)
    {
        return productStorage.productDataIndustry(industryType);
    }

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
    public boolean pay(Product price)
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
        return productStorage.size();
    }

    public Integer getNumberProducts(IndustryType type)
    {
        return productStorage.size(type);
    }

   public Product sellProduct(IndustryType type, ProductOwner buyer)
    {
        Product bought = productStorage.getProduct(type);
        buyer.pay(bought);
        Product.transfer(this, buyer, bought);
        System.out.println(buyer.getName() + " bought " + bought.name);
        return bought;
    }

    public Integer getProductPrice()
    {
        return productPrice;
    }
}
