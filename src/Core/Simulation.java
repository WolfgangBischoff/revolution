package Core;

import Core.Enums.SpecialDayOfYear;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class Simulation
{
    private static Simulation singleton;
    private Society society;
    private Economy economy;
    private Government government;
    private Console console;
    private GameCalendar calender;
    private Player player;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROPERTY_DATE = "date";

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

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void nextPeriod()
    {
        String lastDay = calender.dataDateWeekday();
        calender.nextDay();
        propertyChangeSupport.firePropertyChange(PROPERTY_DATE, lastDay, calender.dataDateWeekday());
        economy.initPeriod();
        //TODO market.init, analysis previos day

        //Day trigger
        System.out.println(calender.dataDateWeekday());
        //End of Month
        if (calender.date.getDayOfMonth() == calender.date.lengthOfMonth())
        {
            System.out.println("Companies payed");
            economy.companiesPaySalary();
        }
        //First of Month
        else if (calender.date.getDayOfMonth() == 1)
        {
            System.out.println("Budgets calculated");
            society.calcSocietyMonthly();
        }

        //Special Day
        SpecialDayOfYear special = calender.checkYearlySpecialDay(calender.date);
        if(special != SpecialDayOfYear.WORKDAY)
            System.out.println("Its: " + special);

        //Daily activities
        //TODO Eco react
        System.out.println("Simulation SocietyShop()");
        society.shop();


        //Not sure if need anymore
        society.calcSocietyDaily();
        System.out.println("Simulation economy.calcState()");
        economy.calcState();
        //TODO Market collect data
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

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println("Simulation added Listener: " + listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
