package Core;

import Core.Enums.IndustryType;

import java.util.*;

class UtilityValueComparator implements Comparator<Product>
{

    @Override
    public int compare(Product o1, Product o2)
    {
        return o1.utilityBase.compareTo(o2.utilityBase); //ascending
    }
}

class PersonListComparator implements Comparator<List<Product>>
{
    @Override
    public int compare(List<Product> o1, List<Product> o2)
    {
        //If list is empty should be deleted
        return o1.get(0).utilityLuxury.compareTo(o2.get(0).utilityLuxury);
    }
}

public class ProductStorage
{
    //private Map<IndustryType, List<Product>> products = new TreeMap<>();
    private Map<IndustryType, List<List<Product>>> productsGrid = new TreeMap<>();
    private Integer numberProducts = 0;
    private ProductOwner owner;

    //Constructors
    public ProductStorage(ProductOwner owner)
    {
        this.owner = owner;
        for (IndustryType i : IndustryType.values())
        {
            List<List<Product>> baseSortedList = new ArrayList();
            //List luxurySortedList = new ArrayList();
            //baseSortedList.add(luxurySortedList);
            productsGrid.put(i, baseSortedList);
        }


//OLDIMPL
        /*
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
        products.put(SPARETIME, sparetimeProducts);*/
    }

    /*
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
    */
    public List<List<Product>> getAllProducts(IndustryType type)
    {
        return productsGrid.get(type); //return products.get(type);
    }

    public void clear()
    {
        for (Map.Entry<IndustryType, List<List<Product>>> productgroup : productsGrid.entrySet())
            for (List<Product> luxuryLists : productgroup.getValue())
                luxuryLists.clear();
        /*
        for (Map.Entry<IndustryType, List<Product>> productgroup : products.entrySet())
            productgroup.getValue().clear();*/
    }


    //Prints
    public String productData()
    {
        StringBuilder stringBuilder = new StringBuilder("Storage of: " + owner.getName() + "\n\t");

        for (Map.Entry<IndustryType, List<List<Product>>> productgroup : productsGrid.entrySet())
            stringBuilder.append(productDataIndustry(productgroup.getKey()) + "\n\t");
        return stringBuilder.toString();

        //return stringBuilder.toString();
    }

    public String productDataIndustry(IndustryType industryType)
    {
        List<List<Product>> industryProducts = productsGrid.get(industryType);
        StringBuilder stringBuilder = new StringBuilder(industryType + ": \n\t");

        if(industryProducts.isEmpty())
            return "";

        for (List<Product> productsPerBasicUtil : industryProducts)
        {
            stringBuilder.append("Base util: " + productsPerBasicUtil.get(0).utilityBase + " Number: " + productsPerBasicUtil.size() + "\n\t");
            //for (Product product : productsPerBasicUtil)
            //    stringBuilder.append(product.baseData() + "\n\t");
        }
        return stringBuilder.toString();

    }

    private int findBaseUtilIdx(IndustryType type, int baseUtil)
    {
        //Iterate list of luxury lists to find luxury list that stores products with baseUtil
        List<List<Product>> baseUtilList = productsGrid.get(type);
        for (int i = 0; i < baseUtilList.size(); i++)
        {
            if (!baseUtilList.get(i).isEmpty() && (baseUtilList.get(i).get(0).utilityBase == baseUtil))
                return i;
        }
        return -1;
    }

    public void add(Product product)
    {
        int idxOfProductsWithSameBaseUtil = findBaseUtilIdx(product.type, product.utilityBase);
        if (idxOfProductsWithSameBaseUtil == -1)
        {
            //System.out.println("IDX "+ idxOfProductsWithSameBaseUtil);
            productsGrid.get(product.type).add(new ArrayList<>());
            idxOfProductsWithSameBaseUtil = productsGrid.get(product.type).size() - 1;
        }
        List<Product> luxuryListWithSameBaseUtil = productsGrid.get(product.type).get(idxOfProductsWithSameBaseUtil);
        luxuryListWithSameBaseUtil.add(product);
        Collections.sort(luxuryListWithSameBaseUtil, new UtilityValueComparator());
        //Sort List of different Base utils
        Collections.sort(productsGrid.get(product.type), new PersonListComparator());

        /*products.get(product.type).add(product);
        Collections.sort(products.get(product.type), new UtilityValueComparator());*/
        numberProducts++;
    }

    public void remove(Product product)
    {
        int idxOfProductsWithSameBaseUtil = findBaseUtilIdx(product.type, product.utilityBase);
        List<Product> luxuryListWithSameBaseUtil = productsGrid.get(product.type).get(idxOfProductsWithSameBaseUtil);
        luxuryListWithSameBaseUtil.remove(product);

        //delete list if empty
        if (luxuryListWithSameBaseUtil.isEmpty())
            productsGrid.get(product.type).remove(luxuryListWithSameBaseUtil);

        //products.get(product.type).remove(product);
        numberProducts--;
    }

    public Integer size()
    {
        return numberProducts;
    }

    public Integer calcBaseUtilSum(IndustryType type)
    {
        Integer sum = 0;
        List<List<Product>> baseUtilList = productsGrid.get(type);
        for (List<Product> luxurySortedList : baseUtilList)
            for (Product product : luxurySortedList)
                sum += product.utilityBase;
        return sum;

        /*
        Integer sum = 0;
        List<Product> summedUp = products.get(type);
        for (Product product : summedUp)
            sum += product.utilityBase;
        return sum;*/
    }
/*
    public Integer calcBaseUtilSum()
    {
        Integer sum = 0;
        for (Map.Entry<IndustryType, List<Product>> type : products.entrySet())
            sum += calcBaseUtilSum(type.getKey());
        return sum;
    }
*/

    /*   public Integer size(IndustryType type)
       {
           return products.get(type).size();
     }
   /*
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
*/
    public ProductOwner getOwner()
    {
        return owner;
    }
}
