package Core;

public class EconomyStatistics extends Statistics
{
    Economy economy;

    public EconomyStatistics(Economy economy)
    {
        this.economy = economy;
        calcState();
    }

    void calcState()
    {
        //GRP
        //GINI
    }

    public Integer calcSumCompanyDeposits()
    {
        Integer sum = 0;
        for(Company company : economy.companies)
        {
            sum += company.getDeposit();
        }
        return sum;
    }

}
