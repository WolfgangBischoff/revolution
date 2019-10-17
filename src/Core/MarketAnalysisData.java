package Core;

import java.time.LocalDate;
import java.util.*;

public class MarketAnalysisData
{
    LocalDate date;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numPlayerBought = 0;
    Map<Integer, Integer> numCustomerPerBudget = new TreeMap<>();
    Map<Integer, Integer> maxRevenueCustomerGroup = new TreeMap<>();
    Map<Integer, List<LuxuryPriceGroup>> offersPerCustomerGroup = new TreeMap<>();
    List<LuxuryPriceGroup> supplierOffers = new ArrayList<>();
    Map<Integer, Integer> maxCustomersAtPrice = new TreeMap<>();
    Map<Integer, Integer> maxRevenueAtPrice = new TreeMap<>();

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

}
