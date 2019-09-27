package Core;

import java.util.ArrayList;
import java.util.List;

public class MarketanalysisDataStorage
{
    private final Integer PERIODS_REMEMBERED = 5;
    private Company owner;
    private List<MarketAnalysisData> dataContainer = new ArrayList<>();

    public MarketanalysisDataStorage(Company company)
    {
        owner = company;
    }

    private void initNewDay()
    {
        GameDate todaysDate = Simulation.getSingleton().getCurrentDay();
        dataContainer.add(0, new MarketAnalysisData(todaysDate));
        dataContainer.get(0).unusedCapacity = owner.getMaxCapacity();
        deleteOldData();
    }

    private void deleteOldData()
    {
        if (dataContainer.size() > PERIODS_REMEMBERED)
            dataContainer.remove(dataContainer.size() - 1);
    }

    public void addNewData(Company bestCompetitor, Integer customerBudget)
    {
        //Is New Day Data
        GameDate todaysDate = Simulation.getSingleton().getCurrentDay();
        if (dataContainer.isEmpty() || !(dataContainer.get(0).collectionDate.equals(todaysDate)))
        {
            initNewDay();
        }
        MarketAnalysisData currentData = dataContainer.get(0);

        //Process Data right after market decision
        currentData.marketTotalDemand++;

        //DEMAND NOT MET
        //Affordable, but nobody can produce
        if (bestCompetitor == null && !owner.canProduce() && owner.getPrice() <= customerBudget)
            currentData.numLostToNoCapacity++;
            //Not affordable, nobody else met demand
        else if (bestCompetitor == null)
        {
            currentData.toExpensive.add(owner.getPrice() - customerBudget);
        }

        //SOLD
        //This company sold
        else if (bestCompetitor == owner)
        {
            currentData.marketTotalSold++;
            currentData.numSold++;
            currentData.toCheap.add(customerBudget - owner.getPrice());
            currentData.unusedCapacity -= owner.calcProductionEffort();
        }
        //OTHER COMPANY SOLD
        else
        {
            currentData.marketTotalSold++;
            //Competitor more luxury
            if (bestCompetitor.getLuxury() > owner.getLuxury())
                currentData.qualityToBad.add(bestCompetitor.getLuxury() - owner.getLuxury());

                //Competitor same luxury but cheaper
            else if (bestCompetitor.getLuxury().equals(owner.getLuxury()) && bestCompetitor.getPrice() < owner.getPrice())
                currentData.toExpensive.add(owner.getPrice() - bestCompetitor.getPrice());

                //Not affordable, but competitor is
            else if (customerBudget < owner.getPrice())
                currentData.toExpensive.add(owner.getPrice() - customerBudget);

                //same offer as competitor but random
            else if (bestCompetitor.getLuxury().equals(owner.getLuxury()) && bestCompetitor.getPrice().equals(owner.getPrice()))
                currentData.numLostToIdenticalOffer++;

                //Affordable and better offer as competitor but no capacity
            else if (!owner.canProduce() && bestCompetitor.getLuxury() < owner.getLuxury() && owner.getPrice() <= customerBudget)
                currentData.numLostToNoCapacity++;
        }

    }

    //Prints
    public String dataAnalysis()
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (dataContainer.isEmpty())
            stringBuilder.append("No Data");
        for (int i = 0; i < dataContainer.size(); i++)
            stringBuilder.append(dataAnalysis(i));
        return stringBuilder.toString();
    }

    public String dataAnalysis(Integer date)
    {
        StringBuilder stringBuilder = new StringBuilder();
        MarketAnalysisData marketanalysisData = dataContainer.get(date);
        stringBuilder.append(marketanalysisData);
        return stringBuilder.toString();
    }
}
