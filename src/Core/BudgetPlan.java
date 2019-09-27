package Core;

import Core.Enums.BudgetPost;
import Core.Enums.IndustryType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static Core.Enums.BudgetPost.*;
import static Core.Util.*;
import static java.time.DayOfWeek.*;

class DaylyBudget
{
    LocalDate date;
    Map<BudgetPost, Integer> budgetPosts = new TreeMap<>();

    public DaylyBudget(LocalDate date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        String ret = date + " ";
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<BudgetPost, Integer> entry : budgetPosts.entrySet())
            if (entry.getValue() != 0)
            {
                //stringBuilder.append(date + " ");
                stringBuilder.append(" " + entry.getKey() + ": " + entry.getValue());
            }
        if (!stringBuilder.toString().equals(""))
            return ret + stringBuilder.toString();
        return null;
    }

    public void set(BudgetPost post, Integer amount)
    {
        budgetPosts.put(post, amount);
    }

    public LocalDate getDate()
    {
        return date;
    }
}

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
    Integer monthlyBudgetSum;
    Map<BudgetPost, Integer> monthBudget = new TreeMap<>();
    Integer numberOfDays = 7;

    List<DaylyBudget> dailyBudgetsOfMonth = new ArrayList<>();

    List<Map<BudgetPost, Integer>> dailyBudgets = new ArrayList<>();
    List<Integer> dailyBudgetSums = new ArrayList<>();
    Person person;

    //Constructor
    public BudgetPlan(Person person)
    {
        this.person = person;
        monthlyBudgetSum = person.getNettIncome();
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

        //Get number of day for current month
        Integer numDays = Simulation.getSingleton().getDate().lengthOfMonth();
        LocalDate dayOfMonth = Simulation.getSingleton().getDate().withDayOfMonth(1);
        for (int i = 0; i < numDays; i++)
        {
            dailyBudgetsOfMonth.add(new DaylyBudget(dayOfMonth));
            dayOfMonth = dayOfMonth.plusDays(1);
        }


        //for monthly budget posts
        for (Map.Entry<BudgetPost, Integer> monthlyPost : monthBudget.entrySet())
        {
            Integer initialMonthlyBudgetPost = monthlyPost.getValue();
            Integer residualBudgetPost = initialMonthlyBudgetPost;
            BudgetPost budgetPost = monthlyPost.getKey();

            //Weightning per day (define shopping days)
            Map<DayOfWeek, Integer> weightingPerWeekday = new HashMap<>();
            weightingPerWeekday.put(MONDAY, 1);
            weightingPerWeekday.put(TUESDAY, 1);
            weightingPerWeekday.put(WEDNESDAY, 0);
            weightingPerWeekday.put(THURSDAY, 0);
            weightingPerWeekday.put(FRIDAY, 0);
            weightingPerWeekday.put(SATURDAY, 0);
            weightingPerWeekday.put(SUNDAY, 0);
            List<Integer> weightingPerMonthDay = new ArrayList<>();
            //Distribute weekday pattern to month
            for (int i = 0; i < numDays; i++)
            {
                DayOfWeek day = dailyBudgetsOfMonth.get(i).getDate().getDayOfWeek();
                weightingPerMonthDay.add(weightingPerWeekday.get(day));
            }
            List<Double> weightingPerDayPercent = Statistics.calcPercFromEnumCount(weightingPerMonthDay);
            //Assign daily budget to month days
            for (int i = 0; i < numDays; i++)
            {
                Integer daylyBudgetPost = (int) (initialMonthlyBudgetPost * weightingPerDayPercent.get(i));
                dailyBudgetsOfMonth.get(i).set(budgetPost, daylyBudgetPost);
                residualBudgetPost -= daylyBudgetPost;
            }
            //TODO Residual budget post has no purpose
            System.out.println("RES: " + residualBudgetPost);

        }












/*
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
            weightingPerDay.put(TUESDAY, 4);
            weightingPerDay.put(WEDNESDAY, 0);
            weightingPerDay.put(THURSDAY, 2);
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
        }*/
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
        for (DaylyBudget daylyBudget : dailyBudgetsOfMonth)
        {
            if (daylyBudget.toString() != null)
            {
                stringBuilder.append(daylyBudget);
                stringBuilder.append("\n");
            }
        }
        /*
        for (int i = 0; i < numberOfDays; i++)
        {
            stringBuilder.append(DayOfWeek.of(i + 1) + " sum: " + dailyBudgetSums.get(i));
            stringBuilder.append(dailyBudgets.get(i).toString() + "\n");
        }*/
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
