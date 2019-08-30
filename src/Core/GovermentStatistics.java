package Core;

public class GovermentStatistics
{
    private static Government goverment;


    public GovermentStatistics(Government government)
    {
        this.goverment = government;
    }

    public String print()
    {
        return "Balance: " + goverment.getDeposit() + " ruled by " + goverment.getRulingParty();
    }
}
