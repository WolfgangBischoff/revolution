package Core;

import Core.Enums.BudgetPost;

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
    Integer monthlyBudgetSum;
    Map<BudgetPost, Integer> monthBudget = new TreeMap<>();
    List<DaylyBudget> dailyBudgetsOfMonth = new ArrayList<>();
    private Map<BudgetPost, Integer> totalResidualBudgetMonth = new HashMap<>();
    Map<BudgetPost, Integer> weighting = new HashMap<>();
    Person person;

    public Map<BudgetPost, Double> percentageWeighted;
    Integer energyAndHousingCost = 400;
    public Integer consumeBudget = 0;
    public Integer dailyConsumebudget = 0;

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

    public boolean hasBudget(LocalDate date)
    {
        for (DaylyBudget daylyBudget : dailyBudgetsOfMonth)
        {
            if (daylyBudget.date.equals(date))
            {
                return true;
            }
        }
        return false;
    }

    public DaylyBudget getBudget(LocalDate date)
    {
        for (DaylyBudget daylyBudget : dailyBudgetsOfMonth)
            if (daylyBudget.date.equals(date))
                return daylyBudget;
        return null;
    }

    /**
     * Here we decide how much we want to spend for each industry
     */
    public void calcMonthlyBudgetPosts()
    {

        if (person.getDeposit() < person.getNettIncome())
            monthlyBudgetSum = person.getDeposit();
        else
            monthlyBudgetSum = person.getNettIncome();

        LocalDate today = Simulation.getSingleton().getDate();
        consumeBudget = monthlyBudgetSum - energyAndHousingCost;
        dailyConsumebudget = consumeBudget / today.lengthOfMonth();

        weighting.put(FOOD, BUDGET_DEFAULT_WEIGHT_FOOD);
        weighting.put(CLOTHS, BUDGET_DEFAULT_WEIGHT_CLOTHS);
        //weighting.put(HOUSING, BUDGET_DEFAULT_WEIGHT_HOUSING);
        //weighting.put(ENERGY, BUDGET_DEFAULT_WEIGHT_ENERGY);
        weighting.put(ELECTRONICS, BUDGET_DEFAULT_WEIGHT_ELECTRONICS);
        weighting.put(HEALTH, BUDGET_DEFAULT_WEIGHT_HEALTH);
        weighting.put(TRAFFIC, BUDGET_DEFAULT_WEIGHT_TRAFFIC);
        weighting.put(EDUCATION, BUDGET_DEFAULT_WEIGHT_EDUCATION);
        weighting.put(SPARETIME, BUDGET_DEFAULT_WEIGHT_SPARETIME);
        //weighting.put(SAVING, BUDGET_DEFAULT_WEIGHT_SAVING);
        //weighting.put(OTHER_AND_SERVICES, BUDGET_DEFAULT_WEIGHT_OTHER_AND_SERVICES);

        percentageWeighted = Statistics.calcPercFromEnumCount(weighting);

    }

    /**
     * Here we define on which days we use the monthly budget per industry, and how much we use for each day
     */

    private void calcDailyBudgets()
    {


        /*
        //logic to split monthly budget to daily budgets, later more random

        dailyBudgetsOfMonth.clear();
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
            Integer residualBudgetPerPost = initialMonthlyBudgetPost;
            BudgetPost budgetPost = monthlyPost.getKey();

            //Weightning per day (define shopping days, can vary for other industries)
            //higher weight leads to better products if available for a day, number of shopping days is basis need
            Map<DayOfWeek, Integer> weightingPerWeekday = new HashMap<>();
            weightingPerWeekday.put(MONDAY, 1);
            weightingPerWeekday.put(TUESDAY, 0);
            weightingPerWeekday.put(WEDNESDAY, 1);
            weightingPerWeekday.put(THURSDAY, 0);
            weightingPerWeekday.put(FRIDAY, 1);
            weightingPerWeekday.put(SATURDAY, 0);
            weightingPerWeekday.put(SUNDAY, 1);

            //Distribute weekday pattern to month
            List<Integer> weightingPerMonthDay = new ArrayList<>();
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
                residualBudgetPerPost -= daylyBudgetPost;
            }
            totalResidualBudgetMonth.put(budgetPost, residualBudgetPerPost);
        }

*/
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
        return stringBuilder.toString();
    }

    public String budgetData()
    {
        return "Monthly: " + monthlyBudgetSum + " : " + monthBudget.toString() + "\n" +
                "Residual Budget Month: " + totalResidualBudgetMonth + "\n" +
                daylyBudgetData();
    }

}
