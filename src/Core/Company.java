package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Integer luxury, price = -1, maxCapacity = 100, capacity = 0;
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
    public void doMarketDecisions()
    {

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

    public void produce()
    {
        capacity += (luxury + baseCapacityCost);
    }

    public boolean canProduce()
    {
        Integer currentCap = capacity + luxury + baseCapacityCost;
        //System.out.println("CAPACITY " + currentCap + " max " + maxCapacity);
        return  currentCap <= maxCapacity;
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
        workposition.worker.receiveSalary(workposition.netIncomeWork);
        Government.getGoverment().raiseIncomeTax(workposition.incomeTaxWork);
        deposit -= workposition.grossIncomeWork;
    }

    public Integer calcNumberFreeWorkpositions()
    {
        Integer returnSum = 0;
        for (Workposition workposition : workpositions)
            if (workposition.worker == null)
                returnSum++;
        return returnSum;
    }

    boolean employWorker(Workposition workposition, Person p)
    {
        if (workposition.isWorkerAppropriate(p))
        {
            workposition.worker = p;
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
        workposition.worker = null;
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
            workposition.worker = null;
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
        if(price == null)
        {
            System.out.println("NULL: " + this);
        }
        return price;
    }

    public Integer getCapacity()
    {
        return capacity;
    }

    public MarketanalysisDataStorage getMarketanalysisData()
    {
        return marketanalysisData;
    }
}
