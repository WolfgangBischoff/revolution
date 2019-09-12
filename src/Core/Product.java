package Core;

import Core.Enums.IndustryType;

import java.util.List;

public class Product
{
    private static Integer nextProductID = 1;
    Integer productID;
    String name;
    Company producent;
    ProductOwner owner;
    ProductOwner previousOwner;
    IndustryType  type;

    public Product(String name, Company producer, ProductOwner owner, IndustryType type)
    {
        this.productID = nextProductID++;
        this.name = name;
        this.producent = producer;
        this.owner = owner;
        this.type = type;
    }

    //Calculation
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

    //Prints
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", producent=" + producent.getName() +
                ", owner=" + owner.getName() +
                ", prev Owner: " + previousOwner.getName() +
                ", type: " + type +
                '}';
    }

    public String baseData()
    {
        return "ID: " + productID + " " + name + " Type: " + type;
    }
}
