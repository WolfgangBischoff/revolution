package Core;

import Core.Enums.SpecialDayOfYear;
import javafx.animation.AnimationTimer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class Simulation
{
    private static Simulation singleton;
    private Society society;
    private Economy economy;
    private Government government;
    private Market market;
    private Console console;
    private GameCalendar calender;
    private Player player;
    private PlayerCompany playerCompany;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROPERTY_DATE = "date";
    public AnimationTimer animationTimer;

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

    public PlayerCompany getPlayerCompany()
    {
        return playerCompany;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void setPlayerCompany(PlayerCompany playerCompany)
    {
        this.playerCompany = playerCompany;
    }

    public void nextPeriod()
    {
        String lastDay = calender.dataDateWeekday();
        calender.nextDay();
        propertyChangeSupport.firePropertyChange(PROPERTY_DATE, lastDay, calender.dataDateWeekday());
        economy.initPeriod();
        market.initPeriod();

        //Day trigger
        System.out.println("-----------------------------------");
        System.out.println("-----" + calender.dataDateWeekday() + "-----");
        System.out.println("-----------------------------------");
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
        //System.out.println("Simulation SocietyShop()");
        society.shop();


        //Not sure if need anymore
        society.calcSocietyDaily();
        //System.out.println("Simulation economy.calcState()");
        economy.calcState();
        //System.out.println("Simulation market.collectMarketData()");
        market.collectMarketData();
    }

    private Simulation()
    {
        society = Society.getSociety();
        economy = Economy.getEconomy();
        government = Government.getGoverment();
        market = Market.getMarket();
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
        //System.out.println("Simulation added Listener: " + listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
