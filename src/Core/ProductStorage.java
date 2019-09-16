package Core;

import Core.Enums.IndustryType;

import java.util.*;

import static Core.Enums.IndustryType.*;

class UtilityValueComparator implements Comparator<Product>
{

    @Override
    public int compare(Product o1, Product o2)
    {
        return o2.utilityUnits.compareTo(o1.utilityUnits); //descending
    }
}

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
        products.put(TRAFFIC, trafficProducts);
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

    public List<Product> getProduct(IndustryType type, Integer amount)
    {
        List<Product> allProducts = products.get(type);
        List<Product> ret = new ArrayList<>();
        if (products.get(type).size() < amount)
            amount = products.get(type).size();

        for (int i = 0; i < amount; i++)
            ret.add(allProducts.get(i));

        return ret;
    }

    public List<Product> getAllProducts(IndustryType type)
    {
        return products.get(type);
    }

    public void clear()
    {
        for (Map.Entry<IndustryType, List<Product>> productgroup : products.entrySet())
            productgroup.getValue().clear();
    }


    //Prints
    public String productData()
    {
        StringBuilder stringBuilder = new StringBuilder("Storage of: " + owner.getName() + "\n\t");

        for (Map.Entry<IndustryType, List<Product>> productgroup : products.entrySet())
            stringBuilder.append(productDataIndustry(productgroup.getKey()) + "\n\t");
        return stringBuilder.toString();
    }

    public String productDataIndustry(IndustryType industryType)
    {
        List<Product> industryProducts = products.get(industryType);
        StringBuilder stringBuilder = new StringBuilder(industryType + ": \n\t");
        for (Product product : industryProducts)
            stringBuilder.append(product.baseData() + "\n\t");
        return stringBuilder.toString();
    }

    public void add(Product product)
    {
        products.get(product.type).add(product);
        Collections.sort(products.get(product.type), new UtilityValueComparator());
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

    public Integer calcUtilSum(IndustryType type)
    {
        Integer sum = 0;
        List<Product> summedUp = products.get(type);
        for (Product product : summedUp)
            sum += product.utilityUnits;
        return sum;
    }

    public Integer calcUtilSum()
    {
        Integer sum = 0;
        for (Map.Entry<IndustryType, List<Product>> type : products.entrySet())
            sum += calcUtilSum(type.getKey());
        return sum;
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
        for (Map.Entry<IndustryType, List<Product>> productgroup : products.entrySet())
            sum += productgroup.getValue().size();
        return sum;
    }

    public ProductOwner getOwner()
    {
        return owner;
    }
}
