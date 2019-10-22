package Core;

import Core.Enums.EducationalLayer;
import Core.Enums.IndustryType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private CompanyMarketDataStorage companyMarketDataStorage = new CompanyMarketDataStorage(this);

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
        this(name, industry, Initdeposit, 5,0);
    }

    public Company(IndustryType type, Integer price, Integer luxury)
    {
        this(Economy.getEconomy().createUniqueCompanyName(getRandomCompanyName(type)), type, COMP_DEFAULT_DEPOSIT, price, luxury);
    }

    public Company(String name, IndustryType industry, Integer Initdeposit, Integer price, Integer luxury)
    {
        //Economy.getEconomy().getCompanies().add(this);
        this.name = name;
        deposit = Initdeposit;
        this.industry = industry;
        Market.getMarket().addCompany(this);
        this.price = price;
        this.luxury = luxury;
        addDefaultWorkplaces();
    }


    //Calculations
    public void initPeriod()
    {
        usedCapacity = 0;
        companyMarketDataStorage.initNewDay();
        doMarketDecisions();
    }

    public void addSellData()
    {
        companyMarketDataStorage.addSellData();
    }

    public void addSellDataPlayer()
    {
        companyMarketDataStorage.addSellDataPlayer();
    }


    public void doMarketDecisions()
    {
        LocalDate yesterday = Simulation.getSingleton().getDate().minusDays(1);
        MarketAnalysisData marketAnalysisData = Market.getMarket().getMarketAnalysisData(industry, yesterday);
        CompanyMarketData companyMarketData = companyMarketDataStorage.getAnalysisData(yesterday);

        //Integer plannedPrice = price;
        if (marketAnalysisData == null || companyMarketDataStorage == null)
        {
            System.out.println("No Market Data Found");
            return;
        }

        //Collect all customer prohibitive prices and competitor prices
        List<Integer> possiblePrices = new ArrayList<>();
        for (Map.Entry<Integer, Integer> budgets : marketAnalysisData.maxRevenueAtPrice.entrySet())
            possiblePrices.add(budgets.getKey());
        for (MarketAnalysisData.LuxuryPriceGroup offer : marketAnalysisData.supplierOffers)
            if (!possiblePrices.contains(offer.price))
                possiblePrices.add(offer.price);
        Collections.sort(possiblePrices);
        //System.out.println("\n" + name + " real Rev: " + companyMarketData.revenue + " sold " + companyMarketData.numSold + " at price: " + price + " luxury: " + luxury);
        //System.out.println("P\\B" + marketAnalysisData.maxRevenueAtPrice.keySet());


        //Calc expected revenues per customer-budget group for all defined prices considering competitors and prohibitive prices
        Map<Integer, List<Integer>> priceToCustomerGroupRevenues = calcExpectedRevenuePerCustomerBudgetGroupForPrices(possiblePrices, marketAnalysisData);

        //Sum up customer budget groups per price
        Map<Integer, Integer> priceToExpectedRevenue = new TreeMap<>();
        for(Map.Entry<Integer, List<Integer>> priceData : priceToCustomerGroupRevenues.entrySet())
        {
            List<Integer> customerGroupData = priceData.getValue();
            Integer sum = 0;
            for(Integer entry : customerGroupData)
                sum+=entry;
            priceToExpectedRevenue.put(priceData.getKey(), sum);
        }
        //System.out.println(priceToExpectedRevenue);

        Integer max = 0;
        Integer bestPrice = price;
        for(Map.Entry<Integer, Integer> priceData : priceToExpectedRevenue.entrySet())
        {
            if(priceData.getValue() > max)
            {
                max = priceData.getValue();
                bestPrice = priceData.getKey();
            }
        }
        //System.out.println("Set price to: " + bestPrice);


        //Decide on price
        price = bestPrice;


        //TODO Decide if luxury or capacity should be changed


    }

    private Map<Integer, List<Integer>> calcExpectedRevenuePerCustomerBudgetGroupForPrices(List<Integer> possiblePrices, MarketAnalysisData marketAnalysisData)
    {
        //Calc expected revenues per customer-budget group for all defined prices considering competitors and prohibitive prices
        Map<Integer, List<Integer>> priceToCustomerGroupRevenues = new TreeMap<>();
        //Foreach given price
        for (Integer priceOption : possiblePrices)
        {
            //Foreach Customer Group
            List<Integer> revenuesAtPrice = new ArrayList<>();
            for (Map.Entry<Integer, Integer> customerGroup : marketAnalysisData.numCustomerPerBudget.entrySet())
            {
                Integer budget = customerGroup.getKey();
                Integer numCustomers = customerGroup.getValue();
                Integer expRev = 0;
                Integer totalCompetitors = 1;
                List<MarketAnalysisData.LuxuryPriceGroup> offersOfCustomerGroup = marketAnalysisData.offersPerCustomerGroup.get(budget);

                //System.out.println("Price Option " + priceOption + " Budget " + budget);
                //To expensive for customer group
                if (priceOption > budget)
                {
                    expRev = 0;
                }
                //Affordable for customers and no Competitors
                else if (offersOfCustomerGroup.isEmpty())
                {
                    expRev = numCustomers * priceOption;
                }
                //Affordable but have to consider competitors
                else
                {
                    boolean betterCompetitor = false;
                    //check all competitors (just affordable ones are in market data)
                    for (MarketAnalysisData.LuxuryPriceGroup competitorOffer : offersOfCustomerGroup)
                    {
                        //System.out.println("is Competitor " + competitorOffer.luxury +" == "+ luxury +" && "+ competitorOffer.price +" == "+ priceOption +" && "+ (competitorOffer.company != this));
                        //Better Competitor in customer group
                        if (competitorOffer.luxury > luxury || (competitorOffer.luxury == luxury && competitorOffer.price < priceOption && competitorOffer.company != this))
                        {
                            betterCompetitor = true;
                        }
                        //Competitor on same level
                        else if (competitorOffer.luxury == luxury && competitorOffer.price == priceOption && competitorOffer.company != this)
                            totalCompetitors++;
                        //Case were competitor has lower luxury or higher price not relevant
                    }

                    //System.out.println("Better Comp: " + betterCompetitor);
                    //System.out.println("(" + numCustomers + " * " + priceOption + ")/ " + totalCompetitors);
                    if (betterCompetitor)
                        expRev = 0;
                    else
                        expRev = (numCustomers * priceOption) / totalCompetitors;
                }

                //System.out.println("Added: " + expRev);
                revenuesAtPrice.add(expRev);
            }

            priceToCustomerGroupRevenues.put(priceOption, revenuesAtPrice);
            //System.out.println(priceOption + ": " + revenuesAtPrice);
        }

        return priceToCustomerGroupRevenues;
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
        Long daysElapsed = ChronoUnit.DAYS.between(workposition.hasWorkerSince, today);
        if (daysElapsed < today.getMonth().length(today.isLeapYear()))
            ratioWorkedDaysOfMonth = (double) daysElapsed / today.getMonth().length(today.isLeapYear());

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
            workposition.setWorker(p);
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
        workposition.setWorker(null);
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
            workposition.setWorker(null);
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

        return names[Util.getRandom().nextInt(names.length)].trim();
    }


    //Prints
    @Override
    public String toString()
    {
        return "\nCompany{" +
                "name='" + name + '\'' +
                '}';
    }

    public String dataBase()
    {
        return "Name: " + name + " Workers: " + calcNumberWorkers() + " Deposit: " + deposit + " Price: " + price + " Luxury: " + luxury;
    }

    public String dataWorkpositions()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(Workposition workposition : workpositions)
            stringBuilder.append("\n"+ workposition.dataBase() );
        return stringBuilder.toString();
    }

    public String dataAnalysis()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name + " Deposit:" + deposit);
        for (CompanyMarketData companyMarketData : companyMarketDataStorage.dataContainer)
            stringBuilder.append(companyMarketData.dataCompanyMarketData());
        return stringBuilder.toString();
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


    public Integer getMaxCapacity()
    {
        return maxCapacity;
    }
}
