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
        Map<Integer, Integer> priceOptionToExpectedRevenue = new TreeMap<>();

        Integer plannedPrice = price;
        if (marketAnalysisData == null || companyMarketDataStorage == null)
        {
            System.out.println("No Market Data Found");
            return;
        }

        //calc rev for all prices for all group considering competitors
        //1 collect all budgets and competitor prices
        List<Integer> possiblePrices = new ArrayList<>();
        for (Map.Entry<Integer, Integer> budgets : marketAnalysisData.maxRevenueAtPrice.entrySet())
            possiblePrices.add(budgets.getKey());
        for (MarketAnalysisData.LuxuryPriceGroup offer : marketAnalysisData.supplierOffers)
            if (!possiblePrices.contains(offer.price))
                possiblePrices.add(offer.price);
        Collections.sort(possiblePrices);
        System.out.println("Possible Prices: " + possiblePrices);

        //2 check for all budget revenue at all prices considering competitors








        /*
        System.out.println("\n" + name + " real Rev: " + companyMarketData.revenue + " sold " + companyMarketData.numSold + " at price: " + price + " luxury: " + luxury);
        System.out.println("Used Capacity: " + companyMarketData.usedCapacity + " / " + companyMarketData.maxCapacity);
        for (Map.Entry<Integer, Integer> priceToRevenue : marketAnalysisData.maxRevenueCustomerGroup.entrySet())
        {
            System.out.println("Pricegroup: " + priceToRevenue.getKey() + " GroupRevenue: " + priceToRevenue.getValue());
            System.out.println("Offers: " + marketAnalysisData.offersPerCustomerGroup.get(priceToRevenue.getKey()));

            List<MarketAnalysisData.LuxuryPriceGroup> offersOfCustomerGroup = marketAnalysisData.offersPerCustomerGroup.get(priceToRevenue.getKey());
            Integer totalCompetitors;
            if(!offersOfCustomerGroup.isEmpty() && price == offersOfCustomerGroup.get(0).price)
                totalCompetitors = marketAnalysisData.offersPerCustomerGroup.get(priceToRevenue.getKey()).size() -1;
            else
                totalCompetitors = marketAnalysisData.offersPerCustomerGroup.get(priceToRevenue.getKey()).size();

            if(offersOfCustomerGroup.isEmpty() || luxury >= offersOfCustomerGroup.get(0).luxury)
                priceOptionToExpectedRevenue.put(priceToRevenue.getKey(), (priceToRevenue.getValue() / ++totalCompetitors));
        }
        //System.out.println("Competitive Rev at possible Prices: " + priceOptionToExpectedRevenue);
*/











/*
        //check market oppurtunities
        Integer maxRevenueAtCurrentPrice = 0;
        Integer maxCustomersAtCurrentPrice = 0;

        Integer maxRevenueMarket = 0;
        Integer maxRevenueMarketPrice = 0;
        Integer maxRevenueNumCustomers = 0;
        Integer capacityNeededForMaxRevenueCustomers =0;
        for (Map.Entry<Integer, Integer> priceToRevenue : marketAnalysisData.maxRevenueAtPrice.entrySet())
        {
            //max revenue at current price
            if (priceToRevenue.getKey() <= price)
            {
                maxRevenueAtCurrentPrice = priceToRevenue.getValue();
                maxCustomersAtCurrentPrice = priceToRevenue.getKey();
            }
            //max revenue on whole market
            if (priceToRevenue.getValue() >= maxRevenueMarket)
            {
                maxRevenueMarket = priceToRevenue.getValue();
                maxRevenueMarketPrice = priceToRevenue.getKey();
                maxRevenueNumCustomers = marketAnalysisData.maxCustomersAtPrice.get(maxRevenueMarketPrice);
            }
        }

        System.out.println("\n" + name + " Expected Rev: " + maxRevenueAtCurrentPrice  + " maxCustomer at price "+ maxCustomersAtCurrentPrice + " real Rev: " + companyMarketData.revenue + " at price: " + price);
        System.out.println("MARKET OPPURTUNITIES");
        System.out.println("Market maxRevenue " + maxRevenueMarket + " at price " + maxRevenueMarketPrice + " with customers " + maxRevenueNumCustomers);

        if (maxRevenueAtCurrentPrice.equals(companyMarketData.revenue))
            System.out.println("Maxed Revenue at price");
        if (maxRevenueMarket == maxRevenueAtCurrentPrice)
            System.out.println("Chose price that max Market revenue");
        else
            plannedPrice = maxRevenueMarketPrice;

        //check capacity problems
        System.out.println("Used Capacity: " + companyMarketData.usedCapacity + " / " + companyMarketData.maxCapacity);
        if(companyMarketData.usedCapacity + companyMarketData.capacityCostPerCustomer >= companyMarketData.maxCapacity)
            System.out.println("Capacity limit met");

        //check competitor constraints
        Integer luxuryCompetitor = -1;
        Integer cheaperCompetitorWithSameLuxuryPrice = -1;
        Integer numberCompSameOffer = 0;
        for (MarketAnalysisData.LuxuryPriceGroup offer : marketAnalysisData.supplierOffers)
        {
            if (offer.company == this)
                continue;

            //is there competitor with more luxury?
            if (luxuryCompetitor == -1 && offer.luxury > luxury)
            {
                luxuryCompetitor = offer.luxury;
            }
            //is there a cheaper competitor with same quality?
            if (offer.price < plannedPrice && offer.luxury == luxury)
            {
                cheaperCompetitorWithSameLuxuryPrice = offer.price;
            }
            //is there a equal offer?
            if (offer.price == price && offer.luxury == luxury)
            {
                numberCompSameOffer++;
            }
        }


        System.out.println("Found competitor with more Luxury: " + luxuryCompetitor);
        System.out.println("Found competitor with less price: " + cheaperCompetitorWithSameLuxuryPrice);
        System.out.println("Same offer: " + numberCompSameOffer);

        if (cheaperCompetitorWithSameLuxuryPrice != -1)
            plannedPrice = cheaperCompetitorWithSameLuxuryPrice;

        Integer expectedRevenuePriceChange = 0;
        Integer exprectedCustomersAtPlannedPrice = 0;
        if(plannedPrice != price)
        {
            for (Map.Entry<Integer, Integer> priceToCustomer : marketAnalysisData.maxCustomersAtPrice.entrySet())
                //max revenue at current price
                if (priceToCustomer.getKey() <= plannedPrice)
                    exprectedCustomersAtPlannedPrice = priceToCustomer.getValue();

            //TODO expected dependen from numcustomer and companies at this price
            expectedRevenuePriceChange = exprectedCustomersAtPlannedPrice * plannedPrice;
            System.out.println("PlannedPrice: ExpRev " +expectedRevenuePriceChange +" with numCustomer " + exprectedCustomersAtPlannedPrice);
        }

        //TODO Check if revenue at price is higher with higher quality
        //TODO or lower quality
        Integer qualityGapToCompetitor = -1;
        Integer expectedRevenueQualityIncrease = -1;
        if(luxuryCompetitor != -1)
        {
            qualityGapToCompetitor = luxuryCompetitor - companyMarketData.dLuxury;
            expectedRevenueQualityIncrease = (qualityGapToCompetitor + calcProductionEffort()) * maxCustomersAtCurrentPrice;
            System.out.println("Quality Gap detected: " + qualityGapToCompetitor + " expected Rev: " + expectedRevenueQualityIncrease);
        }

        //TODO Decide which Action is best
        price = plannedPrice;
        System.out.println("New Price: " + plannedPrice);

*/
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
                ", workpositions=" + workpositions +
                '}';
    }

    public String baseData()
    {
        return "Name: " + name + " Workers: " + calcNumberWorkers() + " Deposit: " + deposit + " Price: " + price + " Luxury: " + luxury;
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
