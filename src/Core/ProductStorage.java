package Core;

import Core.Enums.IndustryType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static Core.Enums.IndustryType.*;

public class ProductStorage
{
    private Map<IndustryType, List<Product>> products = new TreeMap<>();
    private Integer numberProducts = 0;
    private ProductOwner owner;

    //Constructors
    public ProductStorage(ProductOwner owner)
    {
        this.owner = owner;
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

    public Product getProduct(IndustryType type)
    {
        if (!products.get(type).isEmpty())
            return products.get(type).get(0);
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
        StringBuilder stringBuilder = new StringBuilder("Storage of: " + owner.getName() + "\n\t");

        for (Map.Entry<IndustryType,List<Product> > productgroup : products.entrySet())
            stringBuilder.append(productDataIndustry(productgroup.getKey()) + "\n\t");
        return stringBuilder.toString();
    }

    public String productDataIndustry(IndustryType industryType)
    {
        List<Product> industryProducts = products.get(industryType);
        StringBuilder stringBuilder = new StringBuilder(industryType + ": \n\t");
        for(Product product : industryProducts)
            stringBuilder.append(product.baseData() + "\n\t");
        return stringBuilder.toString();
    }

    public void add(Product product)
    {
        products.get(product.type).add(product);
        numberProducts++;
    }

    public void remove(Product product)
    {
        products.get(product.type).remove(product);
        numberProducts--;
    }

    public Integer size()
    {
        return numberProducts;
    }
    public Integer size(IndustryType type)
    {
        return products.get(type).size();
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


}
