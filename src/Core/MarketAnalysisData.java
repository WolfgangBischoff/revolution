package Core;

import java.util.ArrayList;
import java.util.List;


public class MarketAnalysisData
{
    Integer date;
    Integer marketTotalDemand = 0, marketTotalSold = 0;
    Integer numNoProductsLeft = 0;
    Integer numSold = 0;
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
        return "Sold: " + numSold + "\n\tTo Expensive: " + toExpensive.toString() + "\n\tTo Cheap: " + toCheap.toString() +
                "\n\tQuality: " + qualityToBad.toString() + "\n\tNo capacity: " + numNoProductsLeft + "\n";
    }

}
