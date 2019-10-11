package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Core.Util.*;

public class Company
{
    private String name;
    private Integer deposit;
    private IndustryType industry;
    private ArrayList<Workposition> workpositions = new ArrayList<>();
    private static final String COMPANYNAMESPATH = "./res/txt/names/companies/";
    private Integer luxury, price = -1, maxCapacity = MAX_CAPACITY_DEFAULT, usedCapacity = 0;
    private Integer baseCapacityCost = 1;
    private MarketanalysisDataStorage marketanalysisData = new MarketanalysisDataStorage(this);

    //Constructors
    public Company(String name)
    {
        this(name, COMP_DEFAULT_INDUSTRY, COMP_DEFAULT_DEPOSIT);
    }

    public Company(String name, IndustryType industry)
    {
        this(name, industry, COMP_DEFAULT_DEPOSIT);
    }

    public Company(String name, IndustryType industry, Integer Initdeposit)
    {
        this.name = name;
        deposit = Initdeposit;
        this.industry = industry;

        Market.getMarket().addCompany(this);
    }

    public Company(IndustryType type, Integer price, Integer luxury)
    {
        this(Economy.getEconomy().createUniqueCompanyName(getRandomCompanyName(type)), type);
        this.price = price;
        this.luxury = luxury;
        addDefaultWorkplaces();
    }

    public Company(String name, Integer base, Integer app, Integer high, Integer univ)
    {
        this(name, COMP_DEFAULT_INDUSTRY, base, app, high, univ);
    }

    public Company(String name, IndustryType industry, Integer base, Integer app, Integer high, Integer univ)
    {
        this(name, industry);
        for (int i = 0; i < base; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_BASE));
        for (int i = 0; i < app; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_APPRENTICESHIP));
        for (int i = 0; i < high; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_HIGHER));
        for (int i = 0; i < univ; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_UNIVERSITY));
    }

    //Calculations
    public void initPeriod()
    {
        usedCapacity = 0;
        marketanalysisData.initNewDay();
        doMarketDecisions();
    }

    public void doMarketDecisions()
    {
        LocalDate today = Simulation.getSingleton().getDate();
        MarketAnalysisData previousDay = marketanalysisData.getAnalysisData(today.minusDays(1));
        if (previousDay == null)
            return;

        //analyze to cheap
        List<Pair<Integer, Integer>> customersAtPrice = new ArrayList<>();
        List<Pair<Integer, Integer>> revenueAtPrice = new ArrayList<>();

        Integer sumcustomers = previousDay.numSold;

        for (Map.Entry<Integer, Integer> customerRent : previousDay.toCheap.entrySet())
        {
            revenueAtPrice.add(new Pair<>(customerRent.getKey() + price, sumcustomers * (price + customerRent.getKey())));
            customersAtPrice.add(new Pair<>(customerRent.getKey() + price, sumcustomers));
            sumcustomers -= customerRent.getValue();
        }
        System.out.println("At Price => Customer" + customersAtPrice);
        System.out.println("At price => Revenue " + revenueAtPrice);
    }

    private void calcPrice()
    {
        price = 5;
    }

    private void calcLuxury()
    {
        luxury = 3;
    }

    private void calcCapacity()
    {

    }

    public Integer calcProductionEffort()
    {
        return luxury + baseCapacityCost;
    }

    public void produce()
    {
        usedCapacity += calcProductionEffort();
    }

    public boolean canProduce()
    {
        Integer currentCap = usedCapacity + calcProductionEffort();
        return currentCap <= maxCapacity;
    }

    public Integer calcUnusedCapacity()
    {
        return maxCapacity - usedCapacity;
    }

    private void addDefaultWorkplaces()
    {
        for (int i = 0; i < NUM_BASE_EDU_WORKPLACES; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_BASE));
        for (int i = 0; i < NUM_APPR_EDU_WORKPLACES; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_APPRENTICESHIP));
        for (int i = 0; i < NUM_HIGH_EDU_WORKPLACES; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_HIGHER));
        for (int i = 0; i < NUM_UNIV_EDU_WORKPLACES; i++)
            workpositions.add(new Workposition(this, EducationalLayer.EDU_UNIVERSITY));
    }

    public void getPaid(Integer amount)
    {
        deposit += amount;
    }


    void paySalaries()
    {
        for (Workposition workposition : workpositions)
            if (workposition.worker != null)
                paySalary(workposition);
    }

    private void paySalary(Workposition workposition)
    {
        //In case the worker works not the whole month
        double ratioWorkedDaysOfMonth = 1;
        LocalDate today = Simulation.getSingleton().getDate();
        //Period period = Period.between(workposition.hasWorkerSince, Simulation.getSingleton().getDate());
        //System.out.println("Period: " + period);
        //Integer daysElapsed = period.getDays() + 1; //First day counts
        Long daysElapsed = ChronoUnit.DAYS.between(workposition.hasWorkerSince, today);
        //System.out.println("Days Elapsed: " + daysElapsed + " "+ workposition.hasWorkerSince +" "+ today);
        if (daysElapsed < today.getMonth().length(today.isLeapYear()))
            ratioWorkedDaysOfMonth = (double) daysElapsed / today.getMonth().length(today.isLeapYear());

        // System.out.println("Comapny Ratio Worked: " + ratioWorkedDaysOfMonth);
        int gross = (int) (workposition.grossIncomeWork * ratioWorkedDaysOfMonth);
        int tax = Government.CalcIncomeTax(gross);
        int nett = (gross - tax);

        workposition.worker.receiveSalary(nett);
        Government.getGoverment().raiseIncomeTax(tax);
        deposit -= gross;
    }

    public Integer calcNumberFreeWorkpositions()
    {
        Integer returnSum = 0;
        for (Workposition workposition : workpositions)
            if (workposition.worker == null)
                returnSum++;
        return returnSum;
    }

    public boolean employWorker(Workposition workposition, Person p)
    {
        if (workposition.isWorkerAppropriate(p))
        {
            workposition.setWorker(p); //workposition.worker = p;
            p.startAtWorkposition(workposition);
            return true;
        }
        else
            return false;
    }

    void unemployAllWorkers()
    {
        for (Workposition workpositions : workpositions)
            unemployWorker(workpositions);
    }

    public void employeeQuitted(Workposition workposition)
    {
        workposition.setWorker(null);//workposition.worker = null;
    }

    Integer calcNumberWorkers()
    {
        Integer sum = 0;
        for (Workposition wp : workpositions)
            if (wp.worker != null)
                sum++;
        return sum;
    }

    boolean unemployWorker(Workposition workposition)
    {
        if (workposition.worker != null)
        {
            workposition.worker.getUnemployedAtWorkposition();
            workposition.setWorker(null);//workposition.worker = null;
            return true;
        }
        else
            return false;
    }

    static String getRandomCompanyName(IndustryType type)
    {
        String[] names = null;
        switch (type)
        {
            case FOOD:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesFood.csv");
                break;
            case CLOTHS:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesCloths.csv");
                break;
            case SPARETIME:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesSparetime.csv");
                break;
            case EDUCATION:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesEducation.csv");
                break;
            case TRAFFIC:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesTraffic.csv");
                break;
            case HOUSING:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesHousing.csv");
                break;
            case HEALTH:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesHealth.csv");
                break;
            case ENERGY:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesEnergy.csv");
                break;
            case ELECTRONICS:
                names = Util.readFirstLineFromTxt(COMPANYNAMESPATH + "companyNamesElectronics.csv");
                break;
        }

        return names[Util.getRandom().nextInt(names.length)];
    }


    //Prints
    @Override
    public String toString()
    {
        return "\nCompany{" +
                "name='" + name + '\'' +
                ", workpositions=" + workpositions +
                '}';
    }

    public String baseData()
    {
        return "Name: " + name + " Workers: " + calcNumberWorkers() + " Deposit: " + deposit + " Price: " + price + " Luxury: " + luxury;
    }

    //Getter and Setter
    public String getName()
    {
        return name;
    }

    public Integer getDeposit()
    {
        return deposit;
    }

    public ArrayList<Workposition> getWorkpositions()
    {
        return workpositions;
    }

    public ArrayList<Workposition> getFreeWorkpositions()
    {
        ArrayList freePos = new ArrayList();
        for (Workposition workposition : workpositions)
            if (workposition.worker == null)
                freePos.add(workposition);
        return freePos;
    }

    public Integer getNumberEmployees()
    {
        return calcNumberWorkers();
    }

    public IndustryType getIndustry()
    {
        return industry;
    }

    public Integer getLuxury()
    {
        return luxury;
    }

    public Integer getPrice()
    {
        if (price == null)
        {
            System.out.println("NULL: " + this);
        }
        return price;
    }

    public Integer getUsedCapacity()
    {
        return usedCapacity;
    }

    public MarketanalysisDataStorage getMarketanalysisData()
    {
        return marketanalysisData;
    }

    public Integer getMaxCapacity()
    {
        return maxCapacity;
    }
}
