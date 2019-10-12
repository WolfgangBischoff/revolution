package Core;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class MarketAnalysisData
{
    Map<Integer, Integer> customerBudgets = new TreeMap<>();
    Map<LuxuryPriceGroup, Integer> supplierOffers = new TreeMap<>();
    Map<Integer, Integer> customersAtPrice = new TreeMap<>();
    Map<Integer, Integer> revenueAtPrice = new TreeMap<>();

    class LuxuryPriceGroup implements Comparable<LuxuryPriceGroup>
    {
        Integer luxury, price;
        Company company;

        public LuxuryPriceGroup(Company company, Integer luxury, Integer price)
        {
            this.company = company;
            this.luxury = luxury;
            this.price = price;
        }

        @Override
        public String toString()
        {
            return "Luxury: " + luxury +
                    " Price: " + price;
        }

        @Override
        public int compareTo(LuxuryPriceGroup other)
        {
            if (luxury > other.luxury)
                return 1;
            if (luxury < other.luxury)
                return -1;
            if (price < other.price)
                return 1;
            if (price > other.price)
                return -1;
            return 0;
        }
    }


    LocalDate date;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numLostToNoCapacity = 0;
    Integer numSold = 0;
    Integer numLostToIdenticalOffer = 0;
    Integer numPlayerBougt = 0;
    Integer unusedCapacity = 0;
    List<Integer> toExpensive = new ArrayList();
    Map<Integer, Integer> toCheap = new TreeMap<>();
    List<Integer> qualityToBad = new ArrayList<>();


    public void addCustomerBudget(Integer budget)
    {
        if (!customerBudgets.containsKey(budget))
            customerBudgets.put(budget, 0);
        customerBudgets.put(budget, customerBudgets.get(budget) + 1);
        marketTotalDemand++;
    }

    public void addSupplierOffer(Company company, Integer price, Integer luxury)
    {
        LuxuryPriceGroup tmp = new LuxuryPriceGroup(company, luxury, price);
        if (!supplierOffers.containsKey(tmp))
            supplierOffers.put(tmp, 0);
        supplierOffers.put(tmp, supplierOffers.get(tmp) + 1);
    }


    public void calculateMarketAnalysis()
    {
        customersAtPrice = new TreeMap<>();
        revenueAtPrice = new TreeMap<>();

        Integer sumcustomers = marketTotalDemand;
        for (Map.Entry<Integer, Integer> budget : customerBudgets.entrySet())
        {
            Integer customerGroupProhibitivePrice = budget.getKey();
            customersAtPrice.put(customerGroupProhibitivePrice, sumcustomers);
            revenueAtPrice.put(customerGroupProhibitivePrice, sumcustomers * customerGroupProhibitivePrice);
            sumcustomers -= budget.getValue();
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
                        "\nBudget => NumCustomers: " + customerBudgets +
                        "\nCompOffer => NumCompanies: " + supplierOffers +
                        "\nAt Price => num Customer: " + customersAtPrice +
                        "\nAt price => Revenue: " + revenueAtPrice +
                        "\nPlayer: " + numPlayerBougt +
                        "\n"
                        );
        return stringBuilder.toString();
    }

}
