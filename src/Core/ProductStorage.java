package Core;

import Core.Enums.IndustryType;

import java.util.*;

class LuxuryValueComparator implements Comparator<Product>
{

    @Override
    public int compare(Product o1, Product o2)
    {
        return o1.utilityLuxury.compareTo(o2.utilityLuxury); //ascending
    }
}

class ProductListComparator implements Comparator<List<Product>>
{
    @Override
    public int compare(List<Product> o1, List<Product> o2)
    {
        //If list is empty should be deleted
        return o2.get(0).getUtilityBase().compareTo(o1.get(0).getUtilityBase());
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
            productsGrid.put(i, baseSortedList);
        }
    }

    public List<List<Product>> getAllProducts(IndustryType type)
    {
        return productsGrid.get(type);
    }

    public void clear()
    {
        for (Map.Entry<IndustryType, List<List<Product>>> productgroup : productsGrid.entrySet())
            for (List<Product> luxuryLists : productgroup.getValue())
                luxuryLists.clear();

    }


    //Prints
    public String productData()
    {
        StringBuilder stringBuilder = new StringBuilder("Storage of: " + owner.getName() + "\n\t");

        for (Map.Entry<IndustryType, List<List<Product>>> productgroup : productsGrid.entrySet())
            stringBuilder.append(productDataIndustry(productgroup.getKey()) + "\n\t");
        return stringBuilder.toString();
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
            for (Product product : productsPerBasicUtil)
                stringBuilder.append(product.baseData() + "\n\t");
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
        Collections.sort(luxuryListWithSameBaseUtil, new LuxuryValueComparator());
        //Sort List of different Base utils
        Collections.sort(productsGrid.get(product.type), new ProductListComparator());

        /*products.get(product.type).add(product);
        Collections.sort(products.get(product.type), new LuxuryValueComparator());*/
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
    }

    public ProductOwner getOwner()
    {
        return owner;
    }
}
