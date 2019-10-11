package Core;

import java.time.LocalDate;
import java.util.*;

public class MarketAnalysisData
{
    Map<Integer, Integer> customerBudgets = new TreeMap<>();
    Map<LuxuryPriceGroup, Integer> supplierOffers = new TreeMap<>();

    class LuxuryPriceGroup implements Comparable<LuxuryPriceGroup>
    {
        Integer luxury, price;

        public LuxuryPriceGroup(Integer luxury, Integer price)
        {
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
    }

    public void addSupplierOffer(Integer price, Integer luxury)
    {
        LuxuryPriceGroup tmp = new LuxuryPriceGroup(luxury, price);
        if (!supplierOffers.containsKey(tmp))
            supplierOffers.put(tmp, 0);
        supplierOffers.put(tmp, supplierOffers.get(tmp) + 1);
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
        if (marketTotalDemand == 0)
        {
            stringBuilder.append("No market Demand\n");
        }
        else
        {
            stringBuilder.append("Sold: " + numSold +
                    "\nTotal Sold: " + marketTotalSold +
                    "\nTotal Demand: " + marketTotalDemand +
                    "\nCustomer budget Analysis: " + customerBudgets +
                    "\nSupplier offer Analysis: "+ supplierOffers +
                    "\n\tSame offer: " + numLostToIdenticalOffer +
                    "\n\tPlayer: " + numPlayerBougt +
                    "\n\tNo capacity: " + numLostToNoCapacity +
                    "\n\tUnused Capacity: " + unusedCapacity + "\n");
            /*
            stringBuilder.append("Sold: " + numSold +
                    "\nTotal Sold: " + marketTotalSold +
                    "\nTotal Demand: " + marketTotalDemand +
                    "\n\tTo Expensive: " + toExpensive.toString() +
                    "\n\tTo Cheap: (CustomerRent = NumberPersons)" + toCheap.toString() +
                    "\n\tQuality: " + qualityToBad.toString() +
                    "\n\tSame offer: " + numLostToIdenticalOffer +
                    "\n\tPlayer: " + numPlayerBougt +
                    "\n\tNo capacity: " + numLostToNoCapacity +
                    "\n\tUnused Capacity: " + unusedCapacity + "\n");
                    */
        }
        return stringBuilder.toString();
    }

}
