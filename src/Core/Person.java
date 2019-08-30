package Core;

import static Core.Util.*;


public class Person
{
    private static Integer nextId = 1;
    private Integer id;
    PersonName name;
    Integer age;
    Integer baseHappiness;
    Integer effectiveHappiness;
    Workposition worksAt;
    private Integer deposit;


    EconomicLayer economicLayer;
    EducationalLayer educationalLayer;
    PoliticalOpinion politicalOpinion;

    //Constructor and Creators
    public Person (EducationalLayer definedEdu)
    {
        this(new PersonName(chooseRandomFirstname(), chooseRandomLastname()), getRandom().nextInt(100) , definedEdu, PERSON_DEFAULT_DEPOSIT);
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
        calculateEconomicLayer();
        calcPoliticalOpinion();
    }
    void calcState()
    {

        calculateEconomicLayer();
        calcEffectiveHappiness();
        calcPoliticalOpinion();
    }

    void calculateEconomicLayer()
    {
        if(getGrossIncome() < THRESHOLD_VERY_POOR)
            economicLayer = EconomicLayer.ECO_VERYPOOR;
        else if(getGrossIncome() < THRESHOLD_POOR)
            economicLayer = EconomicLayer.ECO_POOR;
        else if(getGrossIncome() < THRESHOLD_MEDIUM)
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
        if(getGrossIncome() < avginc)
            effectiveHappiness -= 10;
        else
            effectiveHappiness += 10;
    }

    void calcPoliticalOpinion()
    {
        if(effectiveHappiness < baseHappiness || worksAt == null)
            politicalOpinion =  PoliticalOpinion.SocialDemocratic;
        else if (effectiveHappiness > baseHappiness)
            politicalOpinion =  PoliticalOpinion.Conservativ;
        else if(effectiveHappiness == baseHappiness)
            politicalOpinion = PoliticalOpinion.Unpolitical;
    }

    void receiveSalary(Integer salary)
    {
        deposit+= salary;
    }

    //Prints
    @Override
    public String toString() {
        return "Person: " + printBasicData();
    }

    public String printBasicData()
    {
        return "ID: " + id + " " + name.getFirstname() + " " + name.getLastname() + " (" + age + ")" + " Deposit: " + deposit;
    }

    public String printHappiness()
    {
        return  "Base: " + baseHappiness + " effective: " + effectiveHappiness + " ";
    }

    public String printWorksAt()
    {
        if(worksAt == null)
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
    public int getGrossIncome()
    {
        if(worksAt != null)
            return worksAt.grossIncomeWork;
        else
            return 0;
    }

    public Integer getNettIncome()
    {
        if(worksAt != null)
            return worksAt.netIncomeWork;
        else
            return 0;
    }

    public void setWorksAt(Workposition worksAt)
    {
        this.worksAt = worksAt;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getDeposit() {
        return deposit;
    }
}
