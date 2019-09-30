package Core;

import Core.Enums.EducationalLayer;

import java.time.LocalDate;

public class Simulation
{
    private static Simulation singleton;
    private Society society;
    private Economy economy;
    private Government government;
    private Console console;
    private GameCalendar calender;
    private Person player;

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
    public LocalDate getDate()
    {
        return calender.getDate();
    }
    public GameCalendar getCalender()
    {
        return calender;
    }
    public Person getPlayer()
    {
        return player;
    }

    public void setPlayer(Person player)
    {
        this.player = player;
    }

    public void nextPeriod()
    {
        calender.nextDay();
        society.calcSociety();
        economy.calc();
    }

    private Simulation()
    {
        society = Society.getSociety();
        economy = Economy.getEconomy();
        government = Government.getGoverment();
        console = new Console(this);
        calender = new GameCalendar();
    }

    public static Simulation getSingleton()
    {
        if (singleton == null)
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
