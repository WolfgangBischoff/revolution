package Core;

public class Simulation
{
    private static Simulation singleton;
    private Society society;
    private Economy economy;
    private Government government;
    private Console console;

    public Society getSociety()
    {
        return society;
    }

    public Economy getEconomy()
    {
        return economy;
    }

    public Government getGovernment()
    {
        return government;
    }

    private Simulation()
    {
        society = Society.getSociety();
        economy = new Economy();
        government = Government.getGoverment();
        console = new Console(this);
    }

    public static Simulation getSingleton()
    {
        if(singleton == null)
        {
            singleton = new Simulation();
        }
        return singleton;
    }

    public Console getConsole()
    {
        return console;
    }
}
