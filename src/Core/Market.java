package Core;


import Core.Enums.ProductType;
import javafx.beans.property.Property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Market implements ProductOwner {
    private static Market singleton;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String NUMBER_PRODUCTS_NAME = "numberProducts";

    private List<Product> products = new ArrayList<>();
    private Integer productPrice = 5;

    //Constructors
    private Market()
    {

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

    public Product getProduct(ProductType type)
    {
        if(!products.isEmpty())
        return products.get(0);
        else
            return null;
    }


    //Prints
    public String productData()
    {
        StringBuilder stringBuilder = new StringBuilder("Market Products: \n\t");
        if(products.isEmpty())
        {
            stringBuilder.append("\tNo Products");
            return stringBuilder.toString();
        }
        for (Product product : products)
            stringBuilder.append(product.baseData() + "\n\t");
        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        return "Market";
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
        propertyChangeSupport.firePropertyChange(NUMBER_PRODUCTS_NAME, products.size()-1, products.size());
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
        propertyChangeSupport.firePropertyChange(NUMBER_PRODUCTS_NAME, products.size()+1, products.size());
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
        return products.size();
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public Integer getProductPrice()
    {
        return productPrice;
    }
}
