package Core;

import java.time.LocalDate;
import java.util.*;

public class MarketAnalysisData
{
    LocalDate date;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numLostToNoCapacity = 0;
    Integer numSold = 0;
    Integer numLostToIdenticalOffer = 0;
    Integer numPlayerBougt = 0;
    Integer unusedCapacity = 0;
    List<Integer> toExpensive = new ArrayList();
    //List<Integer> toCheap = new ArrayList<>();
    Map<Integer, Integer> toCheap = new TreeMap<>();
    List<Integer> qualityToBad = new ArrayList<>();

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
                    "\n\tTo Expensive: " + toExpensive.toString() +
                    "\n\tTo Cheap: (CustomerRent = NumberPersons)" + toCheap.toString() +
                    "\n\tQuality: " + qualityToBad.toString() +
                    "\n\tSame offer: " + numLostToIdenticalOffer +
                    "\n\tPlayer: " + numPlayerBougt +
                    "\n\tNo capacity: " + numLostToNoCapacity +
                    "\n\tUnused Capacity: " + unusedCapacity + "\n");
        }
        return stringBuilder.toString();
    }

}
