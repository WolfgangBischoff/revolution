package Core;

import java.time.LocalDate;
import java.util.*;

public class MarketAnalysisData
{
    LocalDate date;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numPlayerBought = 0;
    TreeMap<Integer, Integer> numCustomerPerBudget = new TreeMap<>();
    Map<Integer, Integer> maxRevenueCustomerGroup = new TreeMap<>();
    Map<Integer, List<LuxuryPriceGroup>> offersPerCustomerGroup = new TreeMap<>();
    List<LuxuryPriceGroup> supplierOffers = new ArrayList<>();
    Map<Integer, Integer> maxCustomersAtPrice = new TreeMap<>();
    Map<Integer, Integer> maxRevenueAtPrice = new TreeMap<>();

    public void addCustomerBudget(Integer budget)
    {
        if (!numCustomerPerBudget.containsKey(budget))
            numCustomerPerBudget.put(budget, 0);
        numCustomerPerBudget.put(budget, numCustomerPerBudget.get(budget) + 1);
        marketTotalDemand++;
    }

    public void addSupplierOffer(Company company, Integer price, Integer luxury)
    {
        LuxuryPriceGroup tmp = new LuxuryPriceGroup(company, luxury, price);
        supplierOffers.add(tmp);
    }


    public void calculateMarketAnalysis()
    {
        Collections.sort(supplierOffers);
        maxCustomersAtPrice = new TreeMap<>();
        maxRevenueAtPrice = new TreeMap<>();

        Integer sumcustomers = marketTotalDemand;
        for (Map.Entry<Integer, Integer> budget : numCustomerPerBudget.entrySet())
        {
            Integer customerGroupProhibitivePrice = budget.getKey();
            maxRevenueCustomerGroup.put(customerGroupProhibitivePrice, budget.getValue() * customerGroupProhibitivePrice);
            maxCustomersAtPrice.put(customerGroupProhibitivePrice, sumcustomers);
            maxRevenueAtPrice.put(customerGroupProhibitivePrice, sumcustomers * customerGroupProhibitivePrice);
            sumcustomers -= budget.getValue();

            List offerListPerCustomerGroup = new ArrayList();
            Integer tmpMaxLux = 0, tmpMinPrice=0;
            for (LuxuryPriceGroup offer : supplierOffers)
            {
                //System.out.println(offer.luxury +">"+ tmpMaxLux +"&&"+ offer.price +"<="+ budget.getKey() +"||"+ offer.luxury +"=="+ tmpMaxLux +"&&"+ offer.price +"<"+ budget.getKey());
                if((offer.luxury > tmpMaxLux && offer.price <= budget.getKey()) || (offer.luxury == tmpMaxLux && offer.price < budget.getKey()))
                {
                    tmpMaxLux = offer.luxury;
                    tmpMinPrice = offer.price;
                }
                //if(offer.price == tmpMinPrice && offer.luxury == tmpMaxLux)
                if(offer.price <= budget.getKey())
                    offerListPerCustomerGroup.add(offer);
            }
            offersPerCustomerGroup.put(budget.getKey(), offerListPerCustomerGroup);
        }



    }


    public MarketAnalysisData(LocalDate date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--- " + date + " ---\n");
        stringBuilder.append(
                "Total Demand: " + marketTotalDemand +
                        "\nTotal Sold: " + marketTotalSold +
                        "\nBudget => NumCustomers: " + numCustomerPerBudget +
                        "\nCompOffer => NumCompanies: " + supplierOffers +
                        "\nAt Price => num Customer: " + maxCustomersAtPrice +
                        "\nAt price => Revenue: " + maxRevenueAtPrice +
                        "\nPlayer: " + numPlayerBought +
                        "\n"
        );
        return stringBuilder.toString();
    }

    public LocalDate getDate()
    {
        return date;
    }

    public Integer getMarketTotalDemand()
    {
        return marketTotalDemand;
    }

    public Integer getMarketTotalSold()
    {
        return marketTotalSold;
    }

    public Integer getNumPlayerBought()
    {
        return numPlayerBought;
    }

    public TreeMap<Integer, Integer> getNumCustomerPerBudget()
    {
        return numCustomerPerBudget;
    }

    public Map<Integer, Integer> getMaxRevenueCustomerGroup()
    {
        return maxRevenueCustomerGroup;
    }

    public Map<Integer, List<LuxuryPriceGroup>> getOffersPerCustomerGroup()
    {
        return offersPerCustomerGroup;
    }

    public List<LuxuryPriceGroup> getSupplierOffers()
    {
        return supplierOffers;
    }

    public Map<Integer, Integer> getMaxCustomersAtPrice()
    {
        return maxCustomersAtPrice;
    }

    public Map<Integer, Integer> getMaxRevenueAtPrice()
    {
        return maxRevenueAtPrice;
    }
}
