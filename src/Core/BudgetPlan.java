package Core;

import Core.Enums.BudgetPost;
import Core.Enums.IndustryType;

import java.time.DayOfWeek;
import java.util.*;

import static Core.Enums.BudgetPost.*;
import static Core.Util.*;
import static java.time.DayOfWeek.*;

/* WRONG
utility-unit = 1
In general budget for product-groups generates utility by: budget/price-utility-unit = #utility-unit * utility-unit = utility
(later utility becomes happiness by Person Marginal Rate)
To involve products we say: product-price = factor * price-utility-unit * utility-unit (factor should make different products possible)
Then budget / product-price = #products = product-price * #product = #product * (factor * price-utility-unit * utility-unit)

Persons should buy similar amount of products, but different prices and therefore utility units. At the same time util units have same price
 */

public class BudgetPlan
{

    private Integer foodBudget;
    private Integer clothsBudget;
    private Integer housingBudget;
    private Integer energyBudget;
    private Integer electronicsBudget;
    private Integer healthBudget;
    private Integer trafficBudget;
    private Integer educationBudget;
    private Integer sparetimeBudget;
    private Integer savingsBudget;
    private Integer otherAndServices;
    Integer monthlyBudgetSum = 0;
    Map<BudgetPost, Integer> monthBudget = new TreeMap<>();
    Integer numberOfDays = 7;
    List<Map<BudgetPost, Integer>> dailyBudgets = new ArrayList<>();
    List<Integer> dailyBudgetSums = new ArrayList<>();
    Person person;

    //Constructor
    public BudgetPlan(Person person)
    {
        this.person = person;
        monthlyBudgetSum = person.getNettIncome();
        //calcBudget();
    }

    //Calculations
    public void calcBudget()
    {
        calcMonthlyBudgetPosts();
        calcDailyBudgets();
    }

    public void calcMonthlyBudgetPosts()
    {
        Map<BudgetPost, Integer> weighting = new HashMap<>();
        Map<BudgetPost, Double> percentageWeighted;
        if (person.getDeposit() < person.getNettIncome())
            monthlyBudgetSum = person.getDeposit();
        else
            monthlyBudgetSum = person.getNettIncome();

        weighting.put(FOOD, BUDGET_DEFAULT_WEIGHT_FOOD);
        weighting.put(CLOTHS, BUDGET_DEFAULT_WEIGHT_CLOTHS);
        weighting.put(HOUSING, BUDGET_DEFAULT_WEIGHT_HOUSING);
        weighting.put(ENERGY, BUDGET_DEFAULT_WEIGHT_ENERGY);
        weighting.put(ELECTRONICS, BUDGET_DEFAULT_WEIGHT_ELECTRONICS);
        weighting.put(HEALTH, BUDGET_DEFAULT_WEIGHT_HEALTH);
        weighting.put(TRAFFIC, BUDGET_DEFAULT_WEIGHT_TRAFFIC);
        weighting.put(EDUCATION, BUDGET_DEFAULT_WEIGHT_EDUCATION);
        weighting.put(SPARETIME, BUDGET_DEFAULT_WEIGHT_SPARETIME);
        weighting.put(SAVING, BUDGET_DEFAULT_WEIGHT_SAVING);
        weighting.put(OTHER_AND_SERVICES, BUDGET_DEFAULT_WEIGHT_OTHER_AND_SERVICES);

        percentageWeighted = Statistics.calcPercFromEnumCount(weighting);
        foodBudget = (int) (percentageWeighted.get(FOOD) * monthlyBudgetSum);
        clothsBudget = (int) (percentageWeighted.get(CLOTHS) * monthlyBudgetSum);
        housingBudget = (int) (percentageWeighted.get(HOUSING) * monthlyBudgetSum);
        energyBudget = (int) (percentageWeighted.get(ENERGY) * monthlyBudgetSum);
        electronicsBudget = (int) (percentageWeighted.get(ELECTRONICS) * monthlyBudgetSum);
        healthBudget = (int) (percentageWeighted.get(HEALTH) * monthlyBudgetSum);
        trafficBudget = (int) (percentageWeighted.get(TRAFFIC) * monthlyBudgetSum);
        educationBudget = (int) (percentageWeighted.get(EDUCATION) * monthlyBudgetSum);
        sparetimeBudget = (int) (percentageWeighted.get(SPARETIME) * monthlyBudgetSum);
        otherAndServices = (int) (percentageWeighted.get(OTHER_AND_SERVICES) * monthlyBudgetSum);
        savingsBudget = monthlyBudgetSum - sumBudgetPostsWithoutSaving();

        //For convinience
        this.monthBudget.put(BudgetPost.FOOD, foodBudget);
        this.monthBudget.put(BudgetPost.CLOTHS, clothsBudget);
        this.monthBudget.put(BudgetPost.HOUSING, housingBudget);
        this.monthBudget.put(BudgetPost.ENERGY, energyBudget);
        this.monthBudget.put(BudgetPost.ELECTRONICS, electronicsBudget);
        this.monthBudget.put(BudgetPost.HEALTH, healthBudget);
        this.monthBudget.put(BudgetPost.TRAFFIC, trafficBudget);
        this.monthBudget.put(BudgetPost.EDUCATION, educationBudget);
        this.monthBudget.put(BudgetPost.SPARETIME, sparetimeBudget);
        this.monthBudget.put(BudgetPost.OTHER_AND_SERVICES, otherAndServices);
        this.monthBudget.put(BudgetPost.SAVING, savingsBudget);
    }

    private void calcDailyBudgets()
    {
        //logic to split monthly budget to daily budgets, later more random

        //init list of daily budgets
        for (int i = 0; i < numberOfDays; i++)
        {
            dailyBudgets.add(new TreeMap<BudgetPost, Integer>());
            dailyBudgetSums.add(0);
        }

        //for monthly budget posts
        for (Map.Entry<BudgetPost, Integer> monthlyPost : monthBudget.entrySet())
        {
            Integer residualMonthlyBudgetPost = monthlyPost.getValue();
            BudgetPost budgetPost = monthlyPost.getKey();

            //Weightning per day can be random in future
            Map<DayOfWeek, Integer> weightingPerDay = new HashMap<>();
            weightingPerDay.put(MONDAY, 2);
            weightingPerDay.put(TUESDAY, 2);
            weightingPerDay.put(WEDNESDAY, 2);
            weightingPerDay.put(THURSDAY, 4);
            weightingPerDay.put(FRIDAY, 2);
            weightingPerDay.put(SATURDAY, 2);
            weightingPerDay.put(SUNDAY, 0);
            Map<DayOfWeek, Double> weightingPerDayPercent = Statistics.calcPercFromEnumCount(weightingPerDay);

            //Assign daily budget for each weekday
            for (int i = 0; i < numberOfDays; i++)
            {
                Map<BudgetPost, Integer> daylyBudget = dailyBudgets.get(i);
                Integer daylybudget = (int) (monthlyPost.getValue() * weightingPerDayPercent.get(DayOfWeek.of(i + 1)));
                residualMonthlyBudgetPost -= (int) (monthlyPost.getValue() * weightingPerDayPercent.get(DayOfWeek.of(i + 1))); //For decimal problem

                //fill in even distributen
                if (i != numberOfDays - 1)
                {
                    daylyBudget.put(budgetPost, daylybudget);
                    dailyBudgetSums.set(i, dailyBudgetSums.get(i) + daylybudget);
                }
                else
                {
                    daylyBudget.put(budgetPost, residualMonthlyBudgetPost); //last day gets cut decimals
                    dailyBudgetSums.set(i, dailyBudgetSums.get(i) + residualMonthlyBudgetPost);
                }
            }
        }
    }

    //Helpers
    public List<Product> getShoppingCartChecked()
    {
        Map<IndustryType, Integer> shoppingBudget = createShoppingBudget();
        System.out.println(person.getName() + " ShoppingBudget: " + shoppingBudget.toString());
        List<Product> shoppingCart = createBundle(shoppingBudget);
        //System.out.println(person.getName() + " ShoppingCart: " + shoppingCart.toString());

        return shoppingCart;
    }

    private List<Product> createBundle(Map<IndustryType, Integer> shoppingBudget)
    {
        //Creates Shopping Cart
        List<Product> shoppingCart = new ArrayList<>();
        for (Map.Entry<IndustryType, Integer> post : shoppingBudget.entrySet())
        {
            shoppingCart.addAll(createBundleOfType(post.getKey(), post.getValue()));
        }
        return shoppingCart;
    }


    private List<Product> createBundleOfType(IndustryType type, Integer money)
    {
        List<Product> shoppingCart = new ArrayList<>();
        List<List<Product>> products = Market.getMarket().getAllProducts(type);

        //Create bundle with basic need min cost
        Integer residualBudget = createShoppingCartBase(products, shoppingCart, money, type);
        System.out.println(person.getName() + " ShoppingCartBASE: " + shoppingCart.toString());
        //Change bundle with max luxury
        createShoppingCartMaxLuxury(products, shoppingCart, residualBudget);
        System.out.println(person.getName() + " ShoppingCartLUXURY: " + shoppingCart.toString());
        return shoppingCart;
    }

    private Integer createShoppingCartBase(List<List<Product>> sameBaseUtilProductList, List<Product> shoppingCart, Integer budget, IndustryType type)
    {
        Integer currentBaseUtil = 0;
        Integer needBaseUtil = person.needs.get(type);
        SizeLoop:
        for (List<Product> sortedLuxuryUtilList : sameBaseUtilProductList)//For Sizes
        {

            Integer priceCheapestProduct = sortedLuxuryUtilList.get(0).calcPrice();
            Integer additionalBaseUtil = sortedLuxuryUtilList.get(0).utilityBase;
            for (Product otherProduct : sortedLuxuryUtilList)
            {
                //buy products with same price till no money
                if (priceCheapestProduct == otherProduct.calcPrice() &&
                        otherProduct.calcPrice() <= budget &&
                        needBaseUtil >= (currentBaseUtil + additionalBaseUtil)) //to avoid too much base util
                {
                    shoppingCart.add(otherProduct);
                    budget -= priceCheapestProduct;
                    currentBaseUtil += sortedLuxuryUtilList.get(0).utilityBase;

                    if (budget == 0 || currentBaseUtil >= needBaseUtil) //No money or base need fulfilled
                        break SizeLoop;
                }
                else
                    break;
            }
        }
        return budget; //residual budget if base need fullfilled before budget == 0
    }

    private List<Product> createShoppingCartMaxLuxury(List<List<Product>> sameBaseUtilProductList, List<Product> shoppingCart, Integer additionalBudget)
    {
        for (List<Product> sortedLuxuryUtilList : sameBaseUtilProductList) //Iterate all product sizes
            {
                System.out.println("START " + sortedLuxuryUtilList.get(0).utilityBase);
                //TODO filter shopping cart to just one util value
                additionalBudget = maxLuxuryOneSize(sortedLuxuryUtilList, shoppingCart, additionalBudget);
            }
        return shoppingCart;
    }

    private Integer maxLuxuryOneSize(List<Product> possibleBetterProducts, List<Product> shoppingCart, Integer additionalBudget)
    {
        List<Product> bestFoundProductsInBudget = shoppingCart;
        boolean productImproved = true;
        while (productImproved)
        {
            productImproved = false;
            shoppingCartItem: for (int i = 0; i < bestFoundProductsInBudget.size(); i++) //Try to better each product step by step
            {
                Product toBeImproved = bestFoundProductsInBudget.get(i);
                System.out.println("Try Improve: " + toBeImproved);
                //improve one step
                for (Product checkIfBetter : possibleBetterProducts)
                {
                    if(checkIfBetter.utilityBase != toBeImproved.utilityBase)//If we reach shopping card item with other base utils
                        break shoppingCartItem; //TODO here is bug, just overgive fitting shopping card items

                    int costIncreaseForBetterProduct = checkIfBetter.calcPrice() - toBeImproved.calcPrice();
                    if (!shoppingCart.contains(checkIfBetter)) //Found product which is already in cart
                        if (checkIfBetter.utilityLuxury > toBeImproved.utilityLuxury && costIncreaseForBetterProduct <= additionalBudget)
                        {
                            System.out.println("Improved: " + checkIfBetter);
                            additionalBudget -= costIncreaseForBetterProduct;
                            bestFoundProductsInBudget.set(i, checkIfBetter);
                            productImproved = true;
                            break; //To let the other product improve
                        }
                }
            }

        }

        return additionalBudget;
    }


    private Map<IndustryType, Integer> createShoppingBudget()
    {
        Map<IndustryType, Integer> shoppingCart = new TreeMap<>();
        //Fetch daily budget
        Map<BudgetPost, Integer> todaysBudget = dailyBudgets.get(0); //weekday
        shoppingCart.put(IndustryType.FOOD, todaysBudget.get(BudgetPost.FOOD));
        shoppingCart.put(IndustryType.CLOTHS, todaysBudget.get(BudgetPost.CLOTHS));
        shoppingCart.put(IndustryType.HOUSING, todaysBudget.get(BudgetPost.HOUSING));
        shoppingCart.put(IndustryType.ENERGY, todaysBudget.get(BudgetPost.ENERGY));
        shoppingCart.put(IndustryType.ELECTRONICS, todaysBudget.get(BudgetPost.ELECTRONICS));
        shoppingCart.put(IndustryType.HEALTH, todaysBudget.get(BudgetPost.HEALTH));
        shoppingCart.put(IndustryType.TRAFFIC, todaysBudget.get(BudgetPost.TRAFFIC));
        shoppingCart.put(IndustryType.EDUCATION, todaysBudget.get(BudgetPost.EDUCATION));
        shoppingCart.put(IndustryType.SPARETIME, todaysBudget.get(BudgetPost.SPARETIME));
        return shoppingCart;
    }

    private int sumBudgetPosts()
    {
        return foodBudget + clothsBudget + housingBudget
                + energyBudget +
                electronicsBudget +
                healthBudget +
                trafficBudget +
                educationBudget +
                sparetimeBudget +
                savingsBudget +
                otherAndServices;
    }

    private int sumBudgetPostsWithoutSaving()
    {
        return foodBudget + clothsBudget + housingBudget
                + energyBudget +
                electronicsBudget +
                healthBudget +
                trafficBudget +
                educationBudget +
                sparetimeBudget +
                otherAndServices;
    }

    //Prints

    public String daylyBudgetData()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfDays; i++)
        {
            stringBuilder.append(DayOfWeek.of(i + 1) + " sum: " + dailyBudgetSums.get(i));
            stringBuilder.append(dailyBudgets.get(i).toString() + "\n");
        }
        return stringBuilder.toString();
    }

    public String budgetData()
    {
        return person.getName() + ":\n" +
                "Monthly: " + monthlyBudgetSum + " : " + monthBudget.toString() + "\n" +
                daylyBudgetData();
    }

    @Override
    public String toString()
    {
        return "BudgetPlan{" +
                "foodBudget=" + foodBudget +
                ", clothsBudget=" + clothsBudget +
                ", housingBudget=" + housingBudget +
                ", energyBudget=" + energyBudget +
                ", electronicsBudget=" + electronicsBudget +
                ", healthBudget=" + healthBudget +
                ", trafficBudget=" + trafficBudget +
                ", educationBudget=" + educationBudget +
                ", sparetimeBudget=" + sparetimeBudget +
                ", savingsBudget=" + savingsBudget +
                ", otherAndServices=" + otherAndServices +
                " SUM: " + sumBudgetPosts();
    }


}
