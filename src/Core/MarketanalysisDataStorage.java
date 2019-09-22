package Core;

import Core.Enums.IndustryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.Enums.IndustryType.FOOD;
import static Core.Enums.IndustryType.getEnumSize;

class MarketanalysisData
{
    Integer date;
    //Company specific
    Map<Company, CompanySpecificMarketData> comanySpecificData = new HashMap<>();

    public MarketanalysisData(Integer date)
    {
        this.date = date;
    }

    public void noProductsLeft(Company company)
    {
        if (!comanySpecificData.containsKey(company))
            comanySpecificData.put(company, new CompanySpecificMarketData());
        comanySpecificData.get(company).numNoProductsLeft++;

    }

    public void toExpensive(Company company, Integer amount)
    {
        if (!comanySpecificData.containsKey(company))
            comanySpecificData.put(company, new CompanySpecificMarketData());
        comanySpecificData.get(company).toExpensive.add(amount);
    }

    public void toCheap(Company company, Integer amount)
    {
        if (!comanySpecificData.containsKey(company))
            comanySpecificData.put(company, new CompanySpecificMarketData());
        comanySpecificData.get(company).toCheap.add(amount);
    }

    public void toBadQuality(Company company, Integer amount)
    {
        if (!comanySpecificData.containsKey(company))
            comanySpecificData.put(company, new CompanySpecificMarketData());
        comanySpecificData.get(company).qualityToBad.add(amount);
    }

    public void sold(Company company)
    {
        comanySpecificData.get(company).numSold++;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: " + date);
        for(Map.Entry<Company, CompanySpecificMarketData> companyData : comanySpecificData.entrySet())
        {
            stringBuilder.append("\n\t" + companyData.getKey().baseData());
            stringBuilder.append("\n\t" + companyData.getValue());
        }
        return stringBuilder.toString();
    }

    class CompanySpecificMarketData
    {
        Integer numNoProductsLeft = 0;
        Integer numSold = 0;
        List<Integer> toExpensive = new ArrayList();
        List<Integer> toCheap = new ArrayList<>();
        List<Integer> qualityToBad = new ArrayList<>();

        @Override
        public String toString()
        {
            return "Sold: " + numSold + "\n\tTo Expensive: " + toExpensive.toString() + "\n\tTo Cheap: " + toCheap.toString() +
                    "\n\tQuality: " + qualityToBad.toString() + "\n\tNo capacity: " + numNoProductsLeft + "\n";
        }
    }

}

public class MarketanalysisDataStorage
{
    Market market;
    List<Map<IndustryType, MarketanalysisData>> dataStorage = new ArrayList<>(); //Foreach day and several industries
    Integer PERIODS_REMEMBERED = 5;


    public MarketanalysisDataStorage(Market market)
    {
        this.market = market;
    }

    public void personNoProductsLeft(IndustryType type, Company company)
    {
        Integer today = 0;
        MarketanalysisData data = dataStorage.get(today).get(type);
        data.noProductsLeft(company);
    }

    public void personCannotAffort(IndustryType type, Company company, Integer amount)
    {
        Integer today = 0;
        MarketanalysisData data = dataStorage.get(today).get(type);
        data.toExpensive(company, amount);
    }

    public void tooFewLuxury(IndustryType type, Company company, Integer amount)
    {
        Integer today = 0;
        MarketanalysisData data = dataStorage.get(today).get(type);
        data.toBadQuality(company, amount);
    }

    public void tooExpensive(IndustryType type, Company company, Integer amount)
    {
        Integer today = 0;
        MarketanalysisData data = dataStorage.get(today).get(type);
        data.toExpensive(company, amount); //same as person cannot afford
    }

    public void tooCheap(IndustryType type, Company company, Integer amount)
    {
        Integer today = 0;
        MarketanalysisData data = dataStorage.get(today).get(type);
        data.toCheap(company, amount); //same as person cannot afford
    }

    public void sold(IndustryType type, Company company)
    {
        Integer today = 0;
        MarketanalysisData data = dataStorage.get(today).get(type);
        data.sold(company); //same as person cannot afford
    }

    public void initNewDay()
    {
        Integer todayDate = 0;

        HashMap<IndustryType, MarketanalysisData> newData = new HashMap<>();
        newData.put(FOOD, new MarketanalysisData(todayDate));

        if (dataStorage.isEmpty())
            dataStorage.add(0, newData); //For current period
        else
            dataStorage.add(0, newData);

        deleteOldData();
    }

    private void deleteOldData()
    {
        if (dataStorage.size() > PERIODS_REMEMBERED)
            dataStorage.remove(dataStorage.size() - 1);
    }

    @Override
    public String toString()
    {
        return dataStorage.toString();
    }

    public String dataAnalysis()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---- Analysis Data ----");
        for(int i=0; i<dataStorage.size(); i++)
            stringBuilder.append(dataAnalysis(i));
        return stringBuilder.toString();
    }

    public String dataAnalysis(Integer date)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Map<IndustryType, MarketanalysisData> marketanalysisData = dataStorage.get(date);
        for(Map.Entry<IndustryType, MarketanalysisData> entry : marketanalysisData.entrySet())
            stringBuilder.append("\nIndustry: " + entry.getKey() + "\n" + entry.getValue());
        return stringBuilder.toString();
    }
}
