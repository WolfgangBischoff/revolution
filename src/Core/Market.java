package Core;


import java.util.ArrayList;
import java.util.List;

public class Market implements ProductOwner {
    private static Market singleton;
    List<Product> products = new ArrayList<>();

    //Constructors
    private Market()
    {

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
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }

    //Getter and Setter
    public static Market getMarket()
    {
        if (singleton == null)
            singleton = new Market();
        return singleton;
    }
}
