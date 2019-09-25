package Core;

import java.util.ArrayList;
import java.util.List;


public class MarketAnalysisData
{
    Integer date;
    Integer marketTotalDemand = 0;
    Integer marketTotalSold = 0;
    Integer numLostToNoCapacity = 0;
    Integer numSold = 0;
    Integer numLostToIdenticalOffer = 0;
    List<Integer> toExpensive = new ArrayList();
    List<Integer> toCheap = new ArrayList<>();
    List<Integer> qualityToBad = new ArrayList<>();

    public MarketAnalysisData(Integer date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Sold: " + numSold + "\nTotal Sold: " + marketTotalSold +"\nTotal Demand: " + marketTotalDemand + "\n\tTo Expensive: " + toExpensive.toString() + "\n\tTo Cheap: " + toCheap.toString() +
                "\n\tQuality: " + qualityToBad.toString() + "\n\tSame offer: " + numLostToIdenticalOffer + "\n\tNo capacity: " + numLostToNoCapacity + "\n";
    }

}
