package Core;

import Core.Enums.*;

import java.util.ArrayList;
import java.util.List;

import static Core.Util.*;

public class Person implements ProductOwner
{
    private static Integer nextId = 1;
    private Integer id;
    PersonName name;
    Integer age;
    Integer baseHappiness;
    Integer effectiveHappiness;
    Workposition worksAt;
    private Integer deposit;
    private List<Product> products = new ArrayList<>();

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
        this(new PersonName(chooseRandomFirstname(), chooseRandomLastname()), getRandom().nextInt(75), definedEdu, PERSON_DEFAULT_DEPOSIT);
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
        initState();
    }


    //Init und Calculate
    void initState()
    {
        calcBaseHappiness();
        effectiveHappiness = baseHappiness;
        budgetPlan = new BudgetPlan(this);
        calculateEconomicLayer();
        calcPoliticalOpinion();
    }

    void calcState()
    {
        calculateEconomicLayer();
        calcEffectiveHappiness();
        calcPoliticalOpinion();
        budgetPlan.calcBudget();
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
        //calc on base of internal vars
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
        //Do budget calc

        //Check availability

        boolean isAvailable = isProductAvailability(IndustryType.FOOD);
        boolean isAffordable = isProductAffordable(BudgetPost.FOOD);
        //Buy
        if(isAvailable && isAffordable)
            buyProduct(IndustryType.FOOD);


        //calc
    }

    private boolean isProductAvailability(IndustryType type)
    {
        //System.out.println("Availability: " + (Market.getMarket().getProducts().size() >= 1));
        return Market.getMarket().getProducts(type).size() >= 1;
    }

    private boolean isProductAffordable(BudgetPost type)
    {
        System.out.println("Affordable: " + (Market.getMarket().getProductPrice() <= deposit));
        return Market.getMarket().getProductPrice() <= deposit;
    }

    private void payProduct(Product product)
    {
        deposit -= Market.getMarket().getProductPrice();
        product.owner.getPaid(Market.getMarket().getProductPrice());
    }

     @Override
     public void getPaid(Integer amount)
     {
         throw new RuntimeException("Person GetPaid()");
     }

    private void buyProduct(IndustryType type)
    {
        Product bought = Market.getMarket().getProduct(type).get(0);
        payProduct(bought);
        Product.transfer(Market.getMarket(), this, bought);
        bought.owner = this;
        System.out.println(this + " bought " + bought);
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

    //Prints
    @Override
    public String toString()
    {
        return "Person: " + printBasicData();
    }

    public String printBasicData()
    {
        return "ID: " + id + " " + name.getFirstname() + " " + name.getLastname() + " (" + age + ")" + " Deposit: " + deposit;
    }

    public String printHappiness()
    {
        return "Base: " + baseHappiness + " effective: " + effectiveHappiness + " ";
    }

    public String printWorksAt()
    {
        if (worksAt == null)
            return "unemployed";
        else
            return worksAt.company.getName();
    }

    public String printEconomical()
    {
        return "Works at: " + printWorksAt() + " grossIncome: " + getGrossIncome() + " NettIncome: " + getNettIncome() + " Deposit: " + deposit;
    }

    public String printLayers()
    {
        return "Edu: " + educationalLayer + " Eco: " + economicLayer + " Pol: " + politicalOpinion;
    }

    public String budgetData()
    {
        return budgetPlan.toString();
    }

    //Helper
    static String chooseRandomFirstname()
    {
        String[] firstnames = {"Wolfgang", "Markus", "Hans", "Stefan", "Elisabeth", "Sebastian", "Juraj", "Anna", "Michael", "Eva", "Stefanie", "Tobias", "Alexander"};
        return firstnames[getRandom().nextInt(firstnames.length)];
    }

    static String chooseRandomLastname()
    {
        String[] lastnames = {"Bischoff", "Delitz", "Otto", "Lempa", "Rosenkranz", "Pay", "Veres", "Markt", "Mitterer", "Storf", "Sprengnagel", "Park", "Husarl"};
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

    public void startAtWorkposition(Workposition workposition)
    {
        this.worksAt = workposition;
        calcState();
    }

    boolean quitWorkposition()
    {
        if (this.worksAt != null)
        {
            this.worksAt.company.employeeQuitted(worksAt);
            worksAt = null;
            calcState();
            return true;
        }
        else return false;

    }

    public void getUnemployedAtWorkposition()
    {
        worksAt = null;
        calcState();
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

    public Integer getNumberProducts(BudgetPost type)
    {
        return products.size();
    }
}
