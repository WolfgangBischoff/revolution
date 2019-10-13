package Core;

import Core.Enums.IndustryType;

import java.time.LocalDate;
import java.util.*;

public class MarketanalysisDataStorage
{
    private final Integer PERIODS_REMEMBERED = 5;
    private Company owner;
    private List<MarketAnalysisData> dataContainer = new ArrayList<>(); //old
    private Map<IndustryType, List<MarketAnalysisData>> data = new TreeMap<>();

    //Constructor and Init
    public MarketanalysisDataStorage()
    {
        for (IndustryType industryType : IndustryType.values())
        {
            data.put(industryType, new ArrayList<MarketAnalysisData>());
        }
        owner = null;
    }

    public void initNewDay()
    {
        System.out.println("MarketAnalyisStorage InitNewDay");
        dataContainer.add(0, new MarketAnalysisData(Simulation.getSingleton().getDate()));

        for (IndustryType industryType : IndustryType.values())
        {
            data.get(industryType).add(0, new MarketAnalysisData(Simulation.getSingleton().getDate()));
            deleteOldData(data.get(industryType));
        }
    }

    //Calc
    public void addCustomerBudget(IndustryType type, Integer budget, Company bestCompany)
    {
        if (bestCompany != null)
            data.get(type).get(0).marketTotalSold++;
        data.get(type).get(0).addCustomerBudget(budget);
    }

    public void addSupplierOffer(Company company, Integer price, Integer luxury)
    {
        MarketAnalysisData marketAnalysisData = data.get(company.getIndustry()).get(0);
        marketAnalysisData.addSupplierOffer(company, price, luxury);
    }

    public void calculateMarketAnalysis()
    {
        for (IndustryType industryType : IndustryType.values())
        {
            List<MarketAnalysisData> industryData = data.get(industryType);
            //System.out.println("calculateMarketAnalysis() " + industryType);
            industryData.get(0).calculateMarketAnalysis();
        }





        /*

        LocalDate today = Simulation.getSingleton().getDate();
        MarketAnalysisData previousDay = getAnalysisData(today.minusDays(1));
        if (previousDay == null)
            return;

        //analyze optimal prize ceteris paribus
        List<Pair<Integer, Integer>> customersAtPrice = new ArrayList<>();
        List<Pair<Integer, Integer>> revenueAtPrice = new ArrayList<>();

        Integer sumcustomers = previousDay.marketTotalDemand;//previousDay.numSold;
        Integer priceLastPeriod = owner.getPrice();
        //Tree Map start with lowest key (CustomerRent = NumberPersons){-4=2, -2=5, 0=2, 5=1}
        for (Map.Entry<Integer, Integer> deltaPriceAndNumberPersons : previousDay.toCheap.entrySet())
        {
            Integer customerGroupProhibitivePrice = deltaPriceAndNumberPersons.getKey() + priceLastPeriod;
            customersAtPrice.add(new Pair<>(customerGroupProhibitivePrice, sumcustomers));
            revenueAtPrice.add(new Pair<>(customerGroupProhibitivePrice, sumcustomers * (priceLastPeriod + deltaPriceAndNumberPersons.getKey())));
            sumcustomers -= deltaPriceAndNumberPersons.getValue();
        }
        System.out.println("At Price => Customer" + customersAtPrice);
        System.out.println("At price => Revenue " + revenueAtPrice);
*/

        //analyze to expensive

    }

    /*
    public void addNewData(Company bestCompetitor, Integer customerBudget)
    {
        //Process Data right after market decision of customer
        MarketAnalysisData currentData = dataContainer.get(0);
        currentData.marketTotalDemand++;

        //DEMAND NOT MET
        //Affordable, but cannot produce
        if (bestCompetitor == null && !owner.canProduce() && owner.getPrice() <= customerBudget)
            currentData.numLostToNoCapacity++;
            //Not affordable, nobody else met demand
        else if (bestCompetitor == null)
        {
            //currentData.toExpensive.add(owner.getPrice() - customerBudget);
            Integer customerRent = customerBudget - owner.getPrice();
            if(!currentData.toCheap.containsKey(customerRent))
                currentData.toCheap.put(customerRent, 0);
            currentData.toCheap.put(customerRent, currentData.toCheap.get(customerRent)+ 1);
        }

        //SOLD
        //This company sold
        else if (bestCompetitor == owner)
        {
            currentData.marketTotalSold++;
            currentData.numSold++;
            Integer customerRent = customerBudget - owner.getPrice();
            if(!currentData.toCheap.containsKey(customerRent))
                currentData.toCheap.put(customerRent, 0);
            currentData.toCheap.put(customerRent, currentData.toCheap.get(customerRent)+ 1);
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
            {
                //currentData.toExpensive.add(owner.getPrice() - bestCompetitor.getPrice());
                Integer customerRent = bestCompetitor.getPrice() - owner.getPrice();
                if(!currentData.toCheap.containsKey(customerRent))
                    currentData.toCheap.put(customerRent, 0);
                currentData.toCheap.put(customerRent, currentData.toCheap.get(customerRent)+ 1);
            }

                //Not affordable, but competitor is
            else if (customerBudget < owner.getPrice())
            {
                //currentData.toExpensive.add(owner.getPrice() - customerBudget);
                Integer customerRent = customerBudget - owner.getPrice();
                if(!currentData.toCheap.containsKey(customerRent))
                    currentData.toCheap.put(customerRent, 0);
                currentData.toCheap.put(customerRent, currentData.toCheap.get(customerRent)+ 1);
            }

                //same offer as competitor but random
            else if (bestCompetitor.getLuxury().equals(owner.getLuxury()) && bestCompetitor.getPrice().equals(owner.getPrice()))
                currentData.numLostToIdenticalOffer++;

                //Affordable and better offer as competitor but no capacity
            else if (!owner.canProduce() && bestCompetitor.getLuxury() < owner.getLuxury() && owner.getPrice() <= customerBudget)
                currentData.numLostToNoCapacity++;
        }

    }*/

    private void deleteOldData()
    {
        if (dataContainer.size() > PERIODS_REMEMBERED)
            dataContainer.remove(dataContainer.size() - 1);
    }

    private void deleteOldData(List<MarketAnalysisData> dataContainer)
    {
        if (dataContainer.size() > PERIODS_REMEMBERED)
            dataContainer.remove(dataContainer.size() - 1);
    }

    /**
     * Variant if Player boughts goods.
     *
     * @param bestCompany company the Player bought
     */
    public void addNewDataPlayer(Company bestCompany)
    {
        MarketAnalysisData currentData = data.get(bestCompany.getIndustry()).get(0);
        currentData.marketTotalDemand++;
        currentData.marketTotalSold++;
        currentData.numPlayerBought++;
    }


    public String dataAnalysis()
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (data.isEmpty())
            stringBuilder.append("No Data");
        for (IndustryType industryType : IndustryType.values())
        {
            stringBuilder.append(dataAnalysis(industryType));
        }
        return stringBuilder.toString();
    }

    public String dataAnalysis(IndustryType industryType)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (data.isEmpty())
            stringBuilder.append("No Data");
        List<MarketAnalysisData> industry = data.get(industryType);
        stringBuilder.append(industryType + ":\n");

        for (MarketAnalysisData marketAnalysisData : industry)
            stringBuilder.append(marketAnalysisData);
        return stringBuilder.toString();
    }

    public MarketAnalysisData getAnalysisData(LocalDate date)
    {
        for (MarketAnalysisData data : dataContainer)
        {
            if (data.date.equals(date))
                return data;
        }
        return null;
    }

    public MarketAnalysisData getAnalysisData(IndustryType type, LocalDate date)
    {
        List<MarketAnalysisData> industryData = data.get(type);
        for (MarketAnalysisData data : industryData)
        {
            if (data.date.equals(date))
                return data;
        }
        return null;
    }

    public List<MarketAnalysisData> getAnalysisData(IndustryType type)
    {
        return data.get(type);
    }


}
