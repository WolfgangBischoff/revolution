package Core;

public class Government
{
    private static Government singleton = null;
    private static GovermentStatistics govermentStatistics = null;
    private Integer deposit = 0;
    private PoliticalOpinion rulingParty = PoliticalOpinion.Unpolitical;
    private static Integer INCOME_TAX_RATE = 30;

    private Government()
    {
        govermentStatistics = new GovermentStatistics(this);
    }


    public Integer getDeposit() {
        return deposit;
    }

    public PoliticalOpinion getRulingParty() {
        return rulingParty;
    }


   public static Government getGoverment()
    {
        if(singleton == null)
            singleton = new Government();
        return singleton;
    }

    @Override
    public String toString() {
        return "Goverment: " + govermentStatistics.print();
    }

    public static Integer CalcIncomeTax(Integer base)
    {
        return (base * INCOME_TAX_RATE) / 100;
    }

    public void raiseIncomeTax(Integer amount)
    {
        deposit += amount;
    }
}
