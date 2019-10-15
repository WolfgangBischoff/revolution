package Core;

import javafx.print.Collation;

import java.time.LocalDate;
import java.util.*;

public class MarketAnalysisData
{
    LocalDate date;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numPlayerBought = 0;
    Map<Integer, Integer> customerBudgets = new TreeMap<>();
    List<LuxuryPriceGroup> supplierOffers = new ArrayList<>();
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
                return -1;
            if (luxury < other.luxury)
                return 1;
            if (price < other.price)
                return -1;
            if (price > other.price)
                return 1;
            if (company.getName().hashCode() < other.company.getName().hashCode())
                return -1;
            if (company.getName().hashCode() > other.company.getName().hashCode())
                return 1;
            return 0;
        }
    }





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
        //if (!supplierOffers.containsKey(tmp))
         //   supplierOffers.put(tmp, 0);
        //supplierOffers.put(tmp, supplierOffers.get(tmp) + 1);
        supplierOffers.add(tmp);
        Collections.sort(supplierOffers);
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
                        "\nPlayer: " + numPlayerBought +
                        "\n"
        );
        return stringBuilder.toString();
    }

}
