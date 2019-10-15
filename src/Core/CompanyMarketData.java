package Core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompanyMarketData
{
    LocalDate date;
    Integer revenue = 0;
    Integer numLostToNoCapacity = 0;
    Integer numSold = 0;
    //Integer numLostToIdenticalOffer = 0;
    Integer numPlayerBought = 0;
    Integer usedCapacity = 0;
    Integer unusedCapacity = 0;
    //List<Integer> toExpensive = new ArrayList();
    //Map<Integer, Integer> toCheap = new TreeMap<>();
    //List<Integer> qualityToBad = new ArrayList<>();
    Company owner;

    public CompanyMarketData(Company owner)
    {
        this.owner = owner;
        this.date = Simulation.getSingleton().getDate();
    }

    public void addSellData()
    {
        numSold++;
        revenue += owner.getPrice();
        usedCapacity += owner.calcProductionEffort();
    }

    public void addSellDataPlayer()
    {
        addSellData();
        numPlayerBought++;
    }

    public String dataCompanyMarketData()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" + date);
        stringBuilder.append(" Num Sold: " + numSold);
        stringBuilder.append(" Revenue: " + revenue);
        stringBuilder.append(" Used Capacity: " + usedCapacity);
        stringBuilder.append(" Player: " + numPlayerBought);
        return stringBuilder.toString();
    }

}
