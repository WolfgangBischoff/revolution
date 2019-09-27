package Core;

import java.util.ArrayList;
import java.util.List;

public class MarketAnalysisData
{
    Integer date;
    GameDate collectionDate;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numLostToNoCapacity = 0;
    Integer numSold = 0;
    Integer numLostToIdenticalOffer = 0;
    Integer unusedCapacity = 0;
    List<Integer> toExpensive = new ArrayList();
    List<Integer> toCheap = new ArrayList<>();
    List<Integer> qualityToBad = new ArrayList<>();

    public MarketAnalysisData(GameDate today)
    {
        collectionDate = today;
    }

    @Override
    public String toString()
    {
        return collectionDate.dataDate() + "\nSold: " + numSold + "\nTotal Sold: " + marketTotalSold +"\nTotal Demand: " + marketTotalDemand + "\n\tTo Expensive: " + toExpensive.toString() + "\n\tTo Cheap: " + toCheap.toString() +
                "\n\tQuality: " + qualityToBad.toString() + "\n\tSame offer: " + numLostToIdenticalOffer + "\n\tNo capacity: " + numLostToNoCapacity + "\n\tUnused Capacity: " + unusedCapacity + "\n";
    }

}
