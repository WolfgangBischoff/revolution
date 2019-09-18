package Core;

import Core.Enums.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.Enums.IndustryType.*;
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
    private ProductStorage productStorage;
    private final static String PERSONNAMESPATH = "./res/txt/names/persons/";

    //Malus if less, just to reache but not more. Luxury for more
    Map<IndustryType, Integer> needs = new HashMap<>();


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
        productStorage = new ProductStorage(this);

        needs.put(FOOD, 10);
        needs.put(CLOTHS, 2);
        needs.put(HOUSING, 1);
        needs.put(ENERGY, 30);
        needs.put(ELECTRONICS, 5);
        needs.put(HEALTH, 1);
        needs.put(TRAFFIC, 15);
        needs.put(EDUCATION, 1);
        needs.put(SPARETIME, 10);
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
        //Get available shopping cart of day
        List<Product> shoppingCart = budgetPlan.getShoppingCartChecked();
        List<Product> bought = Market.getMarket().sellProductsUnchecked(this, shoppingCart);
        System.out.println("\n" + getName() + " bought: " + bought.toString());

        //calc
    }

    @Override
    public void getPaid(Integer amount)
    {
        throw new RuntimeException("Person GetPaid()");
    }

    @Override
    public void pay(Product product)
    {
        Integer price = Market.getMarket().getProductPrice(product.type) * product.utilityBase;
        deposit -= price;
        product.setPriceProductWasBought(price);
        product.owner.getPaid(price);
    }

    @Override
    public void pay(List<Product> products)
    {
        for (Product product : products)
        {
            pay(product);
        }
    }


    @Override
    public void addProduct(Product product)
    {
        productStorage.add(product);
    }

    @Override
    public void removeProduct(Product product)
    {
        productStorage.remove(product);
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

    public String storageData()
    {
        return productStorage.productData();
    }

    public String printLayers()
    {
        return "Edu: " + educationalLayer + " Eco: " + economicLayer + " Pol: " + politicalOpinion;
    }

    public String budgetData()
    {
        return budgetPlan.budgetData()
                //+ "\n" + budgetPlan.basicNeedsData()
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

    public Integer getNumberProducts()
    {
        return productStorage.size();
    }

    public Integer getUtilFromType(IndustryType type)
    {
        return productStorage.calcBaseUtilSum(type);
    }

    public ProductStorage getProductStorage()
    {
        return productStorage;
    }

    @Override
    public double getMargin()
    {
        return 0;
    }
}
