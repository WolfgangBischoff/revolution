package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.Util.*;

public class Company implements ProductOwner
{
    private String name;
    private Integer deposit;
    private IndustryType industry;
    private ArrayList<Workposition> workpositions = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private static final String COMPANYNAMESPATH = "./res/txt/names/companies/";
    private static final String PRODUCTNAMESPATH = "./res/txt/names/products/";
    private Map<Integer, String[]> productNamesPerSize = new HashMap<>();
    private Double marginPercent = 0.1;

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
        List<String[]> ss = Util.readAllLineFromTxt((PRODUCTNAMESPATH + "productNamesFood.csv"));
        for(String[] s : ss)
        {
            //System.out.println(s[0]);
        }
        //productNamesPerSize;
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
    private void produceProduct(Integer utilityUnits)
    {
        addProduct(new Product(name + "s product", this, this, industry, utilityUnits));
        Product.transfer(this, Market.getMarket(), products.get(0));
    }

    private Double calcProductionEffectivity()
    {
        return (double) calcNumberWorkers() / workpositions.size();
    }

    protected void produce()
    {
        int usedCapacity = 0;
        int capacity = COMPANY_UTIL_CAPACITY_DEFAULT;
        float ratioLarge = 0.5f;
        float ratioMedium = 0.2f;
        int effectiveCapacity = (int) (capacity * calcProductionEffectivity());

        int numberLarge = (int) ((ratioLarge * effectiveCapacity) / UTILITY_LARGE);
        int numberMedium = (int) ((ratioMedium * effectiveCapacity) / UTILITY_MEDIUM);

        for (int i = 0; i < numberLarge; i++)
        {
            produceProduct(UTILITY_LARGE);
            usedCapacity += UTILITY_LARGE;
        }
        for (int i = 0; i < numberMedium; i++)
        {
            produceProduct(UTILITY_MEDIUM);
            usedCapacity += UTILITY_MEDIUM;
        }
        for (; usedCapacity < effectiveCapacity; )
        {
            produceProduct(UTILITY_SMALL);
            usedCapacity += UTILITY_SMALL;
        }
    }

    @Override
    public void addProduct(Product product)
    {
        products.add(product);
    }

    @Override
    public void removeProduct(Product product)
    {
        products.remove(product);
    }

    @Override
    public void getPaid(Integer amount)
    {
        deposit += amount;
    }

    @Override
    public void pay(Product price)
    {

    }

    @Override
    public void pay(List<Product> product)
    {

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
        return "Name: " + name + " Workers: " + calcNumberWorkers() + " Deposit: " + deposit + " #Products: " + products.size() + " Industry: " + industry;
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

    public Integer calcNumberProducts()
    {
        return products.size();
    }

    @Override
    public double getMargin()
    {
        return marginPercent;
    }
}
