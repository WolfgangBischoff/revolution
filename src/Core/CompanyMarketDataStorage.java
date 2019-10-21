package Core;

import Core.Enums.IndustryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompanyMarketDataStorage
{
    private final int PERIODS_REMEMBERED = 5;
    List<CompanyMarketData> dataContainer = new ArrayList<>();
    Company owner;

    public CompanyMarketDataStorage(Company company)
    {
        owner = company;
    }

    public void initNewDay()
    {
        dataContainer.add(0, new CompanyMarketData(owner));
        deleteOldData();
    }

    public void addSellData()
    {
        dataContainer.get(0).addSellData();
    }

    public void addSellDataPlayer()
    {
        if(dataContainer.isEmpty())
            initNewDay();
        dataContainer.get(0).addSellDataPlayer();
    }

    private void deleteOldData()
    {
        if (dataContainer.size() > PERIODS_REMEMBERED)
            dataContainer.remove(dataContainer.size() - 1);
    }

    public CompanyMarketData getAnalysisData(LocalDate date)
    {
        for (CompanyMarketData data : dataContainer)
        {
            if (data.date.equals(date))
                return data;
        }
        return null;
    }
}
