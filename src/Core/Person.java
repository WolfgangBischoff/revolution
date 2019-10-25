package Core;

import Core.Enums.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.Enums.IndustryType.*;
import static Core.Util.*;

public class Person // implements ProductOwner
{
    private static Integer nextId = 1;
    private Integer id;
    PersonName name;
    Integer age;
    Integer baseHappiness;
    Integer effectiveHappiness;
    Workposition worksAt;
    protected Integer deposit;
    protected ConsumeDataStorage consumeDataStorage = new ConsumeDataStorage(this);
    private final static String PERSONNAMESPATH = "./res/txt/names/persons/";

    //Malus if less, just to reache but not more. Luxury for more
    Map<IndustryType, Double> needs = new HashMap<>();
    Map<IndustryType, Double> needsGrow = new HashMap<>();

    EconomicLayer economicLayer;
    EducationalLayer educationalLayer;
    PoliticalOpinion politicalOpinion;
    BudgetPlan budgetPlan;

    //Constructor and Creators
    public Person()
    {
        this(DEFAULT_EDU);
    }

    public Person(EducationalLayer definedEdu)
    {
        this(new PersonName(chooseRandomFirstname(), chooseRandomLastname()), getRandom().nextInt(75), definedEdu, definedEdu.getInt() * definedEdu.getInt() * PERSON_DEFAULT_DEPOSIT);
    }

    public Person(PersonName name)
    {
        this(name, DEFAULT_AGE);
    }

    public Person(PersonName name, Integer ag)
    {
        this(name, ag, DEFAULT_EDU, PERSON_DEFAULT_DEPOSIT);
    }

    public Person(PersonName name, Integer age, EducationalLayer edu, Integer deposit)
    {
        id = nextId++;
        this.name = name;
        this.age = age;
        educationalLayer = edu;
        this.deposit = deposit;

        for(IndustryType industryType : IndustryType.values())
        {
            needs.put(industryType, 0d);
        }
        needsGrow.put(FOOD, .8);
        needsGrow.put(CLOTHS, .2);
        needsGrow.put(HOUSING, 1.0);
        needsGrow.put(ENERGY, 1.0);
        needsGrow.put(ELECTRONICS, .2);
        needsGrow.put(HEALTH, .05);
        needsGrow.put(TRAFFIC, .3);
        needsGrow.put(EDUCATION, .01);
        needsGrow.put(SPARETIME, .5);

        /*
        needs.put(FOOD, 11);
        needs.put(CLOTHS, 2);
        needs.put(HOUSING, 1);
        needs.put(ENERGY, 30);
        needs.put(ELECTRONICS, 5);
        needs.put(HEALTH, 1);
        needs.put(TRAFFIC, 15);
        needs.put(EDUCATION, 1);
        needs.put(SPARETIME, 10);
        */
        initState();
    }


    //Init und Calculate
    void initState()
    {
        calcBaseHappiness();
        effectiveHappiness = baseHappiness;
        budgetPlan = new BudgetPlan(this);
        budgetPlan.calcBudget();
        calculateEconomicLayer();
        calcPoliticalOpinion();
    }

    void calcStateDaily()
    {
        calculateEconomicLayer();
        calcEffectiveHappiness();
        calcPoliticalOpinion();
        calcNeeds();
    }

    void calcStateMonthly()
    {
        budgetPlan.calcBudget();
    }

    void calcNeeds()
    {
        for(Map.Entry<IndustryType, Double> need : needs.entrySet())
        {
            Double newValue = roundTwoDigits(need.getValue() + needsGrow.get(need.getKey()));
            need.setValue(newValue);
        }
        //System.out.println("Person calcNeeds");
        //System.out.println(needs);
    }

    void calculateEconomicLayer()
    {
        if (getGrossIncome() < THRESHOLD_VERY_POOR)
            economicLayer = EconomicLayer.ECO_VERYPOOR;
        else if (getGrossIncome() < THRESHOLD_POOR)
            economicLayer = EconomicLayer.ECO_POOR;
        else if (getGrossIncome() < THRESHOLD_MEDIUM)
            economicLayer = EconomicLayer.ECO_MIDDLE;
        else if (getGrossIncome() < THRESHOLD_RICH)
            economicLayer = EconomicLayer.ECO_RICH;
        else if (getGrossIncome() > THRESHOLD_RICH)
            economicLayer = EconomicLayer.ECO_VERYRICH;
    }

    void calcBaseHappiness()
    {
        baseHappiness = INIT_BASE_HAPPINESS;
    }

    void calcEffectiveHappiness()
    {
        //initPeriod on base of internal vars
        double avginc = Society.getSociety().getSocietyStatistics().getAvgIncome();
        effectiveHappiness = baseHappiness;
        if (getGrossIncome() < avginc)
            effectiveHappiness -= 10;
        else
            effectiveHappiness += 10;
    }

    void calcPoliticalOpinion()
    {
        if (effectiveHappiness < baseHappiness || worksAt == null)
            politicalOpinion = PoliticalOpinion.SocialDemocratic;
        else if (effectiveHappiness > baseHappiness)
            politicalOpinion = PoliticalOpinion.Conservativ;
        else if (effectiveHappiness == baseHappiness)
            politicalOpinion = PoliticalOpinion.Unpolitical;
    }

    void receiveSalary(Integer salary)
    {
        deposit += salary;
    }

    public void shop()
    {
        //check if there is budget for today
        LocalDate today = Simulation.getSingleton().getDate();

        //for(IndustryType industryType : IndustryType.values())
        IndustryType industryType = FOOD;
        {
            BudgetPost budgetPost = BudgetPost.fromIndustryType(industryType);
            if(needs.get(industryType) >= 1)
            {
                //Note Demand

                //Calc Budget based on residual buget
                Integer residualBudgetMonth = budgetPlan.monthBudget.get(budgetPost);
                Integer residualDaysMonth = today.lengthOfMonth() - today.getDayOfMonth() +1;
                Double estimatedNumberOfNeedsMonth = today.lengthOfMonth() * needsGrow.get(industryType);
                Double estimatedNumberOfNeedsResidualMonth = residualDaysMonth * needsGrow.get(industryType);
                //Integer budgetToday = (int)(residualBudgetMonth / estimatedNumberOfNeedsResidualMonth);
                //System.out.println("Person shop " + residualDaysMonth +" * "+ needsGrow.get(industryType) + " " + estimatedNumberOfNeedsResidualMonth + " " + industryType);
                Integer budgetToday = (int)(residualBudgetMonth / estimatedNumberOfNeedsMonth);
                System.out.println("Person shop " + residualDaysMonth +" * "+ needsGrow.get(industryType) + " " + estimatedNumberOfNeedsMonth);
                System.out.println("Person shop residualBudget " + residualBudgetMonth  + " budgetTod " + budgetToday);
                //Try Buy
                Company bestSupplier = Market.getMarket().getBestOffer(industryType, budgetToday);

                //Pay Company and consume
                if(bestSupplier != null)
                {
                    consumeDataStorage.consume(industryType, bestSupplier.getLuxury());
                    deposit -= bestSupplier.getPrice();
                    bestSupplier.produce();
                    bestSupplier.getPaid(bestSupplier.getPrice());

                    //Decrease need and budget
                    needs.put(industryType, needs.get(industryType)-1);
                    budgetPlan.monthBudget.put(budgetPost, residualBudgetMonth-bestSupplier.getPrice());
                    System.out.println("Person shop residualBudget " + budgetPlan.monthBudget.get(budgetPost) + " price " + bestSupplier.getPrice());
                }
            }
        }























        /*
        DaylyBudget todaysBudget;
        if(budgetPlan.hasBudget(today))
            todaysBudget = budgetPlan.getBudget(today);
        else
        {
            System.out.println("No budget " + today);
            return;
        }

        //for all Industry types
        for(IndustryType industryType : IndustryType.values())
        {
            //Check if we have budget
            BudgetPost budgetPost = BudgetPost.fromIndustryType(industryType);
            Integer dailyBudgetForIndustry;
            if(!todaysBudget.budgetPosts.containsKey(budgetPost))
                continue;

            dailyBudgetForIndustry = todaysBudget.budgetPosts.get(budgetPost);
            Company bestSupplier = Market.getMarket().getBestOffer(industryType, dailyBudgetForIndustry);

            //Pay Company and consume
            if(bestSupplier != null)
            {
                consumeDataStorage.consume(industryType, bestSupplier.getLuxury());
                deposit -= bestSupplier.getPrice();
                bestSupplier.produce();
                bestSupplier.getPaid(bestSupplier.getPrice());

                //System.out.println(name + " Best " + bestSupplier.dataBase());
                //System.out.println(consumeDataStorage.dataConsume());
            }
        }*/
    }




    //Prints
    @Override
    public String toString()
    {
        return "Person: " + dataBasis();
    }

    public String dataBasis()
    {
        return "ID: " + id + " " + name.getFirstname() + " " + name.getLastname() + " (" + age + ")" + " Deposit: " + deposit;
    }

    public String dateHappiness()
    {
        return "Base: " + baseHappiness + " effective: " + effectiveHappiness + " ";
    }

    public String dataWorksAt()
    {
        if (worksAt == null)
            return "unemployed";
        else
            return worksAt.company.getName();
    }

    public String dataEconomical()
    {
        return "Works at: " + dataWorksAt() + " grossIncome: " + getGrossIncome() + " NettIncome: " + getNettIncome() + " Deposit: " + deposit;
    }

    public String dataLayer()
    {
        return "Edu: " + educationalLayer;
    }

    public String dataBudget()
    {
        return budgetPlan.budgetData()
                ;
    }

    //Helper
    static String chooseRandomFirstname()
    {
        String[] firstnames = Util.readFirstLineFromTxt(PERSONNAMESPATH + "personFirstnames.csv");
        return firstnames[getRandom().nextInt(firstnames.length)];
    }

    static String chooseRandomLastname()
    {
        String[] lastnames = Util.readFirstLineFromTxt(PERSONNAMESPATH + "personLastnames.csv");
        return lastnames[getRandom().nextInt(lastnames.length)];
    }

    //Getter and Setter
    public Integer getGrossIncome()
    {
        if (worksAt != null)
            return worksAt.grossIncomeWork;
        else
            return 0;
    }

    public Integer getNettIncome()
    {
        if (worksAt != null)
            return worksAt.netIncomeWork;
        else
            return 0;
    }

    public void applyToCompany(Company company)
    {
        List<Workposition> vacancies = company.getFreeWorkpositions();
        //Apply to UNIV workposition first
        vacancies.sort(new Comparator<Workposition>()
        {
            @Override
            public int compare(Workposition o1, Workposition o2)
            {
                if(o1.getNeededEducation().getInt() < o2.getNeededEducation().getInt())
                    return 1;
                else if (o1.getNeededEducation().getInt() > o2.getNeededEducation().getInt())
                    return  -1;
                else return 0;
            }
        });
        //Apply to all position beginning with highest edu needed
        for(Workposition workposition : vacancies)
        {
            if(workposition.isWorkerAppropriate(this))
            {
                company.employWorker(workposition, this);
                break;
            }
        }
    }

    public void startAtWorkposition(Workposition workposition)
    {
        this.worksAt = workposition;
        calcStateDaily();
        budgetPlan.calcBudget();
    }

    boolean quitWorkposition()
    {
        if (this.worksAt != null)
        {
            this.worksAt.company.employeeQuitted(worksAt);
            worksAt = null;
            calcStateDaily();
            return true;
        }
        else return false;

    }

    public void getUnemployedAtWorkposition()
    {
        worksAt = null;
        calcStateDaily();
    }

    public Integer getId()
    {
        return id;
    }

    public Integer getAge()
    {
        return age;
    }

    public Integer getDeposit()
    {
        return deposit;
    }

    public String getName()
    {
        return name.toString();
    }

    public Integer getEffectiveHappiness()
    {
        return effectiveHappiness;
    }

    public EconomicLayer getEconomicLayer()
    {
        return economicLayer;
    }

    public EducationalLayer getEducationalLayer()
    {
        return educationalLayer;
    }

    public PoliticalOpinion getPoliticalOpinion()
    {
        return politicalOpinion;
    }

    public ConsumeDataStorage getConsumeDataStorage()
    {
        return consumeDataStorage;
    }
}
