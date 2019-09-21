package Core;

import Core.Enums.IndustryType;
import java.util.List;

public class Product
{
    private static Integer nextProductID = 1;
    Integer productID;
    String name;
    private Company producent;
    ProductOwner owner;
    private ProductOwner previousOwner;
    IndustryType  type;
    Integer utilityBase;
    Integer utilityLuxury;
    Integer numberUsable = 1;
    private Integer priceProductWasBought = null; //price the product was bought

    public Product(String name, Company producer, ProductOwner owner, IndustryType type, Integer utilityUnitsBase, Integer luxuryUnits)
    {
        this.productID = nextProductID++;
        this.name = name;
        this.producent = producer;
        this.owner = owner;
        this.type = type;
        this.utilityBase = utilityUnitsBase;
        this.utilityLuxury = luxuryUnits;
    }

    //Calculation
    public Integer calcPrice()
    {
        Integer priceWithtoutMargin = (utilityBase + utilityLuxury) * numberUsable;
        return (int)(priceWithtoutMargin * (1+owner.getMargin()));
    }

/*
    public static void transfer(ProductOwner sender, ProductOwner receipient, Product product)
    {
        product.previousOwner = product.owner;
        receipient.addProduct(product);
        sender.removeProduct(product);
    }

    public static void transfer(ProductOwner sender, ProductOwner receipient, List<Product> products)
    {
        for(Product p : products)
        {
            transfer(sender, receipient, p);
        }
    }
*/



    //Prints
    @Override
    public String toString() {
        return "\nProduct{" +
                "productID=" + productID +
                //", name='" + name + '\'' +
                ", producent=" + producent.getName() +
                //", owner=" + owner.getName() +
                //", prev Owner: " + previousOwner.getName() +
                ", type: " + type +
                " Utility:Luxury " + utilityBase + ":" + utilityLuxury +
                '}';
    }

    public String baseData()
    {
        return "ID: " + productID + " " + name + " Type: " + type + " Utility:Luxury " + utilityBase + ":" + utilityLuxury;
    }

    //Getter
    public String getName()
    {
        return name;
    }

    public Integer getUtilityBase()
    {
        return utilityBase;
    }

    public void setPriceProductWasBought(Integer priceProductWasBought)
    {
        this.priceProductWasBought = priceProductWasBought;
    }

    public Integer getPriceProductWasBought()
    {
        return priceProductWasBought;
    }
}
